package spring.assignment.jjhh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import spring.assignment.jjhh.entity.Portfolio;
import spring.assignment.jjhh.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("DELETE FROM Team t WHERE t.portfolio.id = :pId")
    @Modifying
    void delTeam(Long pId);
}
