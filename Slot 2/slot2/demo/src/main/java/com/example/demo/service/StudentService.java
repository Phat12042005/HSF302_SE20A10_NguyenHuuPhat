package com.example.demo.service;

import com.example.demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void createStudent(String name, String email, int age) {
        Student s = new Student(name, email, age);
        // INSERT
        em.persist(s);
        System.out.println("Saved with ID = " + s.getId());
    }
    @Transactional(readOnly = true)
    public void printAll(){
        em.createQuery("SELECT s From Student s", Student.class)
                .getResultList()
                .forEach(System.out::println);
    }

    @Transactional
    public void updateStudent(Long id, String newName, String newEmail, int newAge) {
        Student student = em.find(Student.class, id);

        if (student != null) {
            student.setFullName(newName);
            student.setEmail(newEmail);
            student.setAge(newAge);


            System.out.println("Updated student with ID = " + id);
        } else {
            System.out.println("Student with ID = " + id + " not found!");
        }
    }


    @Transactional
    public void deleteStudent(Long id) {
        Student student = em.find(Student.class, id);

        if (student != null) {
            em.remove(student);
            System.out.println("Deleted student with ID = " + id);
        } else {
            System.out.println("Cannot delete! Student with ID = " + id + " not found!");
        }
    }
}