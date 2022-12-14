package spring.assignment.jjhh.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
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
import spring.assignment.jjhh.dto.PortfolioDto;
import spring.assignment.jjhh.dto.Pro_ImageDto;
import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.entity.File;
import spring.assignment.jjhh.repository.AccountRepository;
import spring.assignment.jjhh.repository.FileRepository;
import spring.assignment.jjhh.service.PortfolioService;
import spring.assignment.jjhh.service.PrincipalDatails;
import spring.assignment.jjhh.service.ProfileService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
	
	private final ProfileService profileService;
	
	private final AccountRepository accountRepository;
	private final FileRepository fileRepository;

	private final PasswordEncoder passwordEncoder;

	private final PortfolioService portfolioService;

	@GetMapping("/user/profile")
	public String my_profile(@RequestParam("id") Long id, @AuthenticationPrincipal PrincipalDatails principalDatails, Model model) {
		Account acc = profileService.selrect_Acc(id);
		System.out.println("aaa:" + principalDatails.getid());
		model.addAttribute("account", acc);
		Long l = (long) 1;
		return "profile";
	}
	

	@GetMapping("/user/profile/edit")
	public String profile_edit(@RequestParam("id") Long id, 
			@AuthenticationPrincipal PrincipalDatails principalDatails, Model model) {

		System.out.println("principalDatails : " + principalDatails.getid());
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
		
		System.out.println("?????????:" + acc.getNick());
		System.out.println("????????????:" + acc.getPassword());
		String sourceFileName = file.getOriginalFilename();
		System.out.println("????????? : " + sourceFileName);
		String ps = null;
		
		Account ac = profileService.selrect_Acc(id);
		ac.setNick(acc.getNick());
		if(acc.getPassword() != null) {
			if(!acc.getPassword().equals("")) {
			ps = passwordEncoder.encode(acc.getPassword());
			System.out.println("YY");
//			ac.setPassword(ps);
			}
		}
		else System.out.println("NN");
		ac.setIntroduce(acc.getIntroduce());
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

	@GetMapping("/user/profile/myPortfolio")
	public String myPortfolio(Model model, Authentication authentication, @PageableDefault(size = 4) Pageable pageable) {

		PrincipalDatails userPrincipal = (PrincipalDatails) authentication.getPrincipal();
		Page<PortfolioDto.Preview> myPortfolioPreview = portfolioService.getMyPortfolioPreview(userPrincipal.getAccount().getAccountId(), pageable);

		if (!myPortfolioPreview.isEmpty()) {
			int startPage = Math.max(1, myPortfolioPreview.getPageable().getPageNumber() - 4);
			int endPage = Math.min(myPortfolioPreview.getTotalPages(), myPortfolioPreview.getPageable().getPageNumber() + 4);

			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("preview",myPortfolioPreview);
			return "profile_myportfolio";
		} else {
			return "profile_myportfolio_error";
		}
	}

}
