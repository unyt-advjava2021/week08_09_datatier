package com.eltonb.datatier.jdbc.dao.impl;

import com.eltonb.datatier.jdbc.dao.factory.DaoFactory;
import com.eltonb.datatier.jdbc.dao.interfaces.InstructorDao;
import com.eltonb.datatier.jdbc.dao.utils.JdbcUtils;
import com.eltonb.datatier.jdbc.dao.interfaces.DepartmentDao;
import com.eltonb.datatier.jdbc.dao.model.Department;
import com.eltonb.datatier.jdbc.dao.model.Instructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJdbcImpl implements DepartmentDao {

    private Connection connection;

    public DepartmentDaoJdbcImpl() {
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Department find(String code) {
        String sql = "select * from departments d where d.code = ?";
        try (PreparedStatement stat = connection.prepareStatement(sql)) {
            stat.setString(1, code);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next())
                    return JdbcUtils.newDepartment(rs);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Department findOne(String code) throws Exception {
        String sql = "select * from departments d where d.code = '" + code + "'";
        Statement stat = connection.createStatement();
        ResultSet rs = stat.executeQuery(sql);
        Department d = null;
        if (rs.next()) {
            d = new Department();
            d.setCode(rs.getString("code"));
            d.setName(rs.getString("name"));
        }
        stat.close();
        connection.close();
        return d;
    }

    @Override
    public List<Department> findAll() {
        String sql = "select * from departments d";
        try (PreparedStatement stat = connection.prepareStatement(sql)) {
            try (ResultSet rs = stat.executeQuery()) {
                List<Department> departments = new ArrayList<>();
                while (rs.next())
                    departments.add(JdbcUtils.newDepartment(rs));
                return departments;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Department department) {
        String sql = "insert into departments(code, name, faculty_code, chair_id) values (?, ?, ?, ?)";
        try (CallableStatement stat = connection.prepareCall(sql)) {
            stat.setString(1, department.getCode());
            stat.setString(2, department.getName());
            stat.setString(3, department.getFacultyCode());
            if (department.getChairId() <= 0)
                stat.setNull(4, Types.INTEGER);
            else
                stat.setLong(4, department.getChairId());
            stat.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Department department) {
        String sql = "update departments set name=?, faculty_code=?, chair_id=? where code = ?";
        try (CallableStatement stat = connection.prepareCall(sql)) {
            stat.setString(1, department.getName());
            stat.setString(2, department.getFacultyCode());
            stat.setLong(3, department.getChairId());
            stat.setString(4, department.getCode());
            stat.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Department department) {
        String sql = "delete from departments where code = ?";
        try (CallableStatement stat = connection.prepareCall(sql)) {
            stat.setString(1, department.getCode());
            stat.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Instructor> getInstructors(Department department) {
        String sql = "select * from instructors i where i.department_code = ?";
        try (PreparedStatement stat = connection.prepareStatement(sql)) {
            stat.setString(1, department.getCode());
            try (ResultSet rs = stat.executeQuery()) {
                List<Instructor> instructors = new ArrayList<>();
                while (rs.next())
                    instructors.add(JdbcUtils.newInstructor(rs));
                return instructors;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Instructor getChair(Department department) {
        InstructorDao instructorDao = DaoFactory.createInstructorDao(connection);
        return instructorDao.find(department.getChairId());
    }


}
