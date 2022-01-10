package com.eltonb.datatier.jdbc.dao.factory;

import com.eltonb.datatier.jdbc.dao.impl.*;
import com.eltonb.datatier.jdbc.dao.interfaces.DepartmentDao;
import com.eltonb.datatier.jdbc.dao.interfaces.InstructorDao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DaoFactory {

    static {
        String model = "memory";
        try(InputStream is = new FileInputStream("resources/application.properties")) {
            Properties props = new Properties();
            props.load(is);
            model = props.getProperty("db.factory");
        } catch (Exception e) {
            e.printStackTrace();
        }
        factoryModel = model;
    }

    private static final String factoryModel;
    private static DepartmentDaoInMemoryImpl memDepartmentDao;
    private static InstructorDaoInMemoryImpl memInstructorDao;


    public static DepartmentDao createDepartmentDao() {
        return createDepartmentDao(null);
    }

    public static DepartmentDao createDepartmentDao(Connection conn) {
        if ("jdbc".equals(factoryModel)) {
            DepartmentDaoJdbcImpl dao = new DepartmentDaoJdbcImpl();
            dao.setConnection(conn);
            return dao;
        }

        if ("textFile".equals(factoryModel)) {
            return new DepartmentDaoTextFileImpl();
        }

        if ("memory".equals(factoryModel)) {
            if (memDepartmentDao == null) {
                memDepartmentDao = new DepartmentDaoInMemoryImpl();
            }
            return memDepartmentDao;
        }

        throw new IllegalArgumentException("Illegal factory model: " + factoryModel);
    }

    public static InstructorDao createInstructorDao() {
        return createInstructorDao(null);
    }

    public static InstructorDao createInstructorDao(Connection conn) {
        if ("jdbc".equals(factoryModel)) {
            InstructorDaoJdbcImpl dao = new InstructorDaoJdbcImpl();
            dao.setConnection(conn);
            return dao;
        }

        if ("textFile".equals(factoryModel)) {
            return new InstructorsDaoTextFileImpl();
        }

        if ("memory".equals(factoryModel)) {
            if (memInstructorDao == null) {
                memInstructorDao = new InstructorDaoInMemoryImpl();
            }
            return memInstructorDao;
        }

        throw new IllegalArgumentException("Illegal factory model: " + factoryModel);
    }


    private static DepartmentDao initDepartmentDao(Connection conn) {
        if ("memory".equals(factoryModel)) {
            return new DepartmentDaoInMemoryImpl();
        } else {
            DepartmentDaoJdbcImpl dao = new DepartmentDaoJdbcImpl();
            dao.setConnection(conn);
            return dao;
        }
    }

    private static InstructorDao initInstructorDao(Connection conn) {
        if ("memory".equals(factoryModel)) {
            return new InstructorDaoInMemoryImpl();
        } else {
            InstructorDaoJdbcImpl dao = new InstructorDaoJdbcImpl();
            dao.setConnection(conn);
            return dao;
        }
    }

}
