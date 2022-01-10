package com.eltonb.datatier.jdbc.dao.impl;

import com.eltonb.datatier.jdbc.dao.interfaces.DepartmentDao;
import com.eltonb.datatier.jdbc.dao.model.Department;
import com.eltonb.datatier.jdbc.dao.model.Instructor;

import java.util.List;

public class DepartmentDaoTextFileImpl implements DepartmentDao {
    @Override
    public List<Instructor> getInstructors(Department department) {
        return null;
    }

    @Override
    public Instructor getChair(Department department) {
        return null;
    }

    @Override
    public Department find(String key) {
        return null;
    }

    @Override
    public List<Department> findAll() {
        return null;
    }

    @Override
    public void insert(Department department) {

    }

    @Override
    public void update(Department department) {

    }

    @Override
    public void delete(Department department) {

    }
}
