package com.tama.edu.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tama.edu.model.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Integer> {

	@Query("select stu from Student stu where stu.firstname = ?1")
	public  List<Student> findByName(String firstname);
 
}
