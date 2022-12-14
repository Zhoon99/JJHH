package spring.assignment.jjhh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.assignment.jjhh.dto.AccountDto;
import spring.assignment.jjhh.dto.Pro_ImageDto;
import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.repository.AccountRepository;
import spring.assignment.jjhh.service.ProfileService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
	
	private final PasswordEncoder passwordEncoder;
    
    @Autowired
	private AccountRepository accountRepository;
    
    private final ProfileService profileService;
    
    @GetMapping("/login")
    public String index() {
        return "login";
    }
	
	@GetMapping("/login/error") 
    public String loginError(Model model) {
       model.addAttribute("exception", "아이디 또는 패스워드를 다시 확인하세요.");
        return "login";  
    }
	
	@GetMapping("/new")  
    public String memberForm() {
//        model.addAttribute("memberFormDto", new MemberFormDto());
        return "login/joinForm";
    }

	@PostMapping("/new")
    public String memberForm(AccountDto dto, 
            BindingResult bindingResult,
            @RequestParam(value = "imgsw", required = false) MultipartFile file,  Model model) {
       if(file.isEmpty()) System.out.println("YYY"); 
       else System.out.println("NNN");
       System.out.println(file.getOriginalFilename());
       System.out.println(file.getSize());
       System.out.println(file.getName());
       System.out.println("file : " + dto.getIntroduce()); 
        if(bindingResult.hasErrors()) {
            return "member/memberForm";
        }
        
        try {
//        	if(img.getFileOriName)
        	if(!file.isEmpty()) {
	        	Pro_ImageDto img = profileService.ImageSave(file);
	        	System.out.println("222 : " + file.getOriginalFilename());
	        	String imgurl = "/images/" + img.getFileOriName();
	        	System.out.println("image : " + imgurl);
	        	dto.setProfile_img(imgurl);
	        	dto.setProfileOriName(img.getFileOriName());
        	}
        	dto.setRole("basic");
            Account createMember = Account.createAccount(dto, passwordEncoder);
            accountRepository.save(createMember);
        } catch(IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
        	System.out.println("실패");
            return "member/memberForm";
        }
        
        return "redirect:/";
    }
}
