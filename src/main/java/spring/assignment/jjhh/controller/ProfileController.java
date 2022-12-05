package spring.assignment.jjhh.controller;

import java.security.Principal;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.assignment.jjhh.dto.Pro_ImageDto;
import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.repository.AccountRepository;
import spring.assignment.jjhh.service.PrincipalDatails;
import spring.assignment.jjhh.service.ProfileService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
	
	private final ProfileService profileService;
	
	private final AccountRepository accountRepository;
	
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/user/profile")
	public String my_profile(@RequestParam("id") Long id, @AuthenticationPrincipal PrincipalDatails principalDatails, Model model) {
		Account acc = profileService.selrect_Acc(id);
		System.out.println("aaa:" + principalDatails.getid());
		model.addAttribute("account", acc);
		return "profile";
	}
	

	@GetMapping("/user/profile/edit")
	public String profile_edit(@RequestParam("id") Long id, 
			@AuthenticationPrincipal PrincipalDatails principalDatails, Model model) {
		
		if(principalDatails.getid() == id) {
			System.out.println("AAA");
			Account acc = profileService.selrect_Acc(id);
			model.addAttribute("account", acc);
			return "profile_edit";
//			return "redirect:/user/profile?id=" + principalDatails.getid();
		}
		else {
			return "redirect:/user/profile?id=" + principalDatails.getid();
		}
	}
	
	@PostMapping("/user/profile/edit/{id}")
	public String profile_update(@PathVariable(value = "id") Long id, Account acc, 
			@RequestParam(value = "imgsw", required = false) MultipartFile file, Model model) {
		
		System.out.println("아이디:" + acc.getNick());
		System.out.println("비밀번호:" + acc.getPassword());
		String sourceFileName = file.getOriginalFilename();
		System.out.println("이미지 : " + sourceFileName);
		String ps = null;
		
		Account ac = profileService.selrect_Acc(id);
		ac.setNick(acc.getNick());
		if(acc.getPassword() != null) {
			ps = passwordEncoder.encode(acc.getPassword());
			ac.setPassword(ps);
		}
		
		if(!file.isEmpty()) {
			if(ac.getProfileImg() != null) profileService.deleteFile(ac.getProfileImg());
			ac.setProfileImg(null);
			ac.setProfileOriName(null);
			
			Pro_ImageDto img = profileService.ImageSave(file);
			String imgurl = "/images/" + img.getFileOriName();
        	ac.setProfileImg(imgurl);
        	ac.setProfileOriName(img.getFileOriName());
		}
		
		accountRepository.save(ac);
		
		//model.addAttribute("account", acc);
		return "redirect:/";
	}
	
}
