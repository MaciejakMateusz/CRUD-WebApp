package pl.coderslab.servlets;

import pl.coderslab.model.User;
import pl.coderslab.utils.Parser;
import pl.coderslab.dao.UserDao;
import pl.coderslab.utils.ValidateData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@WebServlet(name = "AppEdit", urlPatterns = "/app/edit")
public class AppEdit extends HttpServlet {

    private static final String EDIT_URL = "/app/edit.jsp";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private User user;
    private boolean userAlreadyExist;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(Objects.isNull(user)) {
            response.sendRedirect(request.getContextPath() + "/app/list");
            return;
        }

        request.setAttribute("user", user);
        getServletContext().getRequestDispatcher(EDIT_URL).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Parser.parse(request.getParameter("id"), request, response);
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repeatedPassword = request.getParameter("repeatedPassword");

        // passing list page number to request attribute, it is used if user
        // wants to go back from edit page to list page he entered from
        HttpSession session = request.getSession();
        int page = (int) session.getAttribute("page");
        request.setAttribute("page", page);

        UserDao userDao = new UserDao();
        user = userDao.read(id);

        boolean validUserName = false;
        if(Objects.nonNull(name)) {
            validUserName = ValidateData.validateUserName(name);
        }

        boolean validEmail = false;
        if(Objects.nonNull(email)) {
            validEmail = ValidateData.validateEmail(email);
        }

        boolean validPassword;
        boolean passwordNotNullOrEmpty = Objects.nonNull(password) && !"".equals(password);
        if(passwordNotNullOrEmpty) {
            validPassword = ValidateData.validatePassword(password);
            request.setAttribute("password", password);
        } else {
            validPassword = true;
        }

        // the reason of not checking if password is null is that if
        // user doesn't want to edit password, he can just leave the field empty
        // and the program will not update the password
        if(Objects.nonNull(name) && Objects.nonNull(email)) {
            AppAdd.formValidation(request, name, email, password, validUserName, validEmail, validPassword, user);
        }

        if(passwordNotNullOrEmpty) {
            if (!password.equals(repeatedPassword)) {
                request.setAttribute("user", user);
                request.setAttribute("passwordMatch", false);
                getServletContext().getRequestDispatcher(EDIT_URL).forward(request, response);
                return;
            }
        }


        // ----- EXECUTING UPDATE -----
        if (validUserName && validEmail && validPassword) {

            user.setLastEdited(DTF.format(LocalDateTime.now()));
            user.setUserName(name);
            user.setEmail(email);
            // Here the password parameter is checked. If null or empty, it will not update
            if(passwordNotNullOrEmpty) {
                user.setPassword(password);
            }

            // Original, pre-update user is created here using ID from parameter to check if email
            // or username equals to the updated user.
            User originalUser = userDao.read(id);
            userExistsValidation(request, name, email, userDao, originalUser);

            request.setAttribute("user", user);
            if(!userAlreadyExist) {
                request.setAttribute("updated", true);
            }
            getServletContext().getRequestDispatcher(EDIT_URL).forward(request, response);
        }
        request.setAttribute("user", user);
        getServletContext().getRequestDispatcher(EDIT_URL).forward(request, response);
    }

    private void userExistsValidation(HttpServletRequest request, String name, String email, UserDao userDao, User originalUser) {
        if(!userDao.isFoundByUsername(name)) {
            userDao.update(user);
            userAlreadyExist = false;
        } else {
            request.setAttribute("user", user);
            if(!originalUser.getUserName().equals(user.getUserName())) {
                userAlreadyExist = true;
                request.setAttribute("nameExists", true);
            }
        }

        if(!userDao.isFoundByEmail(email)) {
            userDao.update(user);
            userAlreadyExist = false;
        } else {
            request.setAttribute("user", user);
            if(!originalUser.getEmail().equals(user.getEmail())) {
                userAlreadyExist = true;
                request.setAttribute("emailExists", true);
            }
        }
    }

}