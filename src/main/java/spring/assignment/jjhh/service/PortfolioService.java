package spring.assignment.jjhh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import spring.assignment.jjhh.dto.PortfolioDto;
import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.entity.Portfolio;
import spring.assignment.jjhh.entity.Team;
import spring.assignment.jjhh.entity.TechStack;
import spring.assignment.jjhh.repository.PortfolioRepository;
import spring.assignment.jjhh.repository.TeamRepository;
import spring.assignment.jjhh.repository.TechStackRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final TechStackRepository techStackRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void registPortfolio(PortfolioDto.Request portfolioDto, MultipartFile[] files, Account account) {
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

    @Transactional
    public List<PortfolioDto.Preview> getPortfolioPreview(String s) {
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "modDate"));

        Page<Portfolio> searchedPortfolio = portfolioRepository.searchPublicPortfolio(s, pageRequest);

        List<PortfolioDto.Preview> previewList = new ArrayList<>();
        searchedPortfolio.forEach(element -> {
            PortfolioDto.Preview preview = PortfolioDto.Preview.builder()
                    .id(element.getId())
                    .projectName(element.getProjectName())
                    .introduce(element.getIntroduce())
                    .startDate(element.getStartDate())
                    .lastDate(element.getLastDate())
                    .views(element.getViews())
                    .disclosure(element.getDisclosure())
                    .regDate(element.getRegDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                    .techStackList(element.getTechStackList())
                    .teamList(element.getTeamList())
                    .build();
            previewList.add(preview);
        });

        return previewList;
    }
}
