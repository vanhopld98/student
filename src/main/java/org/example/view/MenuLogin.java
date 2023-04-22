package org.example.view;


import org.example.controller.StudentManagement;
import org.example.controller.UserManagement;
import org.example.model.Student;
import org.example.model.UserProfile;

import java.util.List;
import java.util.Scanner;

public class MenuLogin {

    StudentManagement studentManagement = new StudentManagement();
    UserManagement userManagement = new UserManagement();

    public void run() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        // Khởi tạo biến chọn chương trình
        int choice;
        do {
            menuLogin();
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    login(scanner);
                    break;
                }
                case 2: {
                    isRegister(scanner);
                    break;
                }
                case 0: {
                    System.exit(0);
                    break;
                }
            }
        } while (choice != 0);
    }

    public void login(Scanner scanner) {
        // Kiểm tra xem đăng nhập có đúng username hay password không
        UserProfile userProfile = userManagement.getUserProfile(scanner);
        if (userProfile != null) {
            if ("ADMIN".equals(userProfile.getRole())) {
                int choice1;
                do {
                    showMenuStudent(userProfile.getUsername());
                    choice1 = scanner.nextInt();
                    switch (choice1) {
                        case 1: {
                            // Hiển thị toàn bộ sinh viên
                            showAllStudent();
                            break;
                        }
                        case 2: {
                            // Thêm mới sinh viên
                            addNewStudent(scanner);
                            break;
                        }
                        case 3: {
                            // Xóa thông tin sinh viên theo ID
                            deleteStudent(scanner);
                            break;
                        }
                        case 4: {
                            // Tìm kiếm theo tên gần đúng
                            findStudentById(scanner);
                            break;
                        }
                    }
                } while (choice1 != 0);
            } else {
                System.err.println("Bạn không có quyền truy cập vào chức năng này!");
            }
        } else {
            System.err.println("Sai tên tài khoản hoặc mật khẩu");
        }
    }

    private void findStudentById(Scanner scanner) {
        System.out.println("Nhập tên sinh viên cần tìm: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        List<Student> studentList = studentManagement.findStudent(name);

        for (int i = 0; i < studentList.size(); i++) {
            System.out.println("---------");
            System.out.println("ID: " + studentList.get(i).getId());
            System.out.println("Tên: " + studentList.get(i).getName());
            System.out.println("Ngày tháng năm sinh: " + studentList.get(i).getDob());
            System.out.println("Địa chỉ: " + studentList.get(i).getAddress());
        }
    }

    private void deleteStudent(Scanner scanner) {
        System.out.println("Nhập ID sinh viên cần xóa");
        scanner.nextLine();
        String id = scanner.nextLine();
        boolean isSuccess = studentManagement.deleteStudent(id);
        if (isSuccess) {
            System.out.println("Xóa thành công với sinh viên có id là " + id);
        } else {
            System.err.println("Xóa không thành công với sinh viên có id là " + id);
        }
    }

    private void addNewStudent(Scanner scanner) {
        Student student = showMenuAddStudent(scanner);
        boolean isSuccess = studentManagement.addNewStudent(student);
        if (isSuccess) {
            System.out.println("Thêm mới sinh viên thành công");
        } else {
            System.err.println("Thêm mới sinh viên không thành công");
        }
    }

    private Student showMenuAddStudent(Scanner scanner) {

        // Nhận vào tên sinh viên
        System.out.println("Nhập tên sinh viên: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        // Nhận vào ngày tháng năm sinh
        System.out.println("Nhập ngày tháng năm sinh: ");
        String dob = scanner.nextLine();

        // Nhận vào mã sinh viên
        System.out.println("Nhập mã sinh viên: ");
        String studentCode = scanner.nextLine();

        // Nhận vào địa chỉ sinh viên
        System.out.println("Nhập địa chỉ: ");
        String address = scanner.nextLine();

        // Tạo mới Student theo các giá trị trên
        return new Student(name, studentCode, dob, address);
    }

    private void showAllStudent() {
        // Lấy ra danh sách sinh viên trong databse
        List<Student> studentList = studentManagement.getAllStudent();

        // Nếu danh sách trống thì hiển thị
        if (studentList.isEmpty()) {
            System.err.println("Danh sách sinh viên trống");
        }

        // Nếu có danh sách sinh viên
        for (int i = 0; i < studentList.size(); i++) {
            System.out.println("---------");
            System.out.println("ID: " + studentList.get(i).getId());
            System.out.println("Tên: " + studentList.get(i).getName());
            System.out.println("Ngày tháng năm sinh: " + studentList.get(i).getDob());
            System.out.println("Địa chỉ: " + studentList.get(i).getAddress());
        }
    }

    public void isRegister(Scanner scanner) {
        boolean usernameExist;
        String username;
        do {
            System.out.println("Nhập username: ");
            scanner.nextLine();
            username = scanner.nextLine();

            // Kiểm tra xem username có tồn tại trong hệ thống hay không
            usernameExist = userManagement.checkUsername(username);
        } while (usernameExist);

        System.out.println("Nhập password: ");
        String password = scanner.nextLine();

        String password2;
        do {
            System.out.println("Nhập lại password: ");
            password2 = scanner.nextLine();
        } while (!password.equals(password2));

        UserProfile userProfile = new UserProfile(username, password, "USER");
        boolean isSuccess = userManagement.register(userProfile);
        if (isSuccess) {
            System.out.println("Đăng kí tài khoản thành công");
        } else {
            System.err.println("Có lỗi xảy ra khi đăng kí tài khoản");
        }
    }

    private void menuLogin() {
        System.out.println("-----------Menu Login-----------|");
        System.out.println("1. Đăng nhập--------------------|");
        System.out.println("2. Đăng ký----------------------|");
        System.out.println("0. Exit-------------------------|");
        System.out.println("--------------------------------|");
    }

    private static void showMenuStudent(String username) {
        System.out.println("Bạn đang đăng nhập với tài khoản : " + username);
        System.out.println("----------------Menu Student---------------|");
        System.out.println("1. Hiển thị danh sách sinh viên------------|");
        System.out.println("2. Thêm thông tin sinh viên----------------|");
        System.out.println("3. Xóa thông tin sinh viên-----------------|");
        System.out.println("4. Tìm sinh viên theo tên------------------|");
        System.out.println("0. Đăng xuất-------------------------------|");
        System.out.println("-------------------------------------------|");
    }

}
