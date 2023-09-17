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
                    <a class="nav-link" href="${pageContext.request.contextPath}/app/list">
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
                <button id="sidebarToggleTop"
                        class="btn btn-link d-md-none rounded-circle mr-3">
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
                        <button type="submit"
                                style="outline: none; font-size: 1.1rem;"
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
                        <c:choose>
                            <c:when test="${sessionUser.isAdmin()}">
                                <h6 class="m-0 font-weight-bold text-primary">Potwierdź usunięcie użytkownika</h6>
                            </c:when>
                            <c:when test="${!sessionUser.isAdmin()}">
                                <h6 class="m-0 font-weight-bold text-primary">Potwierdź usunięcie swojego konta</h6>
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
                                            <c:if test="${deleted==true}">
                                                <p style="color: green">Użytkownik został usunięty.</p>
                                            </c:if>
                                            <c:if test="${deleted==false}">
                                            <tbody>
                                            <tr>
                                                <td><strong>Id</strong></td>
                                                <td><c:out value='${user.id}'/></td>
                                            </tr>
                                            <tr>
                                                <td><strong>Nazwa użytkownika</strong></td>
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
                                        </table>
                                        <form method="post">
                                            <c:if test="${sessionUser.isAdmin()}">
                                                <p style="color: tomato;">Czy potwierdzasz usunięcie tego
                                                    użytkownika?<br></p>
                                            </c:if>
                                            <c:if test="${!sessionUser.isAdmin()}">
                                                <p style="color: tomato;">Czy na pewno chcesz usunąć swoje konto? <br>
                                                    Ta akcja jest nieodwracalna, nastąpi wylogowanie.</p>
                                            </c:if>
                                            <button type="submit"
                                                    style="outline: none; font-size: 1.1rem;"
                                                    class="button-list"
                                                    name="isConfirmed"
                                                    value="false">
                                                Nie
                                            </button>
                                            <button type="submit"
                                                    style="outline: none; font-size: 1.1rem; background: tomato; border-color: tomato;"
                                                    class="button-list"
                                                    name="isConfirmed"
                                                    value="true">
                                                Tak
                                            </button>
                                        </form>
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
        <c:if test="${deleted==false}">
            <%@ include file="/app/footer.jsp" %>
        </c:if>
        <!-- End of Footer -->

        <!-- End of Content -->
    </div>
    <!-- End of Content Wrapper -->
</div>
<!-- End of Page Wrapper -->

</body>

</html>