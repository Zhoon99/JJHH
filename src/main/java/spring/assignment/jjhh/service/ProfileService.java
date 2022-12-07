package spring.assignment.jjhh.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.assignment.jjhh.dto.Pro_ImageDto;
import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.repository.AccountRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
	
	private final AccountRepository accountRepository;
	private final String rootPath = System.getProperty("user.dir");
	
	public Account selrect_Acc(Long id) {
		Account acc = accountRepository.findByAccountId(id);
		return acc;
	}
	
	public Pro_ImageDto ImageSave(MultipartFile file){
		UUID uuid = UUID.randomUUID();
		String fileDir = rootPath + "/src/main/resources/static/images";
//		String fileDir = rootPath + "/src/main/resources/static/assets/img/data";
		 String sourceFileName = file.getOriginalFilename();
         String imgName = uuid.toString() + sourceFileName;
         String imgUrl = fileDir + "/" + imgName;
         
         System.out.println("////////////////////////////////////");
         System.out.println(sourceFileName);
         System.out.println(imgName);
         System.out.println(imgUrl);
         System.out.println("////////////////////////////////////");
         
//         System.out.println("aa : " + fileDir);
         FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(imgUrl);
		} catch (FileNotFoundException e1) {
			System.out.println("경로");
		}
         
         try {
			fos.write(file.getBytes());
		} catch (IOException e) {
			System.out.println("업로드실패");
		}
         
        Pro_ImageDto dto = new Pro_ImageDto(sourceFileName, imgName, imgUrl);
		return dto;
	}
	
	public void deleteFile(String filePath) {
		String fileDir = rootPath + "/src/main/resources/static" + filePath;
		File deleteFile = new File(fileDir);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
	}
}
