package pl.coderslab.servlets;

import pl.coderslab.model.User;
import pl.coderslab.utils.Parser;
import pl.coderslab.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "AppShow", urlPatterns = "/app/show")
public class AppShow extends HttpServlet {
    private static final String URL_SHOW = "/app/show.jsp";
    private User user;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (Objects.nonNull(user)) {
            request.setAttribute("user", user);
            getServletContext().getRequestDispatcher(URL_SHOW).forward(request, response);
        } else {
            HttpSession session = request.getSession();
            int id = (int) session.getAttribute("user");
            UserDao userDao = new UserDao();
            User user = userDao.read(id);
            request.setAttribute("user", user);
            getServletContext().getRequestDispatcher(URL_SHOW).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Parser.parse(request.getParameter("id"), request, response);
        UserDao userDao = new UserDao();

        boolean isFoundById = userDao.isFoundById(id);
        if (isFoundById) {
            this.user = userDao.read(id);
            request.setAttribute("user", user);
            getServletContext().getRequestDispatcher(URL_SHOW).forward(request, response);
        } else {
            request.setAttribute("userNotFound", true);
            getServletContext().getRequestDispatcher(URL_SHOW).forward(request, response);
        }

    }
}