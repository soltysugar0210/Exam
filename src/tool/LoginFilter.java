package tool;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        boolean isStaticResource = uri.matches(".*(\\.jsp|\\.html|\\.css|\\.js|\\.png|\\.jpg|\\.gif)$");
        boolean isLoggedIn = (session != null && session.getAttribute("loginUser") != null);
        boolean isLoginRequest = uri.endsWith("Login.action");
        boolean isLoginPage = uri.endsWith("index.jsp");

        if (isLoggedIn || isLoginPage || isLoginRequest || isStaticResource || uri.endsWith("error.jsp")) {
            chain.doFilter(req, res);
        } else {
            System.out.println("LoginFilter: Redirecting to " + contextPath + "/index.jsp for URI = " + uri);
            response.sendRedirect(contextPath + "/index.jsp");
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {}
    public void destroy() {}
}