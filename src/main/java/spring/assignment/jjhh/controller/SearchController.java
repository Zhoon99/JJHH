package spring.assignment.jjhh.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.assignment.jjhh.dto.PortfolioDto;
import spring.assignment.jjhh.service.PortfolioService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final PortfolioService portfolioService;

    @GetMapping("/search")
    public String searchPortfolio(Model model, @RequestParam String s, @PageableDefault(size = 4, sort="views",direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PortfolioDto.Preview> searchedPortfolio = portfolioService.getSearchedPortfolioPreview(s, pageable);
        if (!searchedPortfolio.isEmpty()) {
            int startPage = Math.max(1, searchedPortfolio.getPageable().getPageNumber() - 4);
            int endPage = Math.min(searchedPortfolio.getTotalPages(), searchedPortfolio.getPageable().getPageNumber() + 4);

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("search",searchedPortfolio);
            model.addAttribute("keyword", s);
            return "search";
        } else {
            return "search_error";
        }
    }
}
