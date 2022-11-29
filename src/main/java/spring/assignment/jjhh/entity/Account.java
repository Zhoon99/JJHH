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
	private Long account_id;
	
	@Column(length = 50)
	private String email;
	
	private String password;
	
	private String nick;
	
	private String profile_img;
	
	private String profile_thumb;
	
	@Column(columnDefinition = "TEXT")
	private String introduce;
	
	private String provider_id;
	
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
        return account;
	}
	
	@Builder
    public Account(String email, String password,String nick, String profile_img, String profile_thumb, String introduce, String provider_id, String provider) {
		this.email = email;
		this.password = password;
		this.nick = nick;
		this.profile_img = profile_img;
		this.profile_thumb = profile_thumb;
		this.introduce = introduce;
		this.provider_id = provider_id;
		this.provider = provider;
	}
}
