package pl.coderslab.users;

import pl.coderslab.utils.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet(name = "UserShow", urlPatterns = "/user/show")
public class UserShow extends HttpServlet {
    private static final String URL_SHOW = "/users/show.jsp";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = 0;
        if (Objects.nonNull(request.getParameter("id"))) {
            id = Integer.parseInt(request.getParameter("id"));
        }


        UserDao userDao = new UserDao();
        ArrayList<User> users = userDao.findAll();

        if (Objects.isNull(users)) {
            request.setAttribute("userNotFound", true);
            getServletContext().getRequestDispatcher(URL_SHOW).forward(request, response);
            return;
        }

        for (User user : users) {
            if (user.getId() == id) {
                request.setAttribute("user", user);
                getServletContext().getRequestDispatcher(URL_SHOW).forward(request, response);
                return;
            }
        }

        request.setAttribute("userNotFound", true);
        getServletContext().getRequestDispatcher(URL_SHOW).forward(request, response);
    }
}