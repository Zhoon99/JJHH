package spring.assignment.jjhh.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class AccountDto {
	
	private Long accountId;	
	private String email;
	private String password;
	private String nick;
	private String profile_img;
	private String profile_thumb;
	private String introduce;
	private String provider_id;
	private String provider;
}
