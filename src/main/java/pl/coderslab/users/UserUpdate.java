package pl.coderslab.users;

import pl.coderslab.utils.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "UserEdit", urlPatterns = "/user/update")
public class UserUpdate extends HttpServlet {

    private static final Pattern USERNAME_REGEX =
            Pattern.compile("^\\S{5,255}$");
    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.([a-zA-Z]{2,})$");
    private static final Pattern PASSWORD_REGEX =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{5,60}$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String stringId = request.getParameter("id");

        int id = 0;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            request.setAttribute("userNotFound", true);
            getServletContext().getRequestDispatcher("/users/show.jsp").forward(request, response);
            return;
        }

        UserDao userDao = new UserDao();
        User user = userDao.read(id);

        request.setAttribute("name", user.getUserName());
        request.setAttribute("email", user.getEmail());
        request.setAttribute("password", user.getPassword());
        request.setAttribute("id", id);
        getServletContext().getRequestDispatcher("/users/update.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String stringId = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        int id = 0;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            request.setAttribute("userNotFound", true);
            getServletContext().getRequestDispatcher("/users/show.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setId(id);

        boolean validUserName = validateUserName(name);
        boolean validEmail = validateEmail(email);
        boolean validPassword = validatePassword(password);

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

        if (Objects.nonNull(password)) {
            if (validPassword) {
                user.setPassword(password);
                request.setAttribute("password", password);
            } else {
                request.setAttribute("validPassword", false);
            }
        }

        // EXECUTING UPDATE:
        if(validUserName && validEmail && validPassword) {

            UserDao userDao = new UserDao();
            userDao.update(user);

            request.setAttribute("name", user.getUserName());
            request.setAttribute("email", user.getEmail());
            request.setAttribute("password", user.getPassword());
            request.setAttribute("updated", true);
            getServletContext().getRequestDispatcher("/users/update.jsp").forward(request, response);
        }
        getServletContext().getRequestDispatcher("/users/create.jsp").forward(request, response);
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

}