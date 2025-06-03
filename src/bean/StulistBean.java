package bean;

import java.io.Serializable;

public class StulistBean implements Serializable {
    private int id;
    private int school_id;
    private String class_name;
    private String name;
    private int entrance_year;
    private boolean student_flag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEntrance_year() {
        return entrance_year;
    }

    public void setEntrance_year(int entrance_year) {
        this.entrance_year = entrance_year;
    }

    public boolean getStudent_flag() {
        return student_flag;
    }

    public void setStudent_flag(boolean student_flag) {
        this.student_flag = student_flag;
    }
}