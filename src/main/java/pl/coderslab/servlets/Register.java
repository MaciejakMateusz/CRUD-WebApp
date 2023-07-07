package pl.coderslab.servlets;

import pl.coderslab.model.User;
import pl.coderslab.dao.UserDao;
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

@WebServlet(name = "Register", urlPatterns = "/register")
public class Register extends HttpServlet {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String URL_REGISTER = "/register.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String admin = request.getParameter("admin");

        if (Objects.nonNull(admin)) {
            if (admin.equals("667560")) {
                getServletContext().getRequestDispatcher("/registerAdmin.jsp").forward(request, response);
            }
        }
        getServletContext().getRequestDispatcher(URL_REGISTER).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repeatedPassword = request.getParameter("repeatedPassword");
        String admin = request.getParameter("admin");
        boolean adminRegistration = Boolean.parseBoolean(admin);

        boolean validUserName = ValidateData.validateUserName(name);
        boolean validEmail = ValidateData.validateEmail(email);
        boolean validPassword = ValidateData.validatePassword(password);

        User user = new User();
        AppAdd.formValidation(request, name, email, password, validUserName, validEmail, validPassword, user);

        if (!password.equals(repeatedPassword)) {
            request.setAttribute("passwordMatch", false);
            if (!adminRegistration) {
                getServletContext().getRequestDispatcher(URL_REGISTER).forward(request, response);
            } else {
                getServletContext().getRequestDispatcher("/registerAdmin.jsp").forward(request, response);
            }
            return;
        }

        if (validUserName && validEmail && validPassword) {

            UserDao userDao = new UserDao();

            boolean usernameExists = userDao.isFoundByUsername(user.getUserName());
            if (usernameExists) {
                request.setAttribute("usernameExists", true);
            }

            boolean emailExists = userDao.isFoundByEmail(email);
            if (emailExists) {
                request.setAttribute("emailExists", true);
            }

            if (!usernameExists && !emailExists) {
                user.setCreationDate(DTF.format(LocalDateTime.now()));
                user.setLastEdited(DTF.format(LocalDateTime.now()));

                if (adminRegistration) {
                    user.setAdmin(true);
                }

                userDao.create(user);
                request.setAttribute("created", true);
            } else {
                getServletContext().getRequestDispatcher(URL_REGISTER).forward(request, response);
            }
        }
        getServletContext().getRequestDispatcher(URL_REGISTER).forward(request, response);
    }
}