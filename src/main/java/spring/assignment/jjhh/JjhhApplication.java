package spring.assignment.jjhh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //@CreatedDate 사용
public class JjhhApplication {

	public static void main(String[] args) {
		SpringApplication.run(JjhhApplication.class, args);
	}

}
