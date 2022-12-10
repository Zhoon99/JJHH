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
import spring.assignment.jjhh.dto.AccountResponse;
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
import java.util.Optional;

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
    public List<PortfolioDto.Preview> getSearchedPortfolioPreview(String s) {
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "regDate"));

        Page<Portfolio> searchedPortfolio = portfolioRepository.searchPublicPortfolio(s, pageRequest);

        return getPreview(searchedPortfolio);
    }

    @Transactional
    public List<PortfolioDto.Preview> getMyPortfolioPreview(Long accountId) {
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "regDate"));

        Page<Portfolio> searchedPortfolio = portfolioRepository.getMyPortfolio(accountId, pageRequest);

        return getPreview(searchedPortfolio);
    }

    private List<PortfolioDto.Preview> getPreview(Page<Portfolio> portfolioList) {
        List<PortfolioDto.Preview> previewList = new ArrayList<>();
        portfolioList.forEach(element -> {
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

    @Transactional
    public PortfolioDto.Response getPortfolioDetail(Long portfolioId) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);

        ModelMapper modelMapper = new ModelMapper();
        PortfolioDto.Response portfolioInfo = modelMapper.map(portfolio.get(), PortfolioDto.Response.class);

        AccountResponse accountResponse = AccountResponse.builder()
                .accountId(portfolio.get().getAccount().getAccountId())
                .nick(portfolio.get().getAccount().getNick())
                .profile_img(portfolio.get().getAccount().getProfileImg())
                .build();
        portfolioInfo.setWriter(accountResponse);

        portfolioRepository.updateViews(portfolio.get().getId());

        return portfolioInfo;
    }
}
