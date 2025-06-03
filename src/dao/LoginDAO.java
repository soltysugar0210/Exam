package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.UserBean;

public class LoginDAO extends DAO {
    public UserBean checkLogin(int id, String password) throws Exception {
        UserBean user = null;
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(
                 "SELECT * FROM users WHERE id = ? AND password = ?")) {
            st.setInt(1, id);
            st.setString(2, password);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    user = new UserBean(
                        rs.getInt("id"),
                        rs.getInt("school_id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getInt("privilege")
                    );
                }
            }
        }
        return user;
    }
}