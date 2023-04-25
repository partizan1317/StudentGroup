package com.sobol.studentGroup.repositories;

import com.sobol.studentGroup.entities.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository <Student, Long> {
    List<Student> findBySecondName(String secondName);
}
