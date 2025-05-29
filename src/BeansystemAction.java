import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.StulistBean;
import tool.Action;

public class BeansystemAction extends Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String className = request.getParameter("className");

        StulistBean Sl = new StulistBean();
        Sl.setId(id);
        Sl.setName(name);
        Sl.setClassName(className);

        request.setAttribute("Sl", Sl);

        return "Studentsystem.action";
    }
}
