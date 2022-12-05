package spring.assignment.jjhh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.assignment.jjhh.dto.PortfolioDto;
import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.entity.Portfolio;
import spring.assignment.jjhh.entity.Team;
import spring.assignment.jjhh.entity.TechStack;
import spring.assignment.jjhh.repository.PortfolioRepository;
import spring.assignment.jjhh.repository.TeamRepository;
import spring.assignment.jjhh.repository.TechStackRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final TechStackRepository techStackRepository;
    private final TeamRepository teamRepository;

    public void registPortpolio(PortfolioDto.Request portfolioDto, MultipartFile[] files, Account account) {
        if(files != null && files.length > 0) {
            //파일 저장

            ModelMapper modelMapper = new ModelMapper();

            Portfolio portfolio = modelMapper.map(portfolioDto, Portfolio.class);
            portfolio.registInit(0, account);
            portfolioRepository.save(portfolio);

            List<TechStack> techStackList = new ArrayList<>();
            portfolioDto.getTechStackList().forEach(element -> {
                TechStack techStack = modelMapper.map(element, TechStack.class);
                techStack.setPortfolio(portfolio);
                techStackList.add(techStack);
            });
            techStackRepository.saveAll(techStackList);

            List<Team> teamList = new ArrayList<>();
            portfolioDto.getTeamList().forEach(element -> {
                Team team = modelMapper.map(element, Team.class);
                team.setPortfolio(portfolio);
                teamList.add(team);
            });
            teamRepository.saveAll(teamList);
        }
    }
}
