package org.zerock.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.MemberDAO;
import org.zerock.domain.MovieDAO;
import org.zerock.service.MemberJoinService;
import org.zerock.service.MemberRegService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

/* 회원 가입에 관한 기능과 VIEW로 연결해주는 컨트롤러 */
@Log4j
@Controller
@AllArgsConstructor
public class MemberAuthJoinController {
	private MemberJoinService service;

	@Inject
	JavaMailSender mailSender;
	
	/* 
	 * 설명: Random 함수를 이용하여, 인증번호를 생성한 뒤 , 보내는 이메일로 접근하여 입력받은 이메일로 인증번호를 발송. 
	 * 일시: 2020.10.08(목)
	 * 작성자: 김동명
	 */
	@PostMapping("/auth.do")
	public ModelAndView mailSending(HttpServletRequest request, String e_mail, 
			HttpServletResponse response_email) throws IOException{
		log.info("auth.do............");
		Random random = new Random();
		// 이메일로 발송할 인증번호 생성
		int dice = random.nextInt(4589362) + 49311; 
		
		String setfrom = "gunmaru54@gmail.com";
		String tomail = request.getParameter("mem_email");
		String title = "회원가입 인증 이메일 입니다.";
		String content =
				// 한줄씩 줄간격을 두기위해 작성
				System.getProperty("line.separator")+ 
				System.getProperty("line.separator")+
				"안녕하세요 회원님 저희 홈페이지를 찾아주셔서 감사합니다"
	            +System.getProperty("line.separator")+
	            System.getProperty("line.separator")+
	            " 인증번호는 " +dice+ " 입니다. "
	            +System.getProperty("line.separator")+
	            System.getProperty("line.separator")+
	            "받으신 인증번호를 홈페이지에 입력해 주시면 다음으로 넘어갑니다.";
		
		try {
			// 여기서 mailSender는 root-context.xml에서 명시한 bean을 의미
			MimeMessage message = mailSender.createMimeMessage();
			
			// 메시지를 담을 mimeMessage를 포함, 인코형식은 "UTF-8" 
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			// 보내는 사람 :: 생략하면 정상작동을 안함
            messageHelper.setFrom(setfrom);
            // 받는사람 이메일
            messageHelper.setTo(tomail);
            // 메일제목
            messageHelper.setSubject(title);
            // 메일 내용
            messageHelper.setText(content); 
            
            // 메시지에 포함되어있는 내용들을 참고하여 메일서버에 접근 후, 이메일 발송까지 처리
            mailSender.send(message);
		}catch(Exception e) {
			// 예외발생시 에러코드 출력
			System.out.println(e);
		}
		
		ModelAndView mv = new ModelAndView(); 

		// 뷰의이름 :: 이메일로 인증번호 발송 후에 나오는 화면
		mv.setViewName("/memberLogin/emailAuthorityInput");
		// 입력받은 이메일을 모델뷰 객체로 넘김
		mv.addObject("mem_email", tomail);
		// 인증번호를 모델객체로 넘김
        mv.addObject("dice", dice); 
       
        System.out.println("mv: " + mv);
        
        // HttpServletResponse의 setContentType을 통해 응답방식을 설정 
        response_email.setContentType("text/html; charset=UTF-8");
        // getWriter로 클라이언트에게 응답하기 위한 출력 스트림을 반환한다.
        PrintWriter out_email = response_email.getWriter();
        // out_email 출력 스트림으로 클라이언트에게 alert응답을 보낸다.
        out_email.println("<script>alert('이메일이 발송되었습니다. 인증번호를 입력해주세요.');</script>");
        // 출력스트림 작동
        out_email.flush();
        return mv;
	}
	
	/*
	 * 설명: 입력한 인증번호와 메일로 받은 인증번호가 맞는지 확인해서 맞으면 회원가입 페이지로 넘어가고, 틀리면 다시 원래 페이지로 돌아오는 메소드
	 * 일시: 2020.10.08(목)
	 * 작성자: 김동명
	 */
	@PostMapping("/join_injeung.do{dice}") 
	public ModelAndView join_injeung(String email_injeung, @PathVariable String dice, 
			HttpServletResponse response_equals, HttpServletRequest request) throws IOException {
		// 입력받은 이메일 값을 받아옴.
		String mem_email = request.getParameter("mem_email");
		
        ModelAndView mv = new ModelAndView();
        
        // "email_injeung"변수는 "emailAuthorityInput.jsp"에서 입력한 인증코드 값 
        //URI를 통해 생성된 인증번호를 전달 받아서 입력받은 인증번호를 비교
        if (email_injeung.equals(dice)) {
        	// 성공시 이돟할 뷰 이름을 설정 (controller에 등록 되어있음)
        	mv.setViewName("/memberLogin/memberJoin"); 
        	// 뷰로 보낼 데이터 값 이메일 값이 아닌 인증 코드값임.
            mv.addObject("e_mail",email_injeung);
            // 입력한 이메일 값을 회원가입페이지에 자동으로 입력하기 위한 키
            mv.addObject("mem_email", mem_email); 

            // HttpServletResponse의 setContentType을 통해 응답방식을 설정
            response_equals.setContentType("text/html; charset=UTF-8");
            // getWriter로 클라이언트에게 응답하기 위한 출력 스트림을 반환한다.
            PrintWriter out_equals = response_equals.getWriter();
            // out_equals 출력 스트림으로 클라이언트에게 alert응답을 보낸다.
            out_equals.println("<script>alert('인증번호가 일치하였습니다. 회원가입창으로 이동합니다.');</script>");
            // 출력스트림 작동
            out_equals.flush();
    
            return mv;
       
        // 인증번호가 틀렸을 시
		}else if(email_injeung != dice) {
			ModelAndView mv2 = new ModelAndView(); 
			
			// 이메일 인증이 실패시 넘어가는 페이지
            mv2.setViewName("/memberLogin/emailAuthorityInput");
            // 인증번호가 틀려도 이메일 값을 유지해서 회원가입 페이지로 넘어갈 떄 이메일 주소를 넘겨주기위한 키
            mv2.addObject("mem_email", mem_email); 
            
            response_equals.setContentType("text/html; charset=UTF-8");
            PrintWriter out_equals = response_equals.getWriter();
            out_equals.println("<script>alert('인증번호가 일치하지않습니다. 인증번호를 다시 입력해주세요.'); </script>");
            out_equals.flush();
            
            return mv2;
		}
        return mv;
	}
	
	// 로그인 화면 출력
	//login-page 속성의 URI는 반드시 GET 방식으로 접근하는 URI를 지정한다.
	@GetMapping("/memberLogin/login") 
	public void login() {
		log.info("login page............");
	}
	
	//이메일 인증 화면 출력
	@GetMapping("/memberLogin/emailAuthority")
	public void emailAuthority() {
		log.info("email_Authority............");
	}
	
	// 에러페이지 출력
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		log.info("access Denied : " + auth);
		model.addAttribute("msg", "Access Denied");
	}
	
	/* 아이디 중복 검사 컨트롤러 */
	@GetMapping("/idCheck.do")
	@ResponseBody
	//Controller쪽에는 해당 정보를 받기 위해선 data에 @ResponseBody가 필수이다.
	public Map<Object, Object> idCheck(HttpServletRequest request) {
		int count = 0;
		log.info("아이디 중복 검사.............");
		String mem_id = request.getParameter("mem_id");
		count = service.memberIdCheck(mem_id);
		
		log.info("아이디 중복값: " + service.memberIdCheck(mem_id));
		log.info("아이디 중복값: " + count);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("cnt", count);
		
		return map;
	}
	
	//회원 등록
	@PostMapping("/memberLogin/memberJoin")
	public String register(MemberDAO member, RedirectAttributes rttr) {
		
		log.info("register: " + member);
		log.info("service: " + service); //서비스 객체 확인 용도
		
		service.memberRegister(member); 		
		rttr.addFlashAttribute("result", member.getMem_id()); //모달창을 위한 처리 addflashAttribute 한번 사용하면 없어진다.그 값이 없어진다.
		
		return "redirect:/memberLogin/login";
	}
}
