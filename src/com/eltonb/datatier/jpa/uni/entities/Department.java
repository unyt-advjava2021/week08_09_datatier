package com.eltonb.datatier.jpa.uni.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "DEPARTMENTS", schema = "APP")
@NamedQueries({
        @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d")
        , @NamedQuery(name = "Department.findByCode", query = "SELECT d FROM Department d WHERE d.code = :code")
        , @NamedQuery(name = "Department.findByName", query = "SELECT d FROM Department d WHERE d.name = :name")
        , @NamedQuery(name = "Department.findByFacultyCode", query = "SELECT d FROM Department d WHERE d.facultyCode = :facultyCode")})
public class Department {
    private String code;
    private String name;
    private String facultyCode;
    private Instructor chair;

    private Collection<Instructor> instructors;
    private Collection<Student> students;

    @Id
    @Column(name = "CODE", nullable = false, length = 10)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "FACULTY_CODE", nullable = false, length = 5)
    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(facultyCode, that.facultyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, facultyCode);
    }

    @Override
    public String toString() {
        return "Department{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", facultyCode='" + facultyCode + '\'' +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "CHAIR_ID", referencedColumnName = "ID")
    public Instructor getChair() {
        return chair;
    }

    public void setChair(Instructor chair) {
        this.chair = chair;
    }

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Collection<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(Collection<Instructor> instructorsByCode) {
        this.instructors = instructorsByCode;
    }

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> studentsByCode) {
        this.students = studentsByCode;
    }
}
