package Tomcat.home;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import Tomcat.utils.ThymeleafUtil;

import java.io.IOException;

@WebServlet("/home")
public class HomePage extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        ThymeleafUtil.process("home3-4.html",context,resp.getWriter());
//        if (req.getSession().getAttribute("login-failure") != null) {
//            context.setVariable("failure",true);
//            req.getSession().removeAttribute("login-failure");
//
//        }
//        if (req.getSession().getAttribute("user") != null) {
//            resp.sendRedirect("index");
//            return;
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
