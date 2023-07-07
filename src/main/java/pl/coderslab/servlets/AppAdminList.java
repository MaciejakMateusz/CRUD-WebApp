package pl.coderslab.servlets;

import pl.coderslab.model.User;
import pl.coderslab.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AppAdminList", urlPatterns = "/app/adminList")
public class AppAdminList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int id = (int) session.getAttribute("user");
        UserDao userDao = new UserDao();

        User user = userDao.read(id);

        if(user.isAdmin()) {
            List<User> admins = userDao.findAllAdmins();

            request.setAttribute("users", admins);
            request.setAttribute("isAdmin", true);
            request.getRequestDispatcher("/app/adminList.jsp").forward(request, response);
        } else {
            request.setAttribute("isAdmin", false);
            response.sendRedirect(request.getContextPath() + "/app/list");
        }

    }
}