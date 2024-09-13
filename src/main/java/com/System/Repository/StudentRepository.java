package com.System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.System.Entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
	Student findBystudentemail(String email);
}
