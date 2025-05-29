package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO extends DAO {

    public boolean checkLogin(String username, String password) throws Exception {
        boolean isValid = false;

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(
                 "SELECT * FROM users WHERE username = ? AND password = ?")) {

            st.setString(1, username);
            st.setString(2, password);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    isValid = true;
                }
            }
        }

        return isValid;
    }
}

