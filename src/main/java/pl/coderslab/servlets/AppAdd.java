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

@WebServlet(name = "AppAdd", urlPatterns = "/app/add")
public class AppAdd extends HttpServlet {

    private static final String ADD_URL = "/app/add.jsp";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(ADD_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repeatedPassword = request.getParameter("repeatedPassword");

        User user = new User();
        boolean validUserName = ValidateData.validateUserName(name);
        boolean validEmail = ValidateData.validateEmail(email);
        boolean validPassword = ValidateData.validatePassword(password);
        request.setAttribute("password", password);

        if (!password.equals(repeatedPassword)) {
            request.setAttribute("userName", name);
            request.setAttribute("email", email);
            request.setAttribute("passwordMatch", false);
            getServletContext().getRequestDispatcher(ADD_URL).forward(request, response);
            return;
        }

        formValidation(request, name, email, password, validUserName, validEmail, validPassword, user);

        user.setCreationDate(DTF.format(LocalDateTime.now()));
        user.setLastEdited(DTF.format(LocalDateTime.now()));


        if (validUserName && validEmail && validPassword) {

            UserDao userDao = new UserDao();
            userDao.create(user);

            request.setAttribute("userName", "");
            request.setAttribute("email", "");
            request.setAttribute("password", "");
            request.setAttribute("created", true);
            getServletContext().getRequestDispatcher(ADD_URL).forward(request, response);
        } else {
            getServletContext().getRequestDispatcher(ADD_URL).forward(request, response);
        }

    }

    static void formValidation(HttpServletRequest request,
                               String name,
                               String email,
                               String password,
                               boolean validUserName,
                               boolean validEmail,
                               boolean validPassword,
                               User user) {
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
            if("".equals(password)) {
                request.setAttribute("password", "");
            } else {
                user.setPassword(password);
                request.setAttribute("password", password);
            }

        } else {
            request.setAttribute("validPassword", false);
        }
    }

}