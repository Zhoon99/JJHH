package spring.assignment.jjhh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import spring.assignment.jjhh.service.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/assets/**", "/favicon.ico", "/resources/**", "/error","/images/**");
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/","/home","/log","/login/**","layout/**").permitAll()
				.antMatchers("/user/**").authenticated()
				.anyRequest().authenticated()
				;
        
        http.formLogin()
        	.loginPage("/login")
        	.loginProcessingUrl("/login/log")
            .defaultSuccessUrl("/index")                       
            .usernameParameter("email")  
            .failureUrl("/login/error")            
            .and()            
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/")
            .and()
            .oauth2Login()
    		.loginPage("/login")
    		.userInfoEndpoint()
    		.userService(principalOauth2UserService);
		
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
