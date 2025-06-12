package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.StulistBean;

public class StudentDAO {
    private Connection getConnection() throws Exception {
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kokushimusou");
        return ds.getConnection();
    }

    public List<StulistBean> getAllStudents() throws SQLException, Exception {
        List<StulistBean> students = new ArrayList<>();
        String sql = "SELECT id, school_id, class_name, name, entrance_year, student_flag FROM student ORDER BY id";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                StulistBean student = new StulistBean();
                student.setId(rs.getInt("id"));
                student.setSchool_id(rs.getInt("school_id"));
                student.setClass_name(rs.getString("class_name"));
                student.setName(rs.getString("name"));
                student.setEntrance_year(rs.getInt("entrance_year"));
                student.setStudent_flag(rs.getBoolean("student_flag"));
                students.add(student);
            }
        }
        return students;
    }
}