package spring.assignment.jjhh.controller;

import java.security.Principal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.service.ProfileService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
	
	private final ProfileService profileService;
	
	@GetMapping("/user/profile")
	public String profile(Model model) {
		Long id = (long) 12; 
		model.addAttribute("data_id",id);
		Account acc = profileService.selrect_Acc(id);
		model.addAttribute("account", acc);
		
		return "profile";
	}
	
	
	@PostMapping("/user/profile/edit")
	public String profile_edit(@RequestParam("id") Long id, Model model) {
//		Long id = (long) 12;
		Account acc = profileService.selrect_Acc(id);
		model.addAttribute("account", acc);
		return "profile_edit";
	}
}
