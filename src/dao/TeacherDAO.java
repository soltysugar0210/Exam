package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class TeacherDAO {
    private Connection getConnection() throws Exception {
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kokushimusou");
        return ds.getConnection();
    }

    public boolean existsTeacher(int teacherId) throws SQLException, Exception {
        String sql = "SELECT COUNT(*) FROM user WHERE id = ? AND privilege > 0";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, teacherId);
            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }
}