package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.GradeBean;

public class GradeDAO {
    private Connection getConnection() throws Exception {
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kokushimusou");
        return ds.getConnection();
    }

    public void insertGrade(GradeBean grade) throws SQLException, Exception {
        String sql = "INSERT INTO grade (student_id, subject_id, count, score) VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, grade.getStudentId());
            st.setInt(2, grade.getSubjectId());
            st.setInt(3, grade.getCount());
            st.setInt(4, grade.getScore());
            st.executeUpdate();
        }
    }
}