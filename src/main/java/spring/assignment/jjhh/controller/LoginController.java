package spring.assignment.jjhh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.assignment.jjhh.dto.AccountDto;
import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.repository.AccountRepository;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
	
	private final PasswordEncoder passwordEncoder;
    
    @Autowired
	private AccountRepository accountRepository;
	
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
            BindingResult bindingResult, Model model) {
        log.info("====================>" + dto );
        
        if(bindingResult.hasErrors()) {
            return "member/memberForm";
        }
        
        try {            
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
