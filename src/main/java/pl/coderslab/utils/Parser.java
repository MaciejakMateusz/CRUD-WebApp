package pl.coderslab.utils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public  class Parser {
    public static Integer parse(String paramName, ServletRequest request, ServletResponse response) throws ServletException, IOException {
        int result;
            try {
                result = Integer.parseInt(paramName);
            } catch (NumberFormatException e) {
                request.setAttribute("errorCode", e);
                request.getServletContext().getRequestDispatcher("/app/error.jsp").forward(request, response);
                return 0;
            }
        return result;
    }
}