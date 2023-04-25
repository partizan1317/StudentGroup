package com.sobol.studentGroup.contollers;

import com.sobol.studentGroup.entities.Student;
import com.sobol.studentGroup.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public String studentsMain(Model model) {
        Iterable<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students-main";
    }

    @GetMapping("/students/add")
    public String studentsAdd(Model model) {
        return "students-add";
    }

    @PostMapping("/students/add")
    public String studentsPostAdd(@RequestParam String firstName, @RequestParam String secondName
            , @RequestParam String patronymic, Model model) {
        Student student = new Student(firstName, secondName, patronymic);
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/students/{id}")
    public String studentsDetails(@PathVariable(value = "id") long id, Model model) {
        if (!studentRepository.existsById(id)) {
            return "redirect:/students";
        }
        Optional<Student> student = studentRepository.findById(id);
        ArrayList<Student> result = new ArrayList<>();
        student.ifPresent(result::add);
        model.addAttribute("student", result);
        return "students-details";
    }

    @GetMapping("/students/{id}/edit")
    public String studentsEdit(@PathVariable(value = "id") long id, Model model) {
        if (!studentRepository.existsById(id)) {
            return "redirect:/students";
        }
        Optional<Student> student = studentRepository.findById(id);
        ArrayList<Student> result = new ArrayList<>();
        student.ifPresent(result::add);
        model.addAttribute("student", result);
        return "students-edit";
    }

    @PostMapping("/students/{id}/edit")
    public String studentsUpdate(@PathVariable(value = "id") long id, @RequestParam String firstName, @RequestParam String secondName
            , @RequestParam String patronymic, Model model) {
        Student student = studentRepository.findById(id).orElseThrow();
        student.setFirstName(firstName);
        student.setSecondName(secondName);
        student.setPatronymic(patronymic);
        studentRepository.save(student);
        return "redirect:/students";
    }

    @PostMapping("/students/{id}/remove")
    public String studentsRemove(@PathVariable(value = "id") long id, Model model) {
        Student student = studentRepository.findById(id).orElseThrow();
        studentRepository.delete(student);
        return "redirect:/students";
    }

    @PostMapping("/students/search")
    public String studentsSearch(@RequestParam String secondName, Model model) {
        if (secondName != null && !secondName.isEmpty()){
            List<Student> students = studentRepository.findBySecondName(secondName);
            model.addAttribute("students", students);
            return "students-main";
        }
        else {
            return "redirect:/students";
        }

    }

}
