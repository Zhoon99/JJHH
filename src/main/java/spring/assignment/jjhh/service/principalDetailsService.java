package spring.assignment.jjhh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.repository.AccountRepository;

//시큐리티 설정에서 로그인 요청이 오면 자동으로 UserDetailsService타입으로 IoC되어 있는 loadUserByUsername 함수 실행
@Service
public class principalDetailsService implements UserDetailsService{

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("username: " + email);
		Account userEntity = accountRepository.findByEmail(email);
		if(userEntity != null) {
			return new PrincipalDatails(userEntity);
		}
		return null;
	}

}
