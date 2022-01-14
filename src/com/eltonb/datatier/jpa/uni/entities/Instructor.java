package com.eltonb.datatier.jpa.uni.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "INSTRUCTORS", schema = "APP")
public class Instructor {
    private int id;
    private String name;
    private String surname;
    private Department department;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "SURNAME", nullable = true, length = 30)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instructor that = (Instructor) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname);
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }

    @ManyToOne()
    @JoinColumn(name = "DEPARTMENT_CODE", referencedColumnName = "CODE", nullable = false)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department departmentsByDepartmentCode) {
        this.department = departmentsByDepartmentCode;
    }
}
