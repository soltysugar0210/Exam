package tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class GetSubjectsAction extends Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");

        String schoolIdParam = request.getParameter("school_id");
        StringBuilder json = new StringBuilder("{");

        try {
            int schoolId = Integer.parseInt(schoolIdParam);
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kokushimusou");
            try (Connection con = ds.getConnection();
                 PreparedStatement st = con.prepareStatement("SELECT id, subject_name FROM subject WHERE school_id = ?")) {
                st.setInt(1, schoolId);
                ResultSet rs = st.executeQuery();

                json.append("\"subjects\":[");
                boolean first = true;
                while (rs.next()) {
                    if (!first) json.append(",");
                    first = false;
                    String name = rs.getString("subject_name").replace("\"", "\\\""); // JSONエスケープ
                    json.append("{\"id\":").append(rs.getInt("id"))
                        .append(",\"name\":\"").append(name).append("\"}");
                }
                json.append("]");
            }
        } catch (Exception e) {
            json.append("\"error\":\"エラーが発生しました\"");
        }

        json.append("}");
        response.getWriter().write(json.toString());
        return null;
    }
}