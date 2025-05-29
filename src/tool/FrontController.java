package tool;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* 「～.action」ときたら動作するコントローラーを作成します
サーブレットはこの一つだけで、あとはActionクラスの子クラス
に設定しておきます */

@WebServlet(urlPatterns = {"*.action"})
public class FrontController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String path = request.getServletPath().substring(1); // 例: login.action
            String name = path.replace(".a", "A").replace('/', '.'); // loginAction
            Action action = (Action) Class.forName(name)
                    .getDeclaredConstructor().newInstance();

            String url = action.execute(request, response);
            if (url != null && !response.isCommitted()) {
                request.getRequestDispatcher(url).forward(request, response);
            }


        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
