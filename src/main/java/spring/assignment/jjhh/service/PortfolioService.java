package spring.assignment.jjhh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import spring.assignment.jjhh.dto.Port_File_Dto;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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
            
           List<File> fileList = new ArrayList<>();
           File file;
           for(int i = 0; i < files.length; i++) {
        	   file = new File();
//        	   portfolioService.filesave(rootPath, files[i].getOriginalFilename(), files[i].getBytes());
        	   UUID uuid = UUID.randomUUID();
        	   
               String imgName = uuid.toString() + files[i].getOriginalFilename();
               String fileDir = rootPath + "/src/main/resources/static/data";
               String fileUploadFullUrl = fileDir + "/" + imgName;
        	   
               FileOutputStream fos;
               try {
            	   fos = new FileOutputStream(fileUploadFullUrl);
            	   fos.write(files[i].getBytes());
            	   fos.close();
               } catch (Exception e) {
            	   System.out.println("에러");
               }
               
               
        	   file.setPortfolio(portfolio);
        	   file.setPath(fileUploadFullUrl);
	           file.setUuid(uuid.toString());
	           file.setFileName(files[i].getOriginalFilename());
	           fileRepository.save(file);
           }
           
            
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
