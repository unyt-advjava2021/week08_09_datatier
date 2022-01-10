package com.eltonb.datatier.jdbc.intro;

import org.apache.derby.jdbc.ClientDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class IntroJDBC {
    private Properties properties;

    public IntroJDBC() throws IOException {
        try(InputStream is = new FileInputStream("resources/application.properties")) {
            properties = new Properties();
            properties.load(is);
        }
    }

    public void go() throws SQLException {
        runSelectAll();
        runUpdate(1);
        runSelectAll();
        runInsert(6, "Albert", "Camus", "acamus@unyt.edu.al", "CS");
        runSelectAll();
        runDelete(6);
        runSelectAll();
    }

    private void runInsert(int id, String name, String surname, String email, String deptCode) throws SQLException {
        Connection conn = newConnection();
        String sql = "insert into students(id, name, surname, email, department_code) values (?, ?, ?, ?, ?)";
        CallableStatement stat = conn.prepareCall(sql);
        stat.setInt(1, id);
        stat.setString(2, name);
        stat.setString(3, surname);
        stat.setString(4, email);
        stat.setString(5, deptCode);
        boolean result = stat.execute();
        System.out.println("result: " + result);
    }

    private void runDelete(int id) throws SQLException {
        Connection conn = newConnection();
        String sql = "delete from students where id = ?";
        CallableStatement stat = conn.prepareCall(sql);
        stat.setInt(1, id);
        boolean result = stat.execute();
        System.out.println("result: " + result);
    }

    private void runUpdate(int id) throws SQLException {
        Connection conn = newConnection();
        String sql = "update students set name = 'Franz' where id = ?";
        CallableStatement stat = conn.prepareCall(sql);
        stat.setInt(1, id);
        boolean result = stat.execute();
        System.out.println("result: " + result);
    }

    private void runSelectAll() throws SQLException {
        Connection conn = newConnection();
        PreparedStatement stat = conn.prepareStatement("select * from students");
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String deptCode = rs.getString("department_code");
            String email = rs.getString("email");
            System.out.printf("%10d %20s %20s %8s, %s\n", id, name, surname, deptCode, email);
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
