package spring.assignment.jjhh.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;
import spring.assignment.jjhh.entity.Account;

//시큐리티가  주소요청이 오면 낚아쳐서 로그인을 진행
@Data
public class PrincipalDatails implements UserDetails, OAuth2User{
	
	private Map<String, Object> attributes;
	private Account account;				
	
	//일반 로그인
	public PrincipalDatails(Account user) {
		System.out.println("여기 와야대");
		this.account = user;
	}
	
	//OAuth 로그인
	public PrincipalDatails(Account user, Map<String, Object> attributes) {
		this.account = user;
		this.attributes = attributes;
	}
	
	//해당 user의 권한 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return account.getRole();
			}
		});
		return collect;
	}
	
	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
//		System.out.println("이히히");
		return account.getNick();
	}

	@Override
	public boolean isAccountNonExpired() { //계정만료?
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { //비밀번호 기간?
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //계정 활성화
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Autowired
	public Long getid() {
		return account.getAccountId();
	}

	
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return null;
	}
	

}
