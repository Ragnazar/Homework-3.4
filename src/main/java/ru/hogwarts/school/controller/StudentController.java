package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "{id}")  //GET http://localhost:8080/student/23
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping  //GET http://localhost:8080/student
    public ResponseEntity<?> findStudents(@RequestParam(required = false) Integer age,
                                                            @RequestParam(required = false) String name) {
        if (age != null) {
            return ResponseEntity.ok(studentService.getStudentsByAge(age));
        }
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(studentService.findByName(name));
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping    //POST http://localhost:8080/student
    public Student addStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @DeleteMapping(path = "{id}")  //DELETE http://localhost:8080/student/23
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping  //PUT http://localhost:8080/student/23
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student foundStudent = studentService.findStudent(student.getId());
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(studentService.editStudent(student));
    }


    @GetMapping(path = "/age-between") //GET http://localhost:8080/student/age-between?age1=11&age2=12
    public ResponseEntity<Collection<Student>> getStudentsByAgeIsBetween(@RequestParam("age1") int age1,
                                                                         @RequestParam("age2") int age2) {
        return ResponseEntity.ok(studentService.findStudentsByAgeIsBetween(age1, age2));
    }
}
