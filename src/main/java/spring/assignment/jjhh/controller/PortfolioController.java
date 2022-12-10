package spring.assignment.jjhh.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import spring.assignment.jjhh.dto.PortfolioDto;
import spring.assignment.jjhh.repository.PortfolioRepository;
import spring.assignment.jjhh.service.PortfolioService;
import spring.assignment.jjhh.service.PrincipalDatails;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final PortfolioRepository portfolioRepository;
    private final String rootPath = System.getProperty("user.dir");

    @GetMapping("/user/portfolio/register")
    public String register() {
        return "portfolio/portfolio_register";
    }

    @PostMapping("/user/portfolio/register")
    @ResponseBody
    public void register(@RequestPart(value = "portfolio") PortfolioDto.Request portfolioDto,
                         @RequestPart(value = "file", required = false) MultipartFile[] files, Authentication authentication) {

        PrincipalDatails userPrincipal = (PrincipalDatails) authentication.getPrincipal();
        portfolioService.registPortfolio(portfolioDto, files, userPrincipal.getAccount());
    }

    @GetMapping("/portfolio/detail")
    public String detail(@RequestParam Long p, Model model) {
        PortfolioDto.Response portfolioDetail = portfolioService.getPortfolioDetail(p);

        model.addAttribute("portfolio", portfolioDetail);
        return "portfolio/portfolio_detail";
    }

    @PostMapping("/portfolio/detail/readme/{pId}")
    @ResponseBody
    public String getPortfolioReadme(@PathVariable Long pId) {
        String readme = portfolioRepository.findById(pId).get().getReadme();
        return readme;
    }

    @GetMapping("/user/portfolio/register/modify")
    public String modifyPortfolio(@RequestParam Long pId, Model model) {
        PortfolioDto.Response portfolioDetail = portfolioService.getPortfolioDetail(pId);
        model.addAttribute("portfolio", portfolioDetail);
        return "portfolio/portfolio_modify";
    }

    @PostMapping("/user/portfolio/register/modify")
    @ResponseBody
    public void modifyPortfolio(@RequestPart(value = "portfolio") PortfolioDto.Request portfolioDto,
                                @RequestPart(value = "file", required = false) MultipartFile[] files) {

        portfolioService.modifyPortfolio(portfolioDto, files);
    }
	@PostMapping("/portfolio/detail/delete")
	public String PortfolioDelete(@RequestParam(value = "pId") String id) {
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		Long lo = Long.parseLong(id); 
		portfolioRepository.deleteById(lo);
		return "redirect:/";
	}
}
