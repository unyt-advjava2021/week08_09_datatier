package com.eltonb.datatier.jdbc.dao.impl;

import com.eltonb.datatier.jdbc.dao.factory.DaoFactory;
import com.eltonb.datatier.jdbc.dao.interfaces.DepartmentDao;
import com.eltonb.datatier.jdbc.dao.interfaces.InstructorDao;
import com.eltonb.datatier.jdbc.dao.model.Department;
import com.eltonb.datatier.jdbc.dao.model.Instructor;

import java.util.*;
import java.util.stream.Collectors;

public class DepartmentDaoInMemoryImpl implements DepartmentDao {

    private Map<String, Department> table;

    public DepartmentDaoInMemoryImpl() {
        table = new HashMap<>();
    }


    @Override
    public List<Instructor> getInstructors(Department department) {
        InstructorDao instructorDao = DaoFactory.createInstructorDao();
        return instructorDao.findAll()
                            .stream()
                            .filter(i -> i.getDepartmentCode().equals(department.getCode()))
                            .collect(Collectors.toList());
    }

    @Override
    public Instructor getChair(Department department) {
        InstructorDao instructorDao = DaoFactory.createInstructorDao();
        return instructorDao.findAll()
                .stream()
                .filter(i -> i.getId() == department.getChairId())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Chair not found for department " + department.getCode()));
    }

    @Override
    public Department find(String key) {
        Department department = table.get(key);
        if (department == null)
            throw new IllegalArgumentException("no such department: " + key);
        return department;
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(table.values());
    }

    @Override
    public void insert(Department department) {
        String key = department.getCode();
        if (table.containsKey(key))
            throw new IllegalArgumentException("already existing department: " + key);
        table.put(key, department);
    }

    @Override
    public void update(Department department) {
        String key = department.getCode();
        if ( table.containsKey(key) )
            table.put(key, department);
    }

    @Override
    public void delete(Department department) {
        table.remove(department.getCode());
    }
}
