package com.example.collegeevent.Lokesh;

public class MemberTeacher {
    private String teacher_name;
    private String teacher_unique_id;
    private String teacher_phone_number;
    private String teacher_specialisation;
    private String teacher_branch;
    private String teacher_designation;
    private String teacher_email;
    private String teacher_password;
    private String teacher_user_Id;
    private String teacher_verified;
    private String user_type;// student, teacher, admin or super admin

    public MemberTeacher() {
    }

    public MemberTeacher(String teacher_name, String teacher_unique_id, String teacher_phone_number, String teacher_specialisation, String teacher_branch, String teacher_designation, String teacher_email, String teacher_password, String teacher_user_Id, String teacher_verified, String user_type) {
        this.teacher_name = teacher_name;
        this.teacher_unique_id = teacher_unique_id;
        this.teacher_phone_number = teacher_phone_number;
        this.teacher_specialisation = teacher_specialisation;
        this.teacher_branch = teacher_branch;
        this.teacher_designation = teacher_designation;
        this.teacher_email = teacher_email;
        this.teacher_password = teacher_password;
        this.teacher_user_Id = teacher_user_Id;
        this.teacher_verified = teacher_verified;
        this.user_type = user_type;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_unique_id() {
        return teacher_unique_id;
    }

    public void setTeacher_unique_id(String teacher_unique_id) {
        this.teacher_unique_id = teacher_unique_id;
    }

    public String getTeacher_phone_number() {
        return teacher_phone_number;
    }

    public void setTeacher_phone_number(String teacher_phone_number) {
        this.teacher_phone_number = teacher_phone_number;
    }

    public String getTeacher_specialisation() {
        return teacher_specialisation;
    }

    public void setTeacher_specialisation(String teacher_specialisation) {
        this.teacher_specialisation = teacher_specialisation;
    }

    public String getTeacher_branch() {
        return teacher_branch;
    }

    public void setTeacher_branch(String teacher_branch) {
        this.teacher_branch = teacher_branch;
    }

    public String getTeacher_designation() {
        return teacher_designation;
    }

    public void setTeacher_designation(String teacher_designation) {
        this.teacher_designation = teacher_designation;
    }

    public String getTeacher_email() {
        return teacher_email;
    }

    public void setTeacher_email(String teacher_email) {
        this.teacher_email = teacher_email;
    }

    public String getTeacher_password() {
        return teacher_password;
    }

    public void setTeacher_password(String teacher_password) {
        this.teacher_password = teacher_password;
    }

    public String getTeacher_user_Id() {
        return teacher_user_Id;
    }

    public void setTeacher_user_Id(String teacher_user_Id) {
        this.teacher_user_Id = teacher_user_Id;
    }

    public String getTeacher_verified() {
        return teacher_verified;
    }

    public void setTeacher_verified(String teacher_verified) {
        this.teacher_verified = teacher_verified;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
