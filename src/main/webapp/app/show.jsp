<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <c:choose>
                        <c:when test="${sessionUser.isAdmin()}">
                            <form style="all: unset;"
                                  method="POST"
                                  action="${pageContext.request.contextPath}/app/edit">
                                <button
                                        type="submit"
                                        style="outline: none; font-size: 1.1rem;"
                                        class="button-list"
                                        name="id"
                                        value="<c:out value='${user.id}'/>">
                                    <i class="fas fa-download fa-sm text-white-50"></i>
                                    <c:choose>
                                        <c:when test="${user.isAdmin()}">
                                            Edytuj administratora
                                        </c:when>
                                        <c:when test="${!user.isAdmin()}">
                                            Edytuj użytkownika
                                        </c:when>
                                    </c:choose>
                                </button>
                            </form>
                        </c:when>
                        <c:when test="${!sessionUser.isAdmin()}">
                            <div>
                                <form style="all: unset;"
                                      method="POST"
                                      action="${pageContext.request.contextPath}/app/edit">
                                    <button
                                            type="submit"
                                            style="outline: none; font-size: 1.1rem;"
                                            class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"
                                            name="id"
                                            value="<c:out value='${sessionUser.id}'/>">
                                        <i class="fas fa-download fa-sm text-white-50"></i>
                                        Edytuj konto
                                    </button>
                                </form>
                                <form style="all: unset;"
                                      method="POST"
                                      action="${pageContext.request.contextPath}/app/delete">
                                    <button
                                            type="submit"
                                            style="outline: none; font-size: 1.1rem; background: tomato; border-color: tomato;"
                                            class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"
                                            name="id"
                                            value="<c:out value='${sessionUser.id}'/>">
                                        <i class="fas fa-download fa-sm text-white-50"></i>
                                        Usuń konto
                                    </button>
                                </form>
                            </div>
                        </c:when>
                    </c:choose>
                </div>
                <!-- /.container-fluid -->
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <c:choose>
                            <c:when test="${sessionUser.isAdmin() && !user.isAdmin()}">
                                <h6 class="m-0 font-weight-bold text-primary">Szczegóły użytkownika</h6>
                            </c:when>
                            <c:when test="${sessionUser.isAdmin() && user.isAdmin()}">
                                <h6 class="m-0 font-weight-bold text-primary">Szczegóły administratora</h6>
                            </c:when>
                            <c:when test="${!sessionUser.isAdmin()}">
                                <h6 class="m-0 font-weight-bold text-primary">Szczegóły konta</h6>
                            </c:when>
                        </c:choose>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <div id="dataTable_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <table class="table table-bordered dataTable" id="dataTable" width="100%"
                                               cellspacing="0" role="grid" aria-describedby="dataTable_info"
                                               style="width: 100%; border-collapse: collapse; border-left: none;">
                                            <c:if test="${userNotFound==true}">
                                                <p style="color: red">Nie znaleziono użytkownika.</p>
                                            </c:if>
                                            <c:if test="${userNotFound!=true && sessionUser.isAdmin()}">
                                                <tbody>
                                                <tr>
                                                    <td><strong>Id</strong></td>
                                                    <td><c:out value='${user.id}'/></td>
                                                </tr>
                                                <tr>
                                                    <c:choose>
                                                        <c:when test="${user.isAdmin()}">
                                                            <td><strong>Nazwa administratora</strong></td>
                                                        </c:when>
                                                        <c:when test="${!user.isAdmin()}">
                                                            <td><strong>Nazwa użytkownika</strong></td>
                                                        </c:when>
                                                    </c:choose>
                                                    <td><c:out value='${user.userName}'/></td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Email</strong></td>
                                                    <td><c:out value='${user.email}'/></td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Data utworzenia</strong></td>
                                                    <td><c:out value='${user.creationDate}'/></td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Ostatnio edytowano</strong></td>
                                                    <td><c:out value='${user.lastEdited}'/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                </tbody>
                                            </c:if>
                                            <c:if test="${userNotFound==true}">
                                                <p style="color: red">Nie znaleziono użytkownika.</p>
                                            </c:if>
                                            <c:if test="${userNotFound!=true && !sessionUser.isAdmin()}">
                                                <tbody>
                                                <tr>
                                                    <td><strong>Id</strong></td>
                                                    <td><c:out value='${sessionUser.id}'/></td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Nazwa użytkownika</strong></td>
                                                    <td><c:out value='${sessionUser.userName}'/></td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Email</strong></td>
                                                    <td><c:out value='${sessionUser.email}'/></td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Data utworzenia</strong></td>
                                                    <td><c:out value='${sessionUser.creationDate}'/></td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Ostatnio edytowano</strong></td>
                                                    <td><c:out value='${sessionUser.lastEdited}'/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                </tbody>
                                            </c:if>
                                        </table>
                                        <c:if test="${sessionUser.isAdmin()}">
                                            <button
                                                    type="submit"
                                                    style="outline: none; font-size: 1.1rem;"
                                                    class="button-list"
                                                    id="backButton">
                                                Powrót
                                            </button>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
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

<script src="${pageContext.request.contextPath}/theme/js/eventListeners.js"></script>
</body>

</html>