package pl.coderslab.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(urlPatterns = "/app/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        if (session != null) {

            Enumeration<String> attributeNames = session.getAttributeNames();

            boolean userAttributeFound = false;

            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();

                if ("user".equals(attributeName)) {
                    userAttributeFound = true;
                    break;
                }
            }

            if (userAttributeFound) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }

        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

}