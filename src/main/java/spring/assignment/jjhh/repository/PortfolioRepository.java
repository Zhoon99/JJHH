package spring.assignment.jjhh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import spring.assignment.jjhh.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Query("SELECT p FROM Portfolio p WHERE p.projectName LIKE %:search% AND p.disclosure = '공개'")
    Page<Portfolio> searchPublicPortfolio(String search, Pageable pageable);

    @Query("SELECT p FROM Portfolio p WHERE p.account.accountId = :accountId ORDER BY p.regDate DESC")
    Page<Portfolio> getMyPortfolio(Long accountId, Pageable pageable);

    @Modifying
    @Query("UPDATE Portfolio p SET p.views = p.views + 1 WHERE p.id = :id")
    int updateViews(Long id);

}
