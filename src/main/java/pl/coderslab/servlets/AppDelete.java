package pl.coderslab.servlets;

import pl.coderslab.model.User;
import pl.coderslab.utils.Parser;
import pl.coderslab.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "AppDelete", urlPatterns = "/app/delete")
public class AppDelete extends HttpServlet {

    private static final String DELETE_URL = "/app/delete.jsp";
    private User user;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (Objects.isNull(user)) {
            response.sendRedirect(request.getContextPath() + "/app/list");
            return;
        }

        request.setAttribute("user", user);
        request.setAttribute("deleted", false);
        getServletContext().getRequestDispatcher(DELETE_URL).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        int page = (int) session.getAttribute("page");
        request.setAttribute("page", page);

        UserDao userDao = new UserDao();

        String confirmation = request.getParameter("isConfirmed");
        if (Objects.isNull(confirmation)) {

            String stringId = request.getParameter("id");
            int id = -1;
            if (Objects.nonNull(stringId)) {
                id = Parser.parse(stringId, request, response);
            }

            if (userDao.isFoundById(id)) {
                user = userDao.read(id);
                request.setAttribute("user", user);
                request.setAttribute("deleted", false);
                getServletContext().getRequestDispatcher(DELETE_URL).forward(request, response);
            } else {
                request.setAttribute("userNotFound", true);
                getServletContext().getRequestDispatcher("/app/show.jsp").forward(request, response);
                return;
            }
        }

        boolean isConfirmed = false;
        if (Objects.nonNull(confirmation)) {
            isConfirmed = Boolean.parseBoolean(confirmation);
        }

        // if confirmation is true this code will perform process of removing user from the database
        if (isConfirmed) {

            int sessionUserId = (int) session.getAttribute("user");

            User sessionUser = userDao.read(sessionUserId);
            // if user is not admin logout will be performed causing
            // deleting cookie and session of deleted user (cookie and session is deleted via LogoutFilter)
            if (!sessionUser.isAdmin()) {
                userDao.delete(user.getId());
                response.sendRedirect(request.getContextPath() + "/app/logout?logout=true");
                return;
            }
            userDao.delete(user.getId());
            request.setAttribute("deleted", true);
            getServletContext().getRequestDispatcher(DELETE_URL).forward(request, response);
        } else if (Objects.nonNull(confirmation)) {
            response.sendRedirect(request.getContextPath() + "/app/list");
        }
    }

}
