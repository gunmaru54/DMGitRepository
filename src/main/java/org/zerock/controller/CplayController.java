package org.zerock.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.CCriteria;
import org.zerock.domain.CPageDTO;
import org.zerock.domain.MovieDAO;
import org.zerock.service.CplayService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/cplay/*")
@AllArgsConstructor
public class CplayController {
	
	private CplayService service;
	
	// 영화목록 출력
	@GetMapping("/mlist")
	public void list(CCriteria cri,Model model) {
		
		log.info("list: " + cri);
		model.addAttribute("list", service.getList(cri));
		
		log.info("list에 넘어가는 값: " + service.getList(cri));
		
        int total = service.getTotal(cri);
		
		log.info("total: " + total);
		
		model.addAttribute("pageMakerController", new CPageDTO(cri, total));
	}
	
	// 영화 상세 정보 출력
	@GetMapping({"/mget"})
	public void get(@RequestParam("mov_title") String title, Model model) {
		
		log.info("/mget ");
		model.addAttribute("cplay",service.get(title));
	}
}
