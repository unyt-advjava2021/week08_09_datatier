package com.eltonb.datatier.jdbc.dao.utils;

import com.eltonb.datatier.jdbc.dao.model.Department;
import com.eltonb.datatier.jdbc.dao.model.Instructor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtils {
    public static Department newDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setCode(rs.getString("code"));
        department.setName(rs.getString("name"));
        department.setFacultyCode(rs.getString("faculty_code"));
        department.setChairId(rs.getLong("chair_id"));
        return department;
    }

    public static Instructor newInstructor(ResultSet rs) throws SQLException {
        Instructor instructor = new Instructor();
        instructor.setId(rs.getLong("id"));
        instructor.setName(rs.getString("name"));
        instructor.setSurname(rs.getString("surname"));
        instructor.setDepartmentCode(rs.getString("department_code"));
        return instructor;
    }

}
