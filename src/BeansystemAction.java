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

import bean.StulistBean;
import bean.UserBean;

public class BeansystemAction extends tool.Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        UserBean user = (session != null) ? (UserBean) session.getAttribute("loginUser") : null;
        if (user == null) {
            request.setAttribute("error", "ログインしてください。");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return null;
        }


        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kokushimusou");
        String idParam = request.getParameter("id");
        String schoolIdParam = request.getParameter("school_id");
        String className = request.getParameter("class_name");
        String name = request.getParameter("name");
        String entranceYearParam = request.getParameter("entrance_year");
        String studentFlag = request.getParameter("student_flag");

        try {
            int id = Integer.parseInt(idParam);
            int schoolId = Integer.parseInt(schoolIdParam);
            int entranceYear = Integer.parseInt(entranceYearParam);
            boolean studentFlagBool = "on".equals(studentFlag);

            if (name == null || name.isEmpty() || className == null || className.isEmpty()) {
                request.setAttribute("error", "必須項目を入力してください。");
                return "student_add.jsp";
            }

            try (Connection con = ds.getConnection();
                 PreparedStatement st = con.prepareStatement(
                     "SELECT COUNT(*) FROM school_class WHERE school_id = ? AND class_name = ?")) {
                st.setInt(1, schoolId);
                st.setString(2, className);
                ResultSet rs = st.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) {
                    request.setAttribute("error", "指定された学校コードまたはクラスは存在しません。");
                    return "student_add.jsp";
                }
            }

            StulistBean student = new StulistBean();
            student.setId(id);
            student.setSchool_id(schoolId);
            student.setClass_name(className);
            student.setName(name);
            student.setEntrance_year(entranceYear);
            student.setStudent_flag(studentFlagBool);

            try (Connection con = ds.getConnection();
                 PreparedStatement st = con.prepareStatement(
                     "INSERT INTO student (id, school_id, class_name, name, entrance_year, student_flag) VALUES (?, ?, ?, ?, ?, ?)")) {
                st.setInt(1, id);
                st.setInt(2, schoolId);
                st.setString(3, className);
                st.setString(4, name);
                st.setInt(5, entranceYear);
                st.setBoolean(6, studentFlagBool);
                st.executeUpdate();
                request.setAttribute("success", "生徒を登録しました。");
                request.setAttribute("Sl", student);
                return "register.jsp";
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "学生番号、学校コード、入学年は数値を入力してください。");
            return "student_add.jsp";
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                request.setAttribute("error", "この学生番号はすでに登録されています。");
            } else {
                request.setAttribute("error", "生徒登録に失敗しました。");
            }
            return "student_add.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            return "student_add.jsp";
        }
    }
}