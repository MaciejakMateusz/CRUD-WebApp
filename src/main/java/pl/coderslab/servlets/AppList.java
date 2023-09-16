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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "AppList", urlPatterns = "/app/list")
public class AppList extends HttpServlet {

    private static final String LIST_URL = "/app/list.jsp";
    private static final int RECORDS_PER_PAGE = 10;
    private User foundById;
    private List<User> foundByEmail;
    private String findEmail;
    private Integer findId;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDao userDao = new UserDao();



        int sessionUserId = (int) session.getAttribute("user");
        User sessionUser = userDao.read(sessionUserId);
        if (sessionUser.isAdmin()) {

            // Method used to handle the search forms
            doGetSearchFormsHandling(request);

            // Calculating number of pages and creating an array based on the number of pages
            // userDao.totalUsers() returns int with number of users (using COUNT in query for faster processing)
            int numberOfPages = (int) Math.ceil((double) userDao.totalUsers() / RECORDS_PER_PAGE);
            int[] pages = new int[numberOfPages];
            for (int i = 0; i < pages.length; i++) {
                pages[i] = i + 1;
            }

            // If total number of pages is greater than 5 it will create and array of 5 pages and pass largeDB
            // parameter so the /app/list.jsp file can render additional buttons
            boolean isLargerThan5 = pages.length > 5;
            if (isLargerThan5) {
                int[] fivePages = {1, 2, 3, 4, 5};
                request.setAttribute("fivePages", fivePages);
                request.setAttribute("largeDB", true);
            }

            // If the "page" parameter is null, it will set session attribute "page" to 1.
            // It is used, if the user enters the website for the first time.
            String stringPage = request.getParameter("page");
            if(Objects.isNull(session.getAttribute("page"))) {
                session.setAttribute("page", 1);
            }

            // The page variable cannot be null as it is set when the user logs in
            Integer page = (Integer) session.getAttribute("page");

            if(Objects.nonNull(stringPage) && !stringPage.isEmpty()) {
                page = Parser.parse(stringPage, request, response);
                int offset = (page - 1) * RECORDS_PER_PAGE;
                ArrayList<User> users = userDao.getTenRecords(RECORDS_PER_PAGE, offset);
                request.setAttribute("users", users);
                session.setAttribute("page", page);
            } else {
                int offset = (page - 1) * RECORDS_PER_PAGE;
                ArrayList<User> users = userDao.getTenRecords(RECORDS_PER_PAGE, offset);
                request.setAttribute("users", users);
                session.setAttribute("page", page);
            }


            request.setAttribute("numberOfPages", numberOfPages);
            request.setAttribute("pages", pages);
            request.setAttribute("isAdmin", true);
            getServletContext().getRequestDispatcher(LIST_URL).forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/app/show");
        }

    }

    private void doGetSearchFormsHandling(HttpServletRequest request) {

        // ----- SEARCH BY ID FORM HANDLING -----
        if (Objects.nonNull(foundById)) {
            request.setAttribute("userFound", true);
            request.setAttribute("foundUser", foundById);
            foundById = null;
        }

        // ----- SEARCH BY EMAIL FORM HANDLING -----
        if (Objects.nonNull(foundByEmail)) {
            if(foundByEmail.size() >= 50) {
                request.setAttribute("wideResult", true);
            } else if (foundByEmail.isEmpty()) {
                request.setAttribute("userNotFound", true);
            }

            request.setAttribute("usersFound", true);
            request.setAttribute("foundUsers", foundByEmail);
            foundByEmail = null;
        }

        if(Objects.nonNull(findId)) {
            request.setAttribute("findId", findId);
            findId = null;
        }

        if(Objects.nonNull(findEmail)) {
            request.setAttribute("findEmail", findEmail);
            findEmail = null;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDao userDao = new UserDao();

        // ----- LIST NAVIGATION NEXT/PREVIOUS BUTTONS -----

        String incrementPageString = request.getParameter("incrementPage");
        String decrementPageString = request.getParameter("decrementPage");

        if (Objects.nonNull(decrementPageString)) {
            HttpSession session = request.getSession();
            int page = Parser.parse(decrementPageString, request, response);
            session.setAttribute("page", page);

            if (page != 1) {
                page--;
            }

            session.setAttribute("page", page);
            response.sendRedirect(request.getContextPath() + "/app/list?page=" + page);
        }

        if (Objects.nonNull(incrementPageString)) {
            HttpSession session = request.getSession();
            int page = Parser.parse(incrementPageString, request, response);
            session.setAttribute("page", page);

            int numberOfPages = (int) Math.ceil((double) userDao.totalUsers() / RECORDS_PER_PAGE);

            if (page != numberOfPages) {
                page++;
            }

            session.setAttribute("page", page);
            response.sendRedirect(request.getContextPath() + "/app/list?page=" + page);
        }

        // ----- SEARCH FORMS HANDLING -----

        String idString = request.getParameter("findId");
        String email = request.getParameter("findEmail");

        doPostSearchFormsHandling(request, response, userDao, idString, email);

    }

    private void doPostSearchFormsHandling(HttpServletRequest request, HttpServletResponse response, UserDao userDao, String idString, String email) throws ServletException, IOException {

        if("".equals(idString) || "".equals(email)) {
            response.sendRedirect(request.getContextPath() + "/app/list");
            return;
        }


        if (Objects.nonNull(idString)) {
            findId = Parser.parse(idString, request, response);
            int id = Parser.parse(idString, request, response);
            if (userDao.isFoundById(id)) {
                foundById = userDao.read(id);
                response.sendRedirect(request.getContextPath() + "/app/list");
            } else {
                request.setAttribute("userNotFound", true);
                findId = null;
                request.getRequestDispatcher("/app/list.jsp").forward(request, response);
                return;
            }
        }

        if(Objects.nonNull(email)) {
            findEmail = email;
            List<User> matchingUsers = userDao.matchingUsers(email);
            if(Objects.nonNull(matchingUsers)) {
                foundByEmail = matchingUsers;
                response.sendRedirect(request.getContextPath() + "/app/list");
            } else {
                foundByEmail = null;
                findEmail = null;
                request.setAttribute("userNotFound", true);
                request.getRequestDispatcher("/app/list.jsp").forward(request, response);
            }
        }

    }
}