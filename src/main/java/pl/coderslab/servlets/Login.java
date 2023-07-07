package pl.coderslab.servlets;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.model.User;
import pl.coderslab.dao.UserDao;
import pl.coderslab.utils.ValidateData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

@WebServlet(name = "Login", urlPatterns = "/login")
public class Login extends HttpServlet {

    private static final String LOGIN_URL = "/login.jsp";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();

        // This algorithm checks if a cookie with user id (rememberMe) is found:
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if ("rememberMe".equals(cookie.getName())) {
                    // if the cookie is found it checks if session named 'user' exists
                    HttpSession session = request.getSession();
                    Enumeration<String> attributeNames = session.getAttributeNames();
                    boolean sessionFound = false; // flag for checking if session was found
                    while (attributeNames.hasMoreElements()) {
                        if(attributeNames.nextElement().equals("user")) {
                            int id = (int) session.getAttribute("user");
                            sessionFound = userDao.isFoundById(id);
                        }
                    }
                    if(!sessionFound) { // if session is not found, the algorithm creates new session
                        User user = userDao.read(Integer.parseInt(cookie.getValue()));
                        session.setAttribute("user", user.getId());
                    }
                    response.sendRedirect(request.getContextPath() + "/app/list");
                    return;
                }
            }
        }

        getServletContext().getRequestDispatcher(LOGIN_URL).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        UserDao userDao = new UserDao();
        User user = new User();


        boolean validEmail = ValidateData.validateEmail(email);
        if (validEmail) {
            if(userDao.isFoundByEmail(email)) {
                user = userDao.readByEmail(email);
            } else {
                request.setAttribute("emailNotFound", true);
                request.setAttribute("email", email);
                getServletContext().getRequestDispatcher(LOGIN_URL).forward(request, response);
                return;
            }

        } else {
            request.setAttribute("email", email);
            request.setAttribute("invalidInput", true);
            request.setAttribute("password", password);
            getServletContext().getRequestDispatcher(LOGIN_URL).forward(request, response);
        }

        if (BCrypt.checkpw(password, user.getPassword())) {

            if (Objects.nonNull(rememberMe)) {
                if ("on".equals(rememberMe)) {
                    Cookie cookie = new Cookie("rememberMe", String.valueOf(user.getId()));
                    cookie.setPath("/");
                    cookie.setMaxAge(3600 * 24 * 7); // 1 week
                    response.addCookie(cookie);
                }
            }

            HttpSession session = request.getSession();
            session.setAttribute("user", user.getId());
            session.setAttribute("page", 1);
            response.sendRedirect(request.getContextPath() + "/app/list");
        } else {
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("invalidInput", true);
            getServletContext().getRequestDispatcher(LOGIN_URL).forward(request, response);
        }

    }

}