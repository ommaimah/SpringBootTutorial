package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

// allows Spring to auto-detect custom Bean as a component
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getSingleStudent(Long studentId) {
        return studentRepository.findById(studentId);
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional =
                studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()) {
            throw new IllegalStateException("Email already exists.");
        } else {
            studentRepository.save(student);
        }
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException("Student Id "
                    + studentId + " does not exist.");
        } else {
            studentRepository.deleteById(studentId);
        }
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student studentToUpdate = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student Id " + studentId + " does not exist."
                ));

        if(name != null && name.length() > 0){
            studentToUpdate.setName(name);
        }

        if(email != null && email.length() > 0){
            Optional<Student> studentOptional = studentRepository
                    .findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("Email already taken.");
            }
            studentToUpdate.setEmail(email);
        }
    }

}
