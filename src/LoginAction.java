import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LoginDAO;
import tool.Action;

public class LoginAction extends Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            LoginDAO dao = new LoginDAO();
            boolean isValid = dao.checkLogin(username, password);

            if (isValid) {
                request.getSession().setAttribute("loginUser", username);
                response.sendRedirect("menu.jsp");
                return null;

            } else {
                request.setAttribute("error", "ユーザー名またはパスワードが間違っています。");
                return "index.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            return "index.jsp";
        }
    }
}


