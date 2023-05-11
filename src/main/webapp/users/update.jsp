<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="pl">

<!-- Header -->
<%@ include file="/users/header.jsp" %>
<!-- End of Header -->

<body id="page-top">
<div id="wrapper">
    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center"
           href="${pageContext.request.contextPath}/user/list">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-laugh-wink"></i>
            </div>
            <div class="sidebar-brand-text mx-3">SB Admin <sup>2</sup></div>
        </a>
        <!-- Divider -->
        <!-- Nav Item - Dashboard -->
        <li class="nav-item active">
            <hr class="sidebar-divider my-0">
            <a class="nav-link" href="${pageContext.request.contextPath}/user/list">
                <i class="fas fa-fw fa-tachometer-alt"></i>
                <span>Dashboard</span>
            </a>
            <hr class="sidebar-divider">
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
                    <h1 class="h3 mb-0 text-gray-800">UsersCRUD</h1>
                    <a href="${pageContext.request.contextPath}/user/list"
                       class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
                            class="fas fa-download fa-sm text-white-50"></i> Lista użytkowników
                    </a>
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
                                    <input type="text" name="name"
                                           value="${name}" class="form-control" id="name">
                                </label>
                                <c:if test="${validUserName==false}">
                                    <p class="validation" style="font-size: 0.7rem">Nazwa użytkownika musi składać się z
                                        minimum 5 znaków</p>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label for="email">Email <br>
                                    <input type="text" name="email"
                                           value="${email}" class="form-control" id="email">
                                </label>
                                <c:if test="${validEmail==false}">
                                    <p class="validation" style="font-size: 0.7rem">Niepoprawny email</p>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label for="password">Hasło <br>
                                    <input type="password" name="password" class="form-control"
                                           placeholder="Nowe hasło" value="${password}" id="password">
                                </label>
                                <c:if test="${validPassword==false}">
                                    <p class="validation" style="font-size: 0.7rem">Hasło musi posiadać: <br> - od 5 do
                                        60 znaków<br> - minimum 1 małą literę<br> - minimum 1 dużą literę<br> - minimum
                                        jeden znak specjalny</p>
                                </c:if>
                            </div>
                            <button type="submit" class="btn btn-primary" name="id" value="${id}">Edytuj</button>
                        </form>
                        <c:if test="${updated==true}">
                            <p class="confirmation">Dane zaktualizowane pomyślnie.</p>
                        </c:if>
                    </div>
                </div>
            </div>
            <!-- End of Main Content -->
        </div>

        <!-- Footer -->
        <%@ include file="/users/footer.jsp" %>
        <!-- End of Footer -->

        <!-- End of Content -->
    </div>
    <!-- End of Content Wrapper -->
</div>
<!-- End of Page Wrapper -->

</body>
</html>