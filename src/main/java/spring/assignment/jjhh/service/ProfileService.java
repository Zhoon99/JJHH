package spring.assignment.jjhh.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.assignment.jjhh.entity.Account;
import spring.assignment.jjhh.repository.AccountRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {
	private final AccountRepository accountRepository;
	
	public Account selrect_Acc(Long id) {
		Account acc = accountRepository.findByAccountId(id);
//		System.out.println(acc.getEmail());
//		System.out.println(acc.getNick());
//		System.out.println(acc.getProvider());
		
		return acc;
	}
	
}
