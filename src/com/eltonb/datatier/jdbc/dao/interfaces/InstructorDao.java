package com.eltonb.datatier.jdbc.dao.interfaces;

import com.eltonb.datatier.jdbc.dao.model.Department;
import com.eltonb.datatier.jdbc.dao.model.Instructor;

public interface InstructorDao extends BaseDao<Instructor, Long> {
    Department getDepartment(Instructor instructor);
}
