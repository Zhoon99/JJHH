package spring.assignment.jjhh.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import spring.assignment.jjhh.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {

    @Query("DELETE FROM File f WHERE f.portfolio.id = :pId")
    @Modifying
    void delFile(Long pId);

}
