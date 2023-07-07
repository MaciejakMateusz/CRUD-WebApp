package pl.coderslab.filters;

import pl.coderslab.model.User;
import pl.coderslab.dao.UserDao;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = "/app/*")
public class SessionAttributeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        Object obj = session.getAttribute("user");
        if (Objects.nonNull(obj)) {
            int id = (int) session.getAttribute("user");
            UserDao userDao = new UserDao();
            User sessionUser = userDao.read(id);

            request.setAttribute("sessionUser", sessionUser);
            chain.doFilter(request, response);
        }

    }
}