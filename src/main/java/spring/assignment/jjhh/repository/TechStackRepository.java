package spring.assignment.jjhh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.assignment.jjhh.entity.TechStack;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {
}
