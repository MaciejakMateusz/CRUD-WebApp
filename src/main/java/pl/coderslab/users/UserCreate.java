package pl.coderslab.users;

import pl.coderslab.utils.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

@WebServlet(name = "UserCreate", urlPatterns = "/user/create")
public class UserCreate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/users/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (Objects.nonNull(name) && Objects.nonNull(email) && Objects.nonNull(password)) {
            UserDao userDao = new UserDao();
            User user = new User(email, name, password);
            userDao.create(user);
            request.setAttribute("created", true);
            getServletContext().getRequestDispatcher("/users/create.jsp").forward(request, response);
        }

    }

}