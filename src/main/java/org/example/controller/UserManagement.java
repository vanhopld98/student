package org.example.controller;

import org.example.config.DBConnection;
import org.example.model.UserProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserManagement {

    public static final String SELECT_USER_SQL = "select * from user_profile where username = ?";
    public static final String ADD_NEW_USER = "insert into user_profile (username, password, role) values (?, ?, ?)";

    public static final String SELECT_USERNAME_SQL = "select username from user_profile where username = ?";

    Connection connection = DBConnection.getConnection();

    public boolean checkUsername(String username) {
        try {
            // Tạo câu sql
            PreparedStatement statement = connection.prepareStatement(SELECT_USERNAME_SQL);
            // Gán username vào câu sql
            statement.setString(1, username);
            // Chạy câu sql
            ResultSet rs = statement.executeQuery();

            // Nếu select có kết quả
            while (rs.next()) {
                System.err.println("Tài khoản này đã tồn tại trong hệ thống");
                // Nếu tồn tại username đã có trong hệ thống thì trả ra true
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public UserProfile getUserProfile(Scanner scanner) {

        // Nhận vào username khi nhập
        System.out.print("Nhập username: ");
        scanner.nextLine();
        String username = scanner.nextLine();

        // Nhận vào password khi nhập
        System.out.print("Nhập password :");
        String password = scanner.nextLine();

        try {
            // Tạo câu sql
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_SQL);
            // Gán username vào câu sql
            statement.setString(1, username);
            // Chạy câu sql
            ResultSet rs = statement.executeQuery();

            // Nếu select có kết quả
            while (rs.next()) {
                // Lấy username, password, role, id của user từ Database
                String passwordSelect = rs.getString("password");
                String usernameSelect = rs.getString("username");
                String roleSelect = rs.getString("role");
                String idSelect = rs.getString("id");

                // Nếu có username và password trong database thì kiểm tra có trùng username và password khi nhập vào hay không
                if (passwordSelect.equals(password)) {
                    return new UserProfile(idSelect, usernameSelect, passwordSelect, roleSelect);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(UserProfile userProfile) {
        boolean isSuccess = false;
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_USER);
            statement.setString(1, userProfile.getUsername());
            statement.setString(2, userProfile.getPassword());
            statement.setString(3, userProfile.getRole());
            isSuccess = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

}
