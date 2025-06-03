import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import dao.LoginDAO;
import tool.Action;

public class LoginAction extends Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String idParam = request.getParameter("id");
        String password = request.getParameter("password");

        if (idParam == null || idParam.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "IDとパスワードを入力してください。");
            return "index.jsp";
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "IDは数値で入力してください。");
            return "index.jsp";
        }

        try {
            LoginDAO dao = new LoginDAO();
            UserBean user = dao.checkLogin(id, password);

            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("loginUser", user);
                System.out.println("LoginAction: Set loginUser = " + user.getName() + ", Session ID = " + session.getId());
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/menu.jsp");
                return null;
            } else {
                request.setAttribute("error", "IDまたはパスワードが間違っています。");
                return "index.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            return "index.jsp";
        }
    }
}