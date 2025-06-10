package tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class GetStudentAction extends Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");

        String studentIdParam = request.getParameter("student_id");
        StringBuilder json = new StringBuilder("{");

        try {
            int studentId = Integer.parseInt(studentIdParam);
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kokushimusou");
            try (Connection con = ds.getConnection();
                 PreparedStatement st = con.prepareStatement("SELECT name FROM student WHERE id = ?")) {
                st.setInt(1, studentId);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name").replace("\"", "\\\""); // JSONエスケープ
                    json.append("\"name\":\"").append(name).append("\"");
                } else {
                    json.append("\"error\":\"生徒が見つかりません\"");
                }
            }
        } catch (Exception e) {
            json.append("\"error\":\"エラーが発生しました\"");
        }

        json.append("}");
        response.getWriter().write(json.toString());
        return null;
    }
}