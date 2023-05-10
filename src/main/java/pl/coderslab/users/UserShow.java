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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String StringId = request.getParameter("id");

        int id = 0;
        try {
            id = Integer.parseInt(StringId);
        } catch (NumberFormatException e) {
            request.setAttribute("userNotFound", true);
            getServletContext().getRequestDispatcher("/users/list.jsp").forward(request, response);
        }

        UserDao userDao = new UserDao();
        User user = userDao.read(id);

        request.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/users/show.jsp").forward(request, response);
    }
}