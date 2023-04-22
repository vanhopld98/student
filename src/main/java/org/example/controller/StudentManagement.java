package org.example.controller;

import org.example.config.DBConnection;
import org.example.model.Student;
import org.example.model.UserProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentManagement {
    public static final String SELECT_ALL_STUDENT = "select * from student";
    public static final String SELECT_STUDENT = "select * from student where name like ?";
    public static final String ADD_NEW_STUDENT = "insert into student (name, student_code, dob, address) values (?,?,?,?);";
    public static final String DELETE_STUDENT = "delete from student where id = ?";
    Connection connection = DBConnection.getConnection();

    public List<Student> getAllStudent() {

        List<Student> studentList = new ArrayList<>();
        try {
            // Tạo câu sql
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STUDENT);

            // Chạy câu sql
            ResultSet rs = statement.executeQuery();

            // Nếu select có kết quả
            while (rs.next()) {

                // Lấy giá trị của các bản ghi
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String studentCode = rs.getString("student_code");
                String dob = rs.getString("dob");
                String address = rs.getString("address");

                // Tạo mới student theo bản ghi trong databse
                Student student = new Student(id, name, studentCode, dob, address);

                // Thêm vào list
                studentList.add(student);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    public boolean addNewStudent(Student student) {

        boolean isSuccess = false;
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_STUDENT);
            statement.setString(1, student.getName());
            statement.setString(2, student.getStudentCode());
            statement.setString(3, student.getDob());
            statement.setString(4, student.getAddress());
            isSuccess = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public boolean deleteStudent(String id) {
        boolean isSuccess = false;
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT);
            statement.setString(1, id);
            isSuccess = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public List<Student> findStudent(String name) {

        List<Student> studentList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT);

            // Tạo value select có dạng là %Lâm% để tìm kiếm gần đúng
            String value = "%" + name + "%";
            statement.setString(1, value);

            // Chạy câu sql
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                // Lấy giá trị của các bản ghi
                Long id = rs.getLong("id");
                String name1 = rs.getString("name");
                String studentCode = rs.getString("student_code");
                String dob = rs.getString("dob");
                String address = rs.getString("address");

                Student student = new Student(id, name1, studentCode, dob, address);
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }
}
