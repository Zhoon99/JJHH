package spring.assignment.jjhh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.repository.AccountRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder; 
//	
	@Autowired
	private AccountRepository repository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("getClientRegistration :" + userRequest.getClientRegistration());
		System.out.println("getAccessToken :" + userRequest.getAccessToken());
//		System.out.println(super.loadUser(userRequest).getAttributes());
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println("==========>" + oAuth2User.getAttributes());
		System.out.println("==========>22" + oAuth2User.getAttribute("name"));
		
		String provider = userRequest.getClientRegistration().getClientId();
		String providerid = oAuth2User.getAttribute("sub");
		String username = provider+"_"+providerid;
		String Nick = oAuth2User.getAttribute("name");
		String password = "gg";//bCryptPasswordEncoder.encode("gg");
 		String email = oAuth2User.getAttribute("email");
 		String role = "ROLE_USER";
		
 		Account userEntity = repository.findByEmail(username);
 		
 		if(userEntity == null) {
 			userEntity = Account.builder()
 				.email(email)
 				.password(password)
 				.nick(Nick)
 				.provider_id(providerid)
 				.provider(provider)
 				.build();
 			repository.save(userEntity);
 					
 		}
 		
		return new PrincipalDatails(userEntity, oAuth2User.getAttributes());
	}
}
