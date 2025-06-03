import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import tool.Action;

public class GetClassesAction extends Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        StringBuilder json = new StringBuilder();
        try {
            int schoolId = Integer.parseInt(request.getParameter("school_id"));
            List<String> classNames = new ArrayList<>();
            // JNDIデータソースを使用
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kokushimusou");
            try (Connection con = ds.getConnection();
                 PreparedStatement st = con.prepareStatement(
                     "SELECT class_name FROM school_class WHERE school_id = ? ORDER BY class_name")) {
                st.setInt(1, schoolId);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    classNames.add(rs.getString("class_name"));
                }
            }
            json.append("{\"classNames\":[");
            for (int i = 0; i < classNames.size(); i++) {
                json.append("\"").append(classNames.get(i).replace("\"", "\\\"")).append("\"");
                if (i < classNames.size() - 1) json.append(",");
            }
            json.append("]}");
        } catch (Exception e) {
            e.printStackTrace();
            json.append("{\"error\":\"無効な学校コードです。\"}");
        }
        response.getWriter().write(json.toString());
        return null;
    }
}