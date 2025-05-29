import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.StulistBean;
import dao.StulistDAO;
import tool.Action;

public class StudentsystemAction extends Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        StulistBean Sl = (StulistBean) request.getAttribute("Sl");

        try {
            StulistDAO dao = new StulistDAO();
            dao.insert(Sl);

            return "output.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            return "error.jsp";
        }
    }
}
