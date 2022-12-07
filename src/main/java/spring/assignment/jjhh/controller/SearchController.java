package spring.assignment.jjhh.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.assignment.jjhh.dto.PortfolioDto;
import spring.assignment.jjhh.entity.Portfolio;
import spring.assignment.jjhh.repository.PortfolioRepository;
import spring.assignment.jjhh.service.PortfolioService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final PortfolioService portfolioService;

    @GetMapping("/search")
    public String searchPortfolio(@RequestParam String s, Model model) {
        List<PortfolioDto.Preview> searchedPortfolio = portfolioService.getPortfolioPreview(s);
        if (searchedPortfolio.size() > 0) {
            model.addAttribute("search",searchedPortfolio);
        } else {
            model.addAttribute("error","검색 결과가 없습니다.");
        }
        return "search";
    }
}
