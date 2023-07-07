package pl.coderslab.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = "/app/logout")
public class LogoutFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String logout = request.getParameter("logout");

        if (Objects.nonNull(logout)) {
            if (logout.equals("true")) {
                HttpSession session = request.getSession();
                session.removeAttribute("user");

                Cookie[] cookies = request.getCookies();
                if (Objects.nonNull(cookies)) {
                    for (Cookie cookie : cookies) {
                        if ("rememberMe".equals(cookie.getName())) {
                            cookie.setPath("/");
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                            break;
                        }
                    }
                }

                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}