package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

// allows this class to serve REST endpoint
@RestController
// path that will follow localhost:8080
@RequestMapping(path = "api/v1/students")
// controller class will have all the resources for the API
public class StudentController {

	private final StudentService studentService;

	@Autowired	// magically instantiate studentService
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

    // GET all students from our service layer
	@GetMapping
	public List<Student> getStudents() {
		return studentService.getStudents();
	}

	@GetMapping(path = "/{studentId}")
	public Optional<Student> getSingleStudent(@PathVariable("studentId") Long studentId) {
		return studentService.getSingleStudent(studentId);
	}

	@PostMapping
	public void registerNewStudent(@RequestBody Student student){
		studentService.addNewStudent(student);
	}

	@DeleteMapping(path = "/{studentId}")
	//@DeleteMapping(path = "{studentId}") <- this works too!
	public void deleteStudent(@PathVariable("studentId") Long studentId){
		studentService.deleteStudent(studentId);
	}

	// update name and email
	@PutMapping(path = "/{studentId}")
	public void updateStudent(@PathVariable("studentId") Long studentId,
							  @RequestParam(required=false) String name,
							  @RequestParam(required=false) String email){
		studentService.updateStudent(studentId, name, email);
	}

}