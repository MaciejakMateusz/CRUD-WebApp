package pl.coderslab.users;

import pl.coderslab.utils.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "UserCreate", urlPatterns = "/user/create")
public class UserCreate extends HttpServlet {

    private static final Pattern USERNAME_REGEX =
            Pattern.compile("^\\S{5,255}$");
    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.([a-zA-Z]{2,})$");
    private static final Pattern PASSWORD_REGEX =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{5,20}$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/users/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User();
        boolean validUserName = validateUserName(name);
        boolean validEmail = validateEmail(email);
        boolean validPassword = validatePassword(password);

        formValidation(request, name, email, password, validUserName, validEmail, validPassword, user);

        if (validUserName && validEmail && validPassword) {

            UserDao userDao = new UserDao();
            userDao.create(user);

            request.setAttribute("userName", "");
            request.setAttribute("email", "");
            request.setAttribute("password", "");
            request.setAttribute("created", true);
            getServletContext().getRequestDispatcher("/users/create.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/users/create.jsp").forward(request, response);
        }


    }

    private static boolean validateUserName(String userName) {
        Matcher matcher = USERNAME_REGEX.matcher(userName);
        return matcher.matches();
    }

    private static boolean validateEmail(String email) {
        Matcher matcher = EMAIL_REGEX.matcher(email);
        return matcher.matches();
    }

    private static boolean validatePassword(String password) {
        Matcher matcher = PASSWORD_REGEX.matcher(password);
        return matcher.matches();
    }

    static void formValidation(HttpServletRequest request, String name, String email, String password, boolean validUserName, boolean validEmail, boolean validPassword, User user) {
        if (validUserName) {
            user.setUserName(name);
            request.setAttribute("userName", name);
        } else {
            request.setAttribute("validUserName", false);
        }

        if (validEmail) {
            user.setEmail(email);
            request.setAttribute("email", email);
        } else {
            request.setAttribute("validEmail", false);
        }

        if (validPassword) {
            user.setPassword(password);
            request.setAttribute("password", password);
        } else {
            request.setAttribute("validPassword", false);
        }
    }

}