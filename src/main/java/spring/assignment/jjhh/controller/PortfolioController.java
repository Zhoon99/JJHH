package spring.assignment.jjhh.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import spring.assignment.jjhh.dto.PortfolioDto;
import spring.assignment.jjhh.service.PortfolioService;
import spring.assignment.jjhh.service.PrincipalDatails;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/user/portfolio/register")
    public String register() {
        return "portfolio/portfolio_register";
    }

    @PostMapping("/user/portfolio/register")
    @ResponseBody
    public void register(@RequestPart(value = "portfolio") PortfolioDto.Request portfolioDto,
                         @RequestPart(value = "file", required = false) MultipartFile[] files,
                         Authentication authentication) {

        PrincipalDatails userPrincipal = (PrincipalDatails) authentication.getPrincipal();
        portfolioService.registPortfolio(portfolioDto, files, userPrincipal.getAccount());
    }
}
