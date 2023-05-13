package pl.coderslab.users;

import pl.coderslab.utils.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

@WebServlet(name = "UserDelete", urlPatterns = "/user/delete")
public class UserDelete extends HttpServlet {

    private static final String URL_DELETE = "/users/delete.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String stringId = request.getParameter("id");

        int id;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            request.setAttribute("userNotFound", true);
            getServletContext().getRequestDispatcher("/users/show.jsp").forward(request, response);
            return;
        }

        UserDao userDao = new UserDao();
        ArrayList<User> users = userDao.findAll();

        for (User user : users) {
            if (user.getId() == id) {
                request.setAttribute("user", user);
                request.setAttribute("deleted", false);
                getServletContext().getRequestDispatcher(URL_DELETE).forward(request, response);
                return;
            }
        }

        request.setAttribute("userNotFound", true);
        getServletContext().getRequestDispatcher("/users/show.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        int id = Integer.parseInt(request.getParameter("id"));
        boolean isConfirmed = Boolean.parseBoolean(request.getParameter("isConfirmed"));


        if (isConfirmed) {
            UserDao userDao = new UserDao();
            userDao.delete(id);
            request.setAttribute("deleted", true);
            getServletContext().getRequestDispatcher(URL_DELETE).forward(request, response);
        } else {
            response.sendRedirect("/user/list");
        }

    }
}