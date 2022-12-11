package spring.assignment.jjhh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import spring.assignment.jjhh.entity.TechStack;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {

    @Query("DELETE FROM TechStack t WHERE t.portfolio.id = :pId")
    @Modifying
    void delTechStack(Long pId);

}
