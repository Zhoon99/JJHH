package spring.assignment.jjhh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.assignment.jjhh.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Query("SELECT p FROM Portfolio p WHERE p.projectName LIKE %:search% AND p.disclosure = '공개'")
    Page<Portfolio> searchPublicPortfolio(String search, Pageable pageable);

}
