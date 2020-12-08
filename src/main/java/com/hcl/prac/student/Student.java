package com.hcl.prac.student;

import java.io.Serializable;

public class Student implements Comparable<Student>, Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int id;
    private String city;

    public Student(String name, int id, String city) {
        this.name = name;
        this.id = id;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Student [city=" + city + ", id=" + id + ", name=" + name + "]";
    }

    @Override
    public int compareTo(Student o) {
        return this.id - o.id;
    }

}
