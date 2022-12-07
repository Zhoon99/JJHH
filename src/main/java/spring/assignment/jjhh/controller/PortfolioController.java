package spring.assignment.jjhh.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.assignment.jjhh.dto.AccountResponse;
import spring.assignment.jjhh.dto.PortfolioDto;
import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.entity.Portfolio;
import spring.assignment.jjhh.repository.PortfolioRepository;
import spring.assignment.jjhh.service.PortfolioService;
import spring.assignment.jjhh.service.PrincipalDatails;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final PortfolioRepository portfolioRepository;

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

    @GetMapping("/portfolio/detail")
    public String detail(@RequestParam Long p, Model model) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(p);

        ModelMapper modelMapper = new ModelMapper();
        PortfolioDto.Response portfolioInfo = modelMapper.map(portfolio.get(), PortfolioDto.Response.class);

        AccountResponse accountResponse = AccountResponse.builder()
                .accountId(portfolio.get().getAccount().getAccountId())
                .nick(portfolio.get().getAccount().getNick())
                .profile_img(portfolio.get().getAccount().getProfileImg())
                .build();
        portfolioInfo.setWriter(accountResponse);

        model.addAttribute("portfolio", portfolioInfo);
        return "portfolio/portfolio_detail";
    }

    @PostMapping("/portfolio/detail/readme/{pId}")
    @ResponseBody
    public String getPortfolioReadme(@PathVariable Long pId) {
        String readme = portfolioRepository.findById(pId).get().getReadme();
        return readme;
    }
}
