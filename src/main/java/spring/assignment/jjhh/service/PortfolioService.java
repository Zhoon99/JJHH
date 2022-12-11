package spring.assignment.jjhh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import spring.assignment.jjhh.dto.Port_File_Dto;
import spring.assignment.jjhh.dto.AccountResponse;
import spring.assignment.jjhh.dto.PortfolioDto;
import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.entity.File;
import spring.assignment.jjhh.entity.Portfolio;
import spring.assignment.jjhh.entity.Team;
import spring.assignment.jjhh.entity.TechStack;
import spring.assignment.jjhh.repository.FileRepository;
import spring.assignment.jjhh.repository.PortfolioRepository;
import spring.assignment.jjhh.repository.TeamRepository;
import spring.assignment.jjhh.repository.TechStackRepository;

import java.time.format.DateTimeFormatter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final TechStackRepository techStackRepository;
    private final FileRepository fileRepository;
    private final TeamRepository teamRepository;
    private final String rootPath = System.getProperty("user.dir");


    public Port_File_Dto filesave(String uploadPath, String originalFileName, byte[] fileData) throws IOException {


    	UUID uuid = UUID.randomUUID();
        String imgName = uuid.toString() + originalFileName;
        String fileDir = uploadPath + "/src/main/resources/static/data";
        String fileUploadFullUrl = fileDir + "/" + imgName;

        System.out.println(fileDir);
        System.out.println(imgName);
        System.out.println(fileUploadFullUrl);

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
    	return null;
    }

    @Transactional
    public void registPortfolio(PortfolioDto.Request portfolioDto, MultipartFile[] files, Account account) {
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

        if(files != null && files.length > 0) {
            //파일 저장

//           List<File> fileList = new ArrayList<>();
           File file;
           for(int i = 0; i < files.length; i++) {
        	   file = new File();
//        	   portfolioService.filesave(rootPath, files[i].getOriginalFilename(), files[i].getBytes());
        	   UUID uuid = UUID.randomUUID();

               String imgName = uuid + files[i].getOriginalFilename();
               String fileDir = rootPath + "/src/main/resources/static/data";
               String fileUploadFullUrl = fileDir + "/" + imgName;

               String filepath = "/data/" + imgName;
               FileOutputStream fos;
               try {
            	   fos = new FileOutputStream(fileUploadFullUrl);
            	   fos.write(files[i].getBytes());
            	   fos.close();
               } catch (Exception e) {
            	   System.out.println("에러");
               }

        	   file.setPortfolio(portfolio);
        	   file.setPath(filepath);
	           file.setUuid(uuid.toString());
	           file.setFileName(files[i].getOriginalFilename());
	           fileRepository.save(file);
           }
        }
    }

    @Transactional
    public Page<PortfolioDto.Preview> getSearchedPortfolioPreview(String s, Pageable pageable) {
//        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "regDate"));

        Page<Portfolio> searchedPortfolio = portfolioRepository.searchPublicPortfolio(s, pageable);

        return getPreviewPage(searchedPortfolio);
    }

    @Transactional
    public Page<PortfolioDto.Preview> getMyPortfolioPreview(Long accountId, Pageable pageable) {
//        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "regDate"));

        Page<Portfolio> searchedPortfolio = portfolioRepository.getMyPortfolio(accountId, pageable);

        return getPreviewPage(searchedPortfolio);
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

    private Page<PortfolioDto.Preview> getPreviewPage(Page<Portfolio> portfolioList) {
        Page<PortfolioDto.Preview> previewList = portfolioList.map(element ->
                PortfolioDto.Preview.builder()
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
                        .build());
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

        portfolioRepository.updateViews(portfolioId);

        return portfolioInfo;
    }

    @Transactional
    public void modifyPortfolio(PortfolioDto.Response portfolioDto, MultipartFile[] files, Account account) {
        ModelMapper modelMapper = new ModelMapper();
        Portfolio portfolio = modelMapper.map(portfolioDto, Portfolio.class);
        portfolio.registInit(0, account);

        portfolioRepository.save(portfolio);

        techStackRepository.delTechStack(portfolio.getId());
        List<TechStack> techStackList = new ArrayList<>();
        portfolioDto.getTechStackList().forEach(element -> {
            TechStack techStack = modelMapper.map(element, TechStack.class);
            techStack.setPortfolio(portfolio);
            techStackList.add(techStack);
        });
        techStackRepository.saveAll(techStackList);

        teamRepository.delTeam(portfolio.getId());
        List<Team> teamList = new ArrayList<>();
        portfolioDto.getTeamList().forEach(element -> {
            Team team = modelMapper.map(element, Team.class);
            team.setPortfolio(portfolio);
            teamList.add(team);
        });
        teamRepository.saveAll(teamList);

        fileRepository.delFile(portfolio.getId());
        if(files != null && files.length > 0) {
            //파일 저장

//           List<File> fileList = new ArrayList<>();
            File file;
            for(int i = 0; i < files.length; i++) {
                file = new File();
//        	   portfolioService.filesave(rootPath, files[i].getOriginalFilename(), files[i].getBytes());
                UUID uuid = UUID.randomUUID();

                String imgName = uuid + files[i].getOriginalFilename();
                String fileDir = rootPath + "/src/main/resources/static/data";
                String fileUploadFullUrl = fileDir + "/" + imgName;

                String filepath = "/data/" + imgName;
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(fileUploadFullUrl);
                    fos.write(files[i].getBytes());
                    fos.close();
                } catch (Exception e) {
                    System.out.println("에러");
                }

                file.setPortfolio(portfolio);
                file.setPath(filepath);
                file.setUuid(uuid.toString());
                file.setFileName(files[i].getOriginalFilename());
                fileRepository.save(file);
            }
        }
    }
}
