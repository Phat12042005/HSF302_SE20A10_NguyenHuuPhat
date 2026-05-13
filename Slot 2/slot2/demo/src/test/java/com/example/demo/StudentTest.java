package com.example.demo;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StudentTest {

    @Autowired
    private StudentService studentService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testCreateStudent() {
        studentService.createStudent("Mai Van Luong", "1a@fpt.edu.vn", 20);

        Student student = entityManager.createQuery(
                        "SELECT s FROM Student s WHERE s.email = :email", Student.class)
                .setParameter("email", "1a@fpt.edu.vn")
                .getSingleResult();

        assertNotNull(student);
        assertEquals("Nguyen Van A", student.getFullName());
        assertEquals(20, student.getAge());
    }

    @Test
    public void testUpdateStudent() {
        Student s = new Student("Gốc", "goc@fpt.edu.vn", 19);
        entityManager.persist(s);

        entityManager.flush();
        Long id = s.getId();

        entityManager.clear();

        studentService.updateStudent(id, "Nguyễn Văn A Updated", "a.updated@fpt.edu.vn", 22);

        entityManager.flush();
        entityManager.clear();

        Student updatedStudent = entityManager.find(Student.class, id);

        assertNotNull(updatedStudent);
        assertEquals("Nguyễn Văn A Updated", updatedStudent.getFullName());
        assertEquals("a.updated@fpt.edu.vn", updatedStudent.getEmail());
        assertEquals(22, updatedStudent.getAge());
    }

    @Test
    public void testDeleteStudent() {
        // 1. Tạo sẵn dữ liệu mẫu
        Student s = new Student("Cần Xóa", "xoa@fpt.edu.vn", 21);
        entityManager.persist(s);

        entityManager.flush();
        Long id = s.getId();

        entityManager.clear();

        studentService.deleteStudent(id);

        entityManager.flush();
        entityManager.clear();

        Student deletedStudent = entityManager.find(Student.class, id);
        assertNull(deletedStudent, "Sinh viên phải được xóa khỏi database");
    }
}