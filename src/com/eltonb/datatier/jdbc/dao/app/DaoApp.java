package com.eltonb.datatier.jdbc.dao.app;

import com.eltonb.datatier.jdbc.dao.factory.DaoFactory;
import com.eltonb.datatier.jdbc.dao.interfaces.DepartmentDao;
import com.eltonb.datatier.jdbc.dao.interfaces.InstructorDao;
import com.eltonb.datatier.jdbc.dao.model.Department;
import com.eltonb.datatier.jdbc.dao.model.Instructor;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DaoApp {

    private Properties properties;
    private InstructorDao instructorDao;
    private DepartmentDao departmentDao;

    public static void main(String[] args) {
        try {
            DaoApp app = new DaoApp();
            //app.go1();
            app.go2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DaoApp() {
        properties = new Properties();
        try(InputStream is = new FileInputStream("resources/application.properties")) {
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void go1() {
        instructorDao = DaoFactory.createInstructorDao();
        departmentDao = DaoFactory.createDepartmentDao();

        seed();
        departmentDao.findAll().forEach(System.out::println);
        instructorDao.findAll().forEach(System.out::println);
        Instructor bengio = new Instructor(5, "Yoshua", "Bengio", "CS");
        instructorDao.insert(bengio);

        Department cs = departmentDao.find("CS");

        departmentDao.getInstructors(cs).stream().map(i->i.getName() + " " + i.getSurname()).forEach(System.out::println);


        instructorDao.delete(bengio);
        instructorDao.findAll().forEach(System.out::println);
    }

    private void seed() {
        Department cs = new Department();
        cs.setCode("CS");
        cs.setFacultyCode("ENG");
        cs.setName("Computer Sciences");

        Department ee = new Department();
        ee.setCode("EE");
        ee.setFacultyCode("ENG");
        ee.setName("Electronic Engineering");

        Department phys = new Department();
        phys.setCode("PHYS");
        phys.setFacultyCode("NSC");
        phys.setName("Physics");

        departmentDao.insert(cs);
        departmentDao.insert(ee);
        departmentDao.insert(phys);

        Instructor turing = new Instructor(1, "Alan", "Turing", "CS");
        Instructor shannon = new Instructor(2, "Claude", "Shannon", "EE");
        Instructor hawking = new Instructor(3, "Steven", "Hawking", "PHYS");
        Instructor church = new Instructor(4, "Alonzo", "Church", "CS");

        instructorDao.insert(turing);
        instructorDao.insert(shannon);
        instructorDao.insert(hawking);
        instructorDao.insert(church);

        cs.setChairId(turing.getId());
        departmentDao.update(cs);

        ee.setChairId(shannon.getId());
        departmentDao.update(ee);

        phys.setChairId(hawking.getId());
        departmentDao.update(phys);
    }

    private void go2() throws SQLException {
        try (Connection conn = newConnection()) {
            System.out.println(conn.getClass().getName());
            instructorDao = DaoFactory.createInstructorDao(conn);
            departmentDao = DaoFactory.createDepartmentDao(conn);
            instructorDao.findAll().forEach(System.out::println);
            /*
            Department chem = new Department();
            chem.setCode("CHEM");
            chem.setFacultyCode("NS");
            chem.setName("Chemistry");
            departmentDao.insert(chem);
            departmentDao.findAll().forEach(System.out::println);
            Instructor chemistryChair = departmentDao.getChair(chem);
            System.out.println(chemistryChair);
            */
        }
    }

    private Connection newConnection() throws SQLException {
        //not needed with JDBC 4 drivers
        //DriverManager.registerDriver(new ClientDriver());
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String pass = properties.getProperty("db.pass");
        return DriverManager.getConnection(url, user, pass);
    }


}
