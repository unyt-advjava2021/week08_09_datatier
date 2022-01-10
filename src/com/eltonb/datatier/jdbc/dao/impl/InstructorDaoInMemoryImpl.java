package com.eltonb.datatier.jdbc.dao.impl;

import com.eltonb.datatier.jdbc.dao.factory.DaoFactory;
import com.eltonb.datatier.jdbc.dao.interfaces.InstructorDao;
import com.eltonb.datatier.jdbc.dao.model.Department;
import com.eltonb.datatier.jdbc.dao.model.Instructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstructorDaoInMemoryImpl implements InstructorDao {
    private Map<Long, Instructor> table;

    public InstructorDaoInMemoryImpl() {
        table = new HashMap<>();
    }

    @Override
    public Department getDepartment(Instructor instructor) {
        return DaoFactory
                .createDepartmentDao()
                .find(instructor.getDepartmentCode());
    }

    @Override
    public Instructor find(Long key) {
        Instructor instructor = table.get(key);
        if (instructor == null)
            throw new IllegalArgumentException("no such instructor: " + key);
        return instructor;
    }

    @Override
    public List<Instructor> findAll() {
        return new ArrayList<>(table.values());
    }

    @Override
    public void insert(Instructor instructor) {
        Long key = instructor.getId();
        if (table.containsKey(key))
            throw new IllegalArgumentException("already existing instructor: " + key);
        table.put(key, instructor);
    }

    @Override
    public void update(Instructor instructor) {
        Long key = instructor.getId();
        if ( table.containsKey(key) )
            table.put(key, instructor);
    }

    @Override
    public void delete(Instructor instructor) {
        table.remove(instructor.getId());
    }
}
