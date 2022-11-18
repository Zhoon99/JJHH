package spring.assignment.jjhh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long account_id;
	
	@Column(length = 50)
	private String email;
	
	private String password;
	
	private String profile_img;
	
	private String profile_thumb;
	
	@Column(columnDefinition = "TEXT")
	private String introduce;
	
	private String provider_id;
	
	@Column(length = 30)
	private String provider;
}
