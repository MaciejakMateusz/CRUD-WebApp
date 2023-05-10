package pl.coderslab.users;

import pl.coderslab.utils.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "UserShow", urlPatterns = "/user/show")
public class UserShow extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String stringId = request.getParameter("id");

        UserDao userDao = new UserDao();
        ArrayList<User> users = userDao.findAll();

        int id = 0;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            request.setAttribute("userNotFound", true);
            getServletContext().getRequestDispatcher("/users/show.jsp").forward(request, response);
            return;
        }

        for (User user : users) {
            if (user.getId() == id) {
                request.setAttribute("user", user);
                getServletContext().getRequestDispatcher("/users/show.jsp").forward(request, response);
                return;
            }
        }

        request.setAttribute("userNotFound", true);
        getServletContext().getRequestDispatcher("/users/show.jsp").forward(request, response);
    }
}