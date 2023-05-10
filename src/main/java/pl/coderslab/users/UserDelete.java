package pl.coderslab.users;

import pl.coderslab.utils.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserDelete", urlPatterns = "/user/delete")
public class UserDelete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String stringId = request.getParameter("id");

        int id;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            request.setAttribute("userNotFound", true);
            getServletContext().getRequestDispatcher("/users/show.jsp").forward(request, response);
            return;
        }

        UserDao userDao = new UserDao();
        userDao.delete(id);

        response.sendRedirect("/user/list");
    }
}