package spring.assignment.jjhh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.assignment.jjhh.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	Account findByEmail(String email);

}
