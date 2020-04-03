package com.example.collegeevent.Lokesh;
public class MemberStudent {
    private String student_name;
    private String student_USN;
    private String student_phone_number;
    private String student_semester;
    private String student_branch;
    private String student_email;
    private String student_password;
    private String student_user_Id;
    private String user_type;

    public MemberStudent() {
    }

    public MemberStudent(String student_name, String student_USN, String student_phone_number, String student_semester, String student_branch, String student_email, String student_password, String student_user_Id, String user_type) {
        this.student_name = student_name;
        this.student_USN = student_USN;
        this.student_phone_number = student_phone_number;
        this.student_semester = student_semester;
        this.student_branch = student_branch;
        this.student_email = student_email;
        this.student_password = student_password;
        this.student_user_Id = student_user_Id;
        this.user_type = user_type;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_USN() {
        return student_USN;
    }

    public void setStudent_USN(String student_USN) {
        this.student_USN = student_USN;
    }

    public String getStudent_phone_number() {
        return student_phone_number;
    }

    public void setStudent_phone_number(String student_phone_number) {
        this.student_phone_number = student_phone_number;
    }

    public String getStudent_semester() {
        return student_semester;
    }

    public void setStudent_semester(String student_semester) {
        this.student_semester = student_semester;
    }

    public String getStudent_branch() {
        return student_branch;
    }

    public void setStudent_branch(String student_branch) {
        this.student_branch = student_branch;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }

    public String getStudent_password() {
        return student_password;
    }

    public void setStudent_password(String student_password) {
        this.student_password = student_password;
    }

    public String getStudent_user_Id() {
        return student_user_Id;
    }

    public void setStudent_user_Id(String student_user_Id) {
        this.student_user_Id = student_user_Id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}