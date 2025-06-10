

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

import bean.GradeBean;
import bean.UserBean;
import dao.GradeDAO;
import dao.TeacherDAO;

public class GradesystemAction extends tool.Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        UserBean user = (session != null) ? (UserBean) session.getAttribute("loginUser") : null;
        if (user == null) {
            request.setAttribute("error", "ログインしてください。");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return null;
        }

        // 権限チェック
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

        // CSRFトークン検証
        String formCsrfToken = request.getParameter("csrfToken");
        String sessionCsrfToken = (String) session.getAttribute("csrfToken");
        if (formCsrfToken == null || !formCsrfToken.equals(sessionCsrfToken)) {
            request.setAttribute("error", "不正なリクエストです。");
            return "grade_add.jsp";
        }
        session.removeAttribute("csrfToken"); // トークン使用後削除

        // フォームデータ取得
        String schoolIdParam = request.getParameter("school_id");
        String studentIdParam = request.getParameter("student_id");
        String subjectIdParam = request.getParameter("subject_id");
        String countParam = request.getParameter("count");
        String scoreParam = request.getParameter("score");
        int teacherId = user.getId(); // ログイン教師のID

        try {
            int schoolId = Integer.parseInt(schoolIdParam);
            int studentId = Integer.parseInt(studentIdParam);
            int subjectId = Integer.parseInt(subjectIdParam);
            int count = Integer.parseInt(countParam);
            int score = Integer.parseInt(scoreParam);

            if (count < 1) {
                request.setAttribute("error", "回数は1以上の値を入力してください。");
                return "grade_add.jsp";
            }
            if (score < 0 || score > 100) {
                request.setAttribute("error", "点数は0から100の範囲で入力してください。");
                return "grade_add.jsp";
            }

            // 生徒と科目の存在確認
            String studentName = null;
            String subjectName = null;
            try (Connection con = ds.getConnection()) {
                // 生徒確認
                PreparedStatement stStudent = con.prepareStatement("SELECT name FROM student WHERE id = ? AND school_id = ?");
                stStudent.setInt(1, studentId);
                stStudent.setInt(2, schoolId);
                ResultSet rsStudent = stStudent.executeQuery();
                if (!rsStudent.next()) {
                    request.setAttribute("error", "指定された学生番号または学校コードは存在しません。");
                    return "grade_add.jsp";
                }
                studentName = rsStudent.getString("name");

                // 科目確認
                PreparedStatement stSubject = con.prepareStatement("SELECT subject_name FROM subject WHERE id = ? AND school_id = ?");
                stSubject.setInt(1, subjectId);
                stSubject.setInt(2, schoolId);
                ResultSet rsSubject = stSubject.executeQuery();
                if (!rsSubject.next()) {
                    request.setAttribute("error", "指定された科目は存在しません。");
                    return "grade_add.jsp";
                }
                subjectName = rsSubject.getString("subject_name");
            }

            // 教師確認
            TeacherDAO teacherDAO = new TeacherDAO();
            if (!teacherDAO.existsTeacher(teacherId)) {
                request.setAttribute("error", "教師情報が無効です。");
                return "grade_add.jsp";
            }

            // GradeBean設定
            GradeBean grade = new GradeBean();
            grade.setStudentId(studentId);
            grade.setStudentName(studentName);
            grade.setSubjectId(subjectId);
            grade.setSubjectName(subjectName);
            grade.setCount(count);
            grade.setScore(score);

            // 成績登録
            GradeDAO gradeDAO = new GradeDAO();
            gradeDAO.insertGrade(grade);

            request.setAttribute("success", "成績を登録しました。");
            request.setAttribute("grade", grade);
            return "grade_add.jsp";

        } catch (NumberFormatException e) {
            request.setAttribute("error", "学校コード、学生番号、科目、回数、点数は数値を入力してください。");
            return "grade_add.jsp";
        } catch (SQLException e) {
            if (e.getSQLState().equals("23503")) {
                request.setAttribute("error", "無効な学生番号または科目IDです。");
            } else {
                request.setAttribute("error", "成績登録に失敗しました。");
            }
            return "grade_add.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            return "grade_add.jsp";
        }
    }
}