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
                        <h6 class="m-0 font-weight-bold text-primary">Potwierdź usunięcie
                            użytkownika</h6>
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
                                                <td>${user.id}</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Nazwa użytkownika</strong></td>
                                                <td>${user.userName}</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Email</strong></td>
                                                <td>${user.email}</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Data utworzenia</strong></td>
                                                <td>${user.creationDate}</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Ostatnio edytowano</strong></td>
                                                <td>${user.lastEdited}</td>
                                            </tr>
                                            </tbody>
                                            </c:if>
                                        </table>
                                        <c:if test="${deleted==false}">
                                            <form method="post">
                                                <p>Czy potwierdzasz usunięcie tego użytkownika?<br></p>
                                                <button type="submit" style="outline: none;" class="button-list"
                                                        name="isConfirmed" value="false">Nie
                                                </button>
                                                <button type="submit" style="outline: none;" class="button-list"
                                                        name="isConfirmed" value="true">Tak
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
        <%@ include file="/users/footer.jsp" %>
        <!-- End of Footer -->

        <!-- End of Content -->
    </div>
    <!-- End of Content Wrapper -->
</div>
<!-- End of Page Wrapper -->

</body>

</html>