package org.acme.service;

import java.util.List;
import java.util.Optional;

import org.acme.model.Student;
import org.acme.repository.StudentRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StudentService {

    @Inject
    StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.listAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findByIdOptional(id);
    }

    @Transactional
    public Student createStudent(Student student) {
        studentRepository.persist(student);
        return student;
    }

    @Transactional
    public Optional<Student> updateStudent(Long id, Student studentDetails) {
        Optional<Student> studentOpt = studentRepository.findByIdOptional(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setFirstName(studentDetails.getFirstName());
            student.setLastName(studentDetails.getLastName());
            student.setEmail(studentDetails.getEmail());
            return Optional.of(student);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteStudent(Long id) {
        return studentRepository.deleteById(id);
    }
}
