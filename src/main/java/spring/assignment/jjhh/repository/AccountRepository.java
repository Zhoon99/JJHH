package spring.assignment.jjhh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.assignment.jjhh.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	Account findByEmail(String email);
	Account findByAccountId(Long accountId);
	
//	@Query("select a from account a where a.account_id =: id")
//    List<Account> find_id(@Param("id") Long account_id);

}
