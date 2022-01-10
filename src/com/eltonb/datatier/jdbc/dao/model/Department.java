package com.eltonb.datatier.jdbc.dao.model;

import java.util.Objects;

public class Department {

    private String code;
    private String name;
    private String facultyCode;
    private long chairId;

    public Department() {

    }

    public Department(String code, String name, String facultyCode, long chairId) {
        this();
        this.code = code;
        this.name = name;
        this.facultyCode = facultyCode;
        this.chairId = chairId;
    }

    @Override
    public String toString() {
        return "Department{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", facultyCode='" + facultyCode + '\'' +
                ", chairId=" + chairId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return chairId == that.chairId &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(facultyCode, that.facultyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, facultyCode, chairId);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    public long getChairId() {
        return chairId;
    }

    public void setChairId(long chairId) {
        this.chairId = chairId;
    }
}
