package spring.assignment.jjhh.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import spring.assignment.jjhh.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
