package spring.assignment.jjhh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.assignment.jjhh.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
