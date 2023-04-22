package org.example.model;


public class Student {

    /**
     * ID
     */
    private Long id;

    /**
     * Tên sinh viên
     */
    private String name;

    /**
     * Mã sinh viên
     */
    private String studentCode;

    /**
     * Ngày tháng năm sinh
     */
    private String dob;

    /**
     * Địa chỉ sinh viên
     */
    private String address;


    /**
     * Contructor không tham số
     */
    public Student() {
    }

    /**
     * Contructor đầy đủ tham số
     */
    public Student(Long id, String name, String studentCode, String dob, String address) {
        this.id = id;
        this.name = name;
        this.studentCode = studentCode;
        this.dob = dob;
        this.address = address;
    }

    public Student(String name, String studentCode, String dob, String address) {
        this.name = name;
        this.studentCode = studentCode;
        this.dob = dob;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
