package pl.coderslab.users;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.utils.UserDao;
import pl.coderslab.utils.ValidateData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserLogin", urlPatterns = "/user/login")
public class UserLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/users/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDao userDao = new UserDao();
        User user = new User();

        boolean validEmail = ValidateData.validateEmail(email);
        if (validEmail) {
            user = userDao.readByEmail(email);
        } else {
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("invalidInput", true);
            getServletContext().getRequestDispatcher("/users/login.jsp").forward(request, response);
        }

        if (BCrypt.checkpw(password, user.getPassword())) {
            response.sendRedirect("/user/list");
        } else {
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("invalidInput", true);
            getServletContext().getRequestDispatcher("/users/login.jsp").forward(request, response);
        }

    }
}