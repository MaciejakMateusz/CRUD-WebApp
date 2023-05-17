package pl.coderslab.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = "/*")
public class IdFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String stringId = request.getParameter("id");

        if (Objects.isNull(stringId)) {
            chain.doFilter(request, response);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            request.setAttribute("userNotFound", true);
            request.getServletContext().getRequestDispatcher("/users/show.jsp").forward(request, response);
            return;
        }

        request.setAttribute("id", id);
        chain.doFilter(request, response);
    }
}