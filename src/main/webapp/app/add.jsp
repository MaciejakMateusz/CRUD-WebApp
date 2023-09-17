<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl">

<!-- Header -->
<%@ include file="/app/header.jsp" %>
<!-- End of Header -->

<body id="page-top">
<div id="wrapper">
    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center"
           href="${pageContext.request.contextPath}/app/list?page=1">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-laugh-wink"></i>
            </div>
            <div class="sidebar-brand-text mx-3">Witaj <c:out value='${sessionUser.userName}'/>!</div>
        </a>
        <!-- Divider -->
        <!-- Nav Item - Dashboard -->
        <li class="nav-item active">
            <hr class="sidebar-divider my-0">
            <c:choose>
                <c:when test="${!sessionUser.isAdmin()}">
                    <a class="nav-link" href="${pageContext.request.contextPath}/app/show">
                        <i class="fas fa-fw fa-tachometer-alt"></i>
                        <span style="font-size: 1.2rem">Pulpit</span>
                    </a>
                </c:when>
                <c:when test="${sessionUser.isAdmin()}">
                    <hr class="sidebar-divider">
                    <a type="s" class="nav-link" href="${pageContext.request.contextPath}/app/list?page=1">
                        <i class="fas fa-fw"></i>
                        <p style="font-size: 1.2rem">Lista użytkowników</p>
                    </a>
                    <hr class="sidebar-divider">
                    <a type="s" class="nav-link" href="${pageContext.request.contextPath}/app/adminList">
                        <i class="fas fa-fw"></i>
                        <p style="font-size: 1.2rem">Lista adminów</p>
                    </a>
                </c:when>
            </c:choose>
            <hr class="sidebar-divider">
            <a type="s" class="nav-link" href="${pageContext.request.contextPath}/app/logout?logout=true">
                <i class="fas fa-fw"></i>
                <p style="font-size: 1.2rem">Wyloguj się</p>
            </a>
        </li>
        <!-- Divider -->
    </ul>
    <!-- End of Sidebar -->
    <div id="content-wrapper" class="d-flex flex-column">
        <!-- Main Content -->
        <div id="content">
            <!-- Topbar -->
            <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
                <!-- Sidebar Toggle (Topbar) -->
                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                    <i class="fa fa-bars"></i>
                </button>
            </nav>
            <!-- End of Topbar -->
            <!-- Begin Page Content -->
            <div class="container-fluid">
                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <form style="all: unset;"
                          action="${pageContext.request.contextPath}/app/list">
                        <button
                                type="submit"
                                style="outline: none;"
                                class="button-list"
                                name="page"
                                value="${page}">
                            <span style="font-size: 1.1rem">Powrót</span>
                        </button>
                    </form>
                </div>
                <!-- /.container-fluid -->
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Dodaj użytkownika</h6>
                    </div>
                    <div class="card-body">
                        <form method="post">
                            <div class="form-group">
                                <label for="name">Nazwa <br>
                                    <input type="text" name="name" placeholder="Nazwa użytkownika" value="<c:out value='${userName}'/>"
                                           class="form-control" id="name">
                                </label>
                                <c:if test="${validUserName==false}">
                                    <p class="validation" style="font-size: 0.7rem">
                                        Nazwa użytkownika musi składać się z
                                        minimum 5 znaków</p>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label for="email">Email <br>
                                    <input type="email"
                                           name="email"
                                           placeholder="Email użytkownika"
                                           value="<c:out value='${email}'/>"
                                           class="form-control" id="email">
                                </label>
                                <c:if test="${validEmail==false}">
                                    <p class="validation" style="font-size: 0.7rem">Niepoprawny email</p>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label for="password">Hasło <br>
                                    <input type="password"
                                           name="password"
                                           class="form-control"
                                           placeholder="Hasło użytkownika"
                                           value="${password}"
                                           id="password">
                                </label>
                                <c:if test="${validPassword==false}">
                                    <p class="validation" style="font-size: 0.7rem">Hasło musi posiadać: <br> - od 5 do
                                        20 znaków<br> - minimum 1 małą literę<br> - minimum 1 dużą literę<br> - minimum
                                        jeden znak specjalny</p>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label for="repeatedPassword">Powtórz hasło <br>
                                    <input type="password"
                                           name="repeatedPassword"
                                           class="form-control"
                                           placeholder="Powtórz nowe hasło"
                                           id="repeatedPassword">
                                </label>
                                <c:if test="${passwordMatch==false}">
                                    <p class="validation" style="font-size: 0.7rem">Hasła nie są identyczne</p>
                                </c:if>
                            </div>
                            <button type="submit" class="btn btn-primary">Zapisz</button>
                        </form>
                        <c:if test="${created==true}">
                            <p class="confirmation">Użytkownik utworzony pomyślnie.</p>
                        </c:if>
                    </div>
                </div>
            </div>
            <!-- End of Main Content -->
        </div>

        <!-- Footer -->
        <%@ include file="/app/footer.jsp" %>
        <!-- End of Footer -->

        <!-- End of Content -->
    </div>
    <!-- End of Content Wrapper -->
</div>
<!-- End of Page Wrapper -->

</body>
</html>