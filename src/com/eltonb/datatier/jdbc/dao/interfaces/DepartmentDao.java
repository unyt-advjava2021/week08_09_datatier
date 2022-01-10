package com.eltonb.datatier.jdbc.dao.interfaces;

import com.eltonb.datatier.jdbc.dao.model.Department;
import com.eltonb.datatier.jdbc.dao.model.Instructor;

import java.util.List;

public interface DepartmentDao extends BaseDao<Department, String> {
    List<Instructor> getInstructors(Department department);
    Instructor getChair(Department department);
}
