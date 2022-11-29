package spring.assignment.jjhh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.repository.AccountRepository;

@Service
//@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	
//	private final PasswordEncoder passwordEncoder;
//	
	@Autowired
	private AccountRepository repository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
//		System.out.println("userRequest : " + userRequest);
//		System.out.println("getClientRegistration : " + userRequest.getClientRegistration().getRegistrationId());
//		System.out.println("==========>" + oAuth2User.getAttributes());
//		System.out.println("==========>22" + oAuth2User.getAttribute("login"));
		String provider = null;
		String providerid = null;
		String username = null;
		String Nick = null;
		String password = null;
		String email = null;
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {

			provider = userRequest.getClientRegistration().getClientId();
			providerid = oAuth2User.getAttribute("sub");
			email = provider + "_" + providerid;
			Nick = oAuth2User.getAttribute("name");
			password = "gg";// bCryptPasswordEncoder.encode("gg");
//			email = oAuth2User.getAttribute("email");
		} else {
			provider = userRequest.getClientRegistration().getClientId();
			providerid = oAuth2User.getAttribute("id").toString();
			email = provider + "_" + providerid;
			
			Nick = oAuth2User.getAttribute("login");
			password = "gg";
		}
		
		Account userEntity = repository.findByEmail(email);

		if (userEntity == null) {
			userEntity = Account.builder().email(email).password(password).nick(Nick).provider_id(providerid)
					.provider(provider).build();
			repository.save(userEntity);

		}
		return new PrincipalDatails(userEntity, oAuth2User.getAttributes());
//		return super.loadUser(userRequest);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
}
