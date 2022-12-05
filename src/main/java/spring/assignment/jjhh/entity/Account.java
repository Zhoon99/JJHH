package spring.assignment.jjhh.entity;

import javax.persistence.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import spring.assignment.jjhh.dto.AccountDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"portfolioList", "commentList"})
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long accountId;
	
	@Column(length = 50)
	private String email;
	
	private String password;
	
	private String nick;

	private String role;
	
	@Column(name = "profile_img")
	private String profileImg;
	
	private String profileOriName;
	
	@Column(name = "profile_thumb")
	private String profileThumb;
	
	@Column(columnDefinition = "TEXT")
	private String introduce;
	
	@Column(name = "provider_id")
	private String providerId;
	
	@Column(length = 100)
	private String provider;

	@OneToMany(mappedBy = "account")
	private List<Portfolio> portfolioList = new ArrayList<>();

	@OneToMany(mappedBy = "account")
	private List<Comment> commentList = new ArrayList<>();
	
	public static Account createAccount(AccountDto dto, PasswordEncoder passwordEncoder) {
		Account account = new Account();
		account.setEmail(dto.getEmail());
		account.setNick(dto.getNick());
        String password = passwordEncoder.encode(dto.getPassword());
        account.setPassword(password);
        account.setRole(dto.getRole());
        account.setProfileImg(dto.getProfile_img());
        account.setProfileOriName(dto.getProfileOriName());
        return account;
	}
	
	public void updateAccount(AccountDto dto) {
		this.nick = dto.getNick();
		this.password = dto.getPassword();
//		this.profileImg = dto.getProfile_img();
		this.introduce = dto.getIntroduce();
	}
		
	@Builder
    public Account(String email, String password,String nick, String role ,String profile_img, String profile_thumb, String introduce, String provider_id, String provider) {
		this.email = email;
		this.password = password;
		this.nick = nick;
		this.profileImg = profile_img;
		this.profileThumb = profile_thumb;
		this.introduce = introduce;
		this.role = role;
		this.providerId = provider_id;
		this.provider = provider;
	}
}
