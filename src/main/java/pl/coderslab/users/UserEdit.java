package pl.coderslab.users;

import pl.coderslab.utils.UserDao;
import pl.coderslab.utils.ValidateData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "UserEdit", urlPatterns = "/user/edit")
public class UserEdit extends HttpServlet {

    private static final String URL_EDIT = "/users/edit.jsp";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        int id = 0;
        if (Objects.nonNull(request.getParameter("id"))) {
            id = Integer.parseInt(request.getParameter("id"));
        }

        UserDao userDao = new UserDao();
        User user = userDao.read(id);

        request.setAttribute("name", user.getUserName());
        request.setAttribute("email", user.getEmail());
        request.setAttribute("password", user.getPassword());
        request.setAttribute("id", id);
        getServletContext().getRequestDispatcher(URL_EDIT).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String stringId = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        int id;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            request.setAttribute("userNotFound", true);
            getServletContext().getRequestDispatcher("/users/show.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setId(id);

        boolean validUserName = ValidateData.validateUserName(name);
        boolean validEmail = ValidateData.validateEmail(email);
        boolean validPassword = ValidateData.validatePassword(password);

        UserAdd.formValidation(request, name, email, password, validUserName, validEmail, validPassword, user);

        user.setLastEdited(DTF.format(LocalDateTime.now()));

        // EXECUTING UPDATE:
        if (validUserName && validEmail && validPassword) {

            UserDao userDao = new UserDao();
            userDao.update(user);

            request.setAttribute("name", user.getUserName());
            request.setAttribute("email", user.getEmail());
            request.setAttribute("password", user.getPassword());
            request.setAttribute("updated", true);
            getServletContext().getRequestDispatcher(URL_EDIT).forward(request, response);
        }
        getServletContext().getRequestDispatcher(URL_EDIT).forward(request, response);
    }

}