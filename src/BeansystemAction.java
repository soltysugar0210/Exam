import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import bean.UserBean;

public class BeansystemAction extends tool.Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        UserBean user = (session != null) ? (UserBean) session.getAttribute("loginUser") : null;
        System.out.println("BeansystemAction: loginUser=" + (user != null ? user.getName() : "null"));
        if (user == null) {
            request.setAttribute("error", "ログインしてください。");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return null;
        }

        String idParam = request.getParameter("id");
        String schoolIdParam = request.getParameter("school_id");
        String className = request.getParameter("class_name");
        String name = request.getParameter("name");
        String entranceYearParam = request.getParameter("entrance_year");

        try {
            int id = Integer.parseInt(idParam);
            int schoolId = Integer.parseInt(schoolIdParam);
            int entranceYear = Integer.parseInt(entranceYearParam);

            if (name == null || name.isEmpty() || className == null || className.isEmpty()) {
                request.setAttribute("error", "必須項目を入力してください。");
                return "register.jsp";
            }

            // JNDIデータソースを使用
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kokushimusou");
            try (Connection con = ds.getConnection();
                 PreparedStatement st = con.prepareStatement(
                     "SELECT COUNT(*) FROM school_class WHERE school_id = ? AND class_name = ?")) {
                st.setInt(1, schoolId);
                st.setString(2, className);
                ResultSet rs = st.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) {
                    request.setAttribute("error", "指定された学校コードまたはクラスは存在しません。");
                    return "register.jsp";
                }
            }

            try (Connection con = ds.getConnection();
                 PreparedStatement st = con.prepareStatement(
                     "INSERT INTO student (id, school_id, class_name, name, entrance_year, student_flag) VALUES (?, ?, ?, ?, ?, ?)")) {
                st.setInt(1, id);
                st.setInt(2, schoolId);
                st.setString(3, className);
                st.setString(4, name);
                st.setInt(5, entranceYear);
                st.setBoolean(6, true);
                st.executeUpdate();
                request.setAttribute("success", "生徒を登録しました。");
                return "register.jsp";
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "学生番号、学校コード、入学年は数値を入力してください。");
            return "register.jsp";
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                request.setAttribute("error", "この学生番号はすでに登録されています。");
            } else {
                request.setAttribute("error", "登録に失敗しました。");
            }
            return "register.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            return "register.jsp";
        }
    }
}