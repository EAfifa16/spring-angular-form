package com.tama.edu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tama.edu.dao.StudentDao;
import com.tama.edu.model.Student;

@CrossOrigin("*")
@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentDao stuDao;

	@GetMapping("/getAllStudents")
	private List<Student> getAllStudent() {
		List<Student> stuList = stuDao.findAll();
		return stuList;
	}

	@GetMapping("/getStudent/{sid}")
	private Optional<Student> getStudentByName(@PathVariable("sid") int sid) {
		Optional<Student> student = stuDao.findById(sid);
		return student;
	}

	@PostMapping("/addStudent")
	private ResponseEntity<String> addStudent(@RequestBody Student student) {
		stuDao.save(student);
		return new ResponseEntity<String>(student.getFirstname() + " : inserted sucessfully", HttpStatus.OK);
	}

	@PutMapping("/updateStudent/{sid}")
	private ResponseEntity<String> updateStudent(@RequestBody Student student, @PathVariable("sid") int sid) {
		Optional<Student> stu = stuDao.findById(sid);
		Student retrivedStu = stu.get();
		if (retrivedStu != null) {
			retrivedStu.setFirstname(student.getFirstname());
			retrivedStu.setLastname(student.getLastname());
			retrivedStu.setAge(student.getAge());
			retrivedStu.setTelephone(student.getTelephone());
			retrivedStu.setAddress(student.getAddress());
			stuDao.save(retrivedStu);
			return new ResponseEntity<String>("Existing Student Updated Sucessfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Student does not exists", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/deleteStudent/{sid}")
	private ResponseEntity<String> deleteStudent(@PathVariable("sid") int sid) {
		Optional<Student> stu = stuDao.findById(sid);
		if (stu.isPresent()) {
			stuDao.deleteById(sid);
			return new ResponseEntity<String>("deleted Sucessufully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("data not present", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping("/addUpdateStudent/{sid}")
	private ResponseEntity<String> addUpdate(@PathVariable int sid, @RequestBody Student student) {
		Optional<Student> stu = stuDao.findById(sid);
		if (stu.isPresent()) {
			// updating Student
			Student updateStu = stu.get();
			updateStu.setFirstname(student.getFirstname());
			updateStu.setLastname(student.getLastname());
			updateStu.setAge(student.getAge());
			updateStu.setTelephone(student.getTelephone());
			updateStu.setAddress(student.getAddress());
			stuDao.save(updateStu);
			return new ResponseEntity<String>("updated Student with sid : " + updateStu.getSid(), HttpStatus.OK);
		} else {
			// saving new Student
			stuDao.save(student);
			return new ResponseEntity<String>("new Student : " + student.getFirstname() + " : added sucessufully :)",
					HttpStatus.OK);
		}
	}

}