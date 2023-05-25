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
import java.util.ArrayList;

@WebServlet(name = "UserRegister", urlPatterns = "/user/register")
public class UserRegister extends HttpServlet {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String URL_REGISTER = "/users/register.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/users/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String reEnteredPassword = request.getParameter("reEnteredPassword");

        boolean validUserName = ValidateData.validateUserName(name);
        boolean validEmail = ValidateData.validateEmail(email);
        boolean validPassword = ValidateData.validatePassword(password);

        User user = new User();
        UserAdd.formValidation(request, name, email, password, validUserName, validEmail, validPassword, user);

        user.setCreationDate(DTF.format(LocalDateTime.now()));
        user.setLastEdited(DTF.format(LocalDateTime.now()));


        if(!password.equals(reEnteredPassword)) {
            request.setAttribute("passwordMatch", false);
            getServletContext().getRequestDispatcher(URL_REGISTER).forward(request, response);
            return;
        }

        if (validUserName && validEmail && validPassword) {

            UserDao userDao = new UserDao();
            ArrayList<User> users = userDao.findAll();

            boolean usernameExists = false;
            for (User checkUser : users) {
                if (checkUser.getUserName().equals(name)) {
                    usernameExists = true;
                    request.setAttribute("usernameExists", true);
                    break;
                }
            }

            boolean emailExists = false;
            for (User checkUser : users) {
                if (checkUser.getEmail().equals(email)) {
                    emailExists = true;
                    request.setAttribute("emailExists", true);
                    break;
                }
            }

            if (!usernameExists && !emailExists) {
                userDao.create(user);
                request.setAttribute("created", true);
            }
            getServletContext().getRequestDispatcher(URL_REGISTER).forward(request, response);
        } else {
            getServletContext().getRequestDispatcher(URL_REGISTER).forward(request, response);
        }

    }
}