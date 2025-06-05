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

public class UserbeanAction extends tool.Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        UserBean user = (session != null) ? (UserBean) session.getAttribute("loginUser") : null;
        if (user == null) {
            request.setAttribute("error", "ログインしてください。");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return null;
        }

        int privilege = 0;
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kokushimusou");
        try (Connection con = ds.getConnection();
             PreparedStatement st = con.prepareStatement("SELECT privilege FROM user WHERE id = ?")) {
            st.setInt(1, user.getId());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                privilege = rs.getInt("privilege");
            } else {
                request.setAttribute("error", "ユーザ情報が見つかりません。");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return null;
            }
        } catch (SQLException e) {
            request.setAttribute("error", "権限の確認に失敗しました。");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return null;
        }

        if (privilege == 0) {
            request.setAttribute("error", "この操作を行う権限がありません。");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return null;
        }

        String idParam = request.getParameter("id");
        String schoolIdParam = request.getParameter("school_id");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String privilegeParam = request.getParameter("privilege");

        try {
            int id = Integer.parseInt(idParam);
            int schoolId = Integer.parseInt(schoolIdParam);
            int newPrivilege = Integer.parseInt(privilegeParam);

            if (name == null || name.isEmpty() || password == null || password.isEmpty()) {
                request.setAttribute("error", "必須項目（名前、パスワード）を入力してください。");
                return "teacher_add.jsp";
            }

            try (Connection con = ds.getConnection();
                 PreparedStatement st = con.prepareStatement(
                     "INSERT INTO user (id, school_id, password, name, privilege) VALUES (?, ?, ?, ?, ?)")) {
                st.setInt(1, id);
                st.setInt(2, schoolId);
                st.setString(3, password); // 注意: 実運用ではハッシュ化推奨
                st.setString(4, name);
                st.setInt(5, newPrivilege);
                st.executeUpdate();
                request.setAttribute("success", "教師を登録しました。");
                return "teacher_add.jsp";
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID、学校コード、権限は数値を入力してください。");
            return "teacher_add.jsp";
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                request.setAttribute("error", "このIDはすでに登録されています。");
            } else {
                request.setAttribute("error", "教師登録に失敗しました。");
            }
            return "teacher_add.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            return "teacher_add.jsp";
        }
    }
}