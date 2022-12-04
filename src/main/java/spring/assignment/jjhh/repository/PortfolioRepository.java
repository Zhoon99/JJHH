package spring.assignment.jjhh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.assignment.jjhh.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

}
