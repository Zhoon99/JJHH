package spring.assignment.jjhh.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Port_File_Dto {
	private String fileName;

	private String fileOriName;

	private String fileUrl;

	public Port_File_Dto(String fileName, String fileOriName, String fileUrl) {
		this.fileName = fileName;
		this.fileOriName = fileOriName;
		this.fileUrl = fileUrl;
	}
	
}
