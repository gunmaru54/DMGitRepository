package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.MovieDAO;
import org.zerock.service.CplayService;
import org.zerock.service.MemberJoinService;
import org.zerock.service.MovieAdminService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@RequestMapping("/admin/*")
@Log4j
@AllArgsConstructor
public class MovieAdminController {
	private MovieAdminService service;
	private CplayService cplayService;
	
	// 이미지 체크함수
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 영화 등록:: 등록화면
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')") // 관리자만 이용할 수 있는 등록 페이지
	@GetMapping("/register")
	public void movieRegister() {
		log.info("Register Movie View..........");
	}

	// 영화 등록:: 등록처리
	@PostMapping("/register")
	public String movieRegister(MovieDAO movie, RedirectAttributes rttr) {
		log.info("영화 등록: " + movie);
		service.insertMovieInfo(movie);
//		rttr.addFlashAttribute("result", board.getBno()); //모달창을 위한 처리 addflashAttribute 한번 사용하면 없어진다.그 값이 없어진다.
		return "redirect:/admin/register";
	}

	/*
	 * 설명: 영화 상세 페이지 이미지 업로드 처리 
	 * 작성자: 김동명
	 */
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<MovieDAO>> uploadAjaxPost(MultipartFile[] upload_mov_detailImg) {
		// ResponseEntity는 @ResponseBody 어노테이션과 같은 의미로, ResponseEntity를 return Type으로 지정하면 JSON (default) 또는 Xml Format으로 결과를 내려준다.
		// MultipartFile의 변수명은 파일이 만들어지는 시점을 정해준다. 그래서 input or button name의 이름을 넣어준다.
		
		// 파일을 저장한 배열을 선언
		List<MovieDAO> list = new ArrayList<MovieDAO>();
		// 파일이 저장될 경로 선언
		String uploadFolder = "C:\\Users\\gunma\\spring\\boardtest\\src\\main\\webapp\\resources\\img\\";
		
		// 업로드 이미지를 list에 계속 저장
		for (MultipartFile multipartFile : upload_mov_detailImg) {
			MovieDAO attachDAO = new MovieDAO();
			
			// 파일이름을 불러온다.
			String uploadFileName = multipartFile.getOriginalFilename(); 

			// IE has file path :: IE의 경우에는 전체 파일 경로가 전송되므로 마지막 '\'을 기준으로 문자열을 잘라낸다.
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
			// 첨부파일 이름 설정
			attachDAO.setMov_title(uploadFileName); 

			try {
				// 업로드 폴더 위치와 파일이름을 지정해여 저장 파일을 생성
				File saveFile = new File(uploadFolder, uploadFileName);

				// 이미지 파일 여부 확인, 업로드파일이 이미지 일시, 썸네일을 생성
				if (checkImageType(saveFile)) {
					
					// saveFile로 업로드한 파일을 multipartFile에 저장합니다.
					multipartFile.transferTo(saveFile);
					// 이미지 파일이면 true값
					attachDAO.setMov_image(true);
					// C드라이브에 이미지를 파일을 생성한 후, (이떄, 지정한 이름 앞에 "s_"를 붙여 이름을 지정함), 지정한 업로드 경로로 파일을 생성한다.
					FileOutputStream thumnail = new FileOutputStream(new File(uploadFolder, "s_" + uploadFileName));
					// 파일 업로더시 화면상에 보여줄 썸네일 그림을 생성한다.
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumnail, 100, 100);
					thumnail.close();
				}
				// register.jsp로 넘길 값을 list에 셋팅
				list.add(attachDAO); 
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return new ResponseEntity<List<MovieDAO>>(list, HttpStatus.OK);
		// 지정하면 JSON (default) 또는 Xml Format으로 결과를 내려준다.
	}
	
	/*
	 * 설명: 영화 썸네일 이미지 업로드 처리  
	 * 작성자: 김동명
	 */
	@PostMapping(value = "/uploadAjaxAction_Thumbnail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<MovieDAO>> uploadAjaxPost_Thumbnail(MultipartFile[] upload_mov_thumbnail) {
		// MultipartFile의 변수명은 파일이 만들어지는 시점을 정해준다. 그래서 input or button name의 이름을 넣어준다.
		
		List<MovieDAO> list = new ArrayList<MovieDAO>();
		String uploadFolder = "C:\\Users\\gunma\\spring\\boardtest\\src\\main\\webapp\\resources\\img\\thumbnail\\";

		for (MultipartFile multipartFile : upload_mov_thumbnail) {
			MovieDAO attachDAO = new MovieDAO();

			String uploadFileName = multipartFile.getOriginalFilename(); // 파일이름을 불러온다.

			// IE has file path :: IE의 경우에는 전체 파일 경로가 전송되므로 마지막 '\'을 기준으로 문자열을 잘라낸다.
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

			attachDAO.setMov_thumbnail(uploadFileName); // 이름 삽입 //썸네일은 필요가 없다. 

			try {
				File saveFile = new File(uploadFolder, uploadFileName); // 파일을 만든다.

				// 이미지 파일 여부 확인, 이미지라면 썸네일을 만들기
				if (checkImageType(saveFile)) {
					multipartFile.transferTo(saveFile);
					attachDAO.setMov_image(true);// 이미지 파일이면 true값
					FileOutputStream thumnail = new FileOutputStream(new File(uploadFolder, "s_" + uploadFileName)); // C드라이브에 썸네일을 "s_"를 붙여 이름을 지정함
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumnail, 100, 100);
					thumnail.close();
				}
				list.add(attachDAO); // register.jsp로 넘길 값을 list에 셋팅
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		log.info(new ResponseEntity<List<MovieDAO>>(list, HttpStatus.OK));
		return new ResponseEntity<List<MovieDAO>>(list, HttpStatus.OK);
	}
	
	// 등록시 메인 이미지 보이기
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String upload_mov_detailImg) {
		
		log.info("영화 이미지 이름:" + upload_mov_detailImg);
		
		File file = new File("C:\\Users\\gunma\\spring\\boardtest\\src\\main\\webapp\\resources\\img\\" + upload_mov_detailImg);

		log.info("file : " + file);

		ResponseEntity<byte[]> result = null;

		try {
			HttpHeaders header = new HttpHeaders();
			
			// byte[]로 이미지 파일의 데이터를 전송할 때 신경 써야하는 부분은 브라우저에 보내주는 MIME 타입이 파일의 종류에 따라 달라진다. 
			// 이 부분을 해결하기 위해서 probeContentType()을 이용해서 
			// 적절한 MIME 타입 데이터를 Http의 헤더 메시지에 포함할 수 있도록 처리한다. 
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("result: " + result);
		return result;
	}
	
	// 등록시 썸네일 이미지 보이기
	@GetMapping("/display_Thumbnail")
	@ResponseBody
	public ResponseEntity<byte[]> getFile_Thumbnail(String upload_mov_thumbnail) {
		
		log.info("영화 이미지 이름:" + upload_mov_thumbnail);
		
		File file = new File("C:\\Users\\gunma\\spring\\boardtest\\src\\main\\webapp\\resources\\img\\thumbnail\\" + upload_mov_thumbnail);

		log.info("file : " + file);

		ResponseEntity<byte[]> result = null;

		try {
			HttpHeaders header = new HttpHeaders();

			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//등록시 썸네일 조작되는 사진 삭제
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("deleteFile: " + fileName);
		
		File file;
		
		try {
			file = new File("C:\\Users\\gunma\\spring\\boardtest\\src\\main\\webapp\\resources\\img\\" + URLDecoder.decode(fileName,"UTF-8"));
			
			file.delete();
			
			if(type.equals("image")) {
				log.info(file.getAbsolutePath());
				log.info(file.getAbsolutePath().replace("s_", ""));
				
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				
				log.info("largeFileName: " + largeFileName);
				
				file = new File(largeFileName);
				
				file.delete();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("delete", HttpStatus.OK);
	}

	//등록시 썸네일 조작되는 썸네일 삭제
	@PostMapping("/deleteFile_Thumbnail")
	@ResponseBody
	public ResponseEntity<String> deleteFile_Thumbnail(String fileName, String type){
		log.info("deleteFile: " + fileName);
		
		File file = null;
		
		try {
			file = new File("C:\\Users\\gunma\\spring\\boardtest\\src\\main\\webapp\\resources\\img\\thumbnail\\" + URLDecoder.decode(fileName,"UTF-8"));
			
			file.delete();
			
			if(type.equals("image")) {
				log.info(file.getAbsolutePath());
				log.info(file.getAbsolutePath().replace("s_", ""));
				
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				
				log.info("largeFileName: " + largeFileName);
				
				file = new File(largeFileName);
				
				file.delete();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("delete", HttpStatus.OK);
	}
	
	// 영화정보 삭제
	@GetMapping("/remove")
	public String remove(@RequestParam("mov_title") String mov_title, RedirectAttributes rttr) {
		log.info("remove....."+ mov_title);
		
		if(service.removeMovieInfo(mov_title)) {
			
			deleteFiles(mov_title);
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/cplay/mlist";
	}
	
	// 업로드한 썸네일, 메인이미지 삭제 메소드
	private void deleteFiles(String mov_title) {
		if(mov_title == null) {
			return;
		}
		
		log.info("delete attach files.........................");
		log.info("mov_title: " + mov_title);
		
		try {
			Path file = Paths.get("C:\\Users\\gunma\\spring\\boardtest\\src\\main\\webapp\\resources\\img\\" + mov_title);
			Files.deleteIfExists(file);
			file = Paths.get("C:\\Users\\gunma\\spring\\boardtest\\src\\main\\webapp\\resources\\img\\s_" + mov_title);
			Files.deleteIfExists(file);
			
			Path thumbNail = Paths.get("C:\\Users\\gunma\\spring\\boardtest\\src\\main\\webapp\\resources\\img\\thumbnail\\" + mov_title);
			Files.delete(thumbNail);
			thumbNail = Paths.get("C:\\Users\\gunma\\spring\\boardtest\\src\\main\\webapp\\resources\\img\\thumbnail\\s_" + mov_title);
			Files.delete(thumbNail);
		} catch (Exception e) {
			log.error("delete file error" + e.getMessage());
		}
	}
	
	// 선택한 영화정보를 수정 페이지에 제공 
	// 작성자: 김동명
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/update")
	public void update(@RequestParam("mov_title") String mov_title, RedirectAttributes rttr, Model model) {
		log.info("update: " + mov_title);
		model.addAttribute("cplay", cplayService.get(mov_title));
	}
	
	// 영화 정보 수정하는 로직처리
	// 작성자: 김동명
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/update")
	public String modify(MovieDAO movie, RedirectAttributes rttr) {
		log.info("modify:" + movie);
		
		if(service.updateMovieInfo(movie)) {
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/cplay/mlist";
	}
}
