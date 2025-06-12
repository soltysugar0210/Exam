

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.StulistBean;
import dao.StudentDAO;

public class StudentListAction extends tool.Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            StudentDAO studentDAO = new StudentDAO();
            List<StulistBean> students = studentDAO.getAllStudents();
            request.setAttribute("students", students);
            return "student_output.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "生徒データの取得に失敗しました。");
            return "student_output.jsp";
        }
    }
}