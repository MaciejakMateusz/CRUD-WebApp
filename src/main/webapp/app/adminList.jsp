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
                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                    <i class="fa fa-bars"></i>
                </button>
            </nav>
            <!-- End of Topbar -->
            <!-- Begin Page Content -->
            <div class="container-fluid">
                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <c:if test="${!sessionUser.isAdmin()}">
                        <form style="all: unset;"
                              method="POST"
                              action="${pageContext.request.contextPath}/app/edit">
                            <button
                                    type="submit"
                                    style="outline: none; font-size: 1.1rem;"
                                    class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm "
                                    name="id"
                                    value="<c:out value='${sessionUser.id}'/>">
                                <i class="fas fa-download fa-sm text-white-50"></i>
                                Edytuj konto
                            </button>
                        </form>
                    </c:if>
                </div>
                <!-- /.container-fluid -->
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <c:if test="${sessionUser.isAdmin()}">
                            <h6 class="m-0 font-weight-bold text-primary">Lista administratorów</h6>
                        </c:if>
                        <c:if test="${!sessionUser.isAdmin()}">
                            <h6 class="m-0 font-weight-bold text-primary">Konto</h6>
                        </c:if>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <div id="dataTable_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <c:if test="${userNotFound==true}">
                                            <p style="color: red">Nie znaleziono użytkownika.</p>
                                            <form style="all: unset;"
                                                  action="${pageContext.request.contextPath}/app/list">
                                                <button
                                                        type="submit"
                                                        style="outline: none; font-size: 1.1rem;"
                                                        class="button-list">
                                                    Powrót
                                                </button>
                                            </form>
                                        </c:if>
                                        <c:if test="${userNotFound!=true}">

                                            <table class="table table-bordered dataTable" id="dataTable" width="100%"
                                                   cellspacing="0" role="grid" aria-describedby="dataTable_info"
                                                   style="width: 100%; border-collapse: collapse; border-left: none;">
                                                <thead>
                                                <tr role="row">
                                                    <th tabindex="0" aria-controls="dataTable"
                                                        rowspan="1" colspan="1"
                                                        aria-label="Name: activate to sort column descending"
                                                        style="width: auto; text-align: center" aria-sort="ascending">Id
                                                    </th>
                                                    <th tabindex="0" aria-controls="dataTable" rowspan="1"
                                                        colspan="1" aria-label="Position: activate to sort column ascending"
                                                        style="width: auto">Nazwa administratora
                                                    </th>
                                                    <th tabindex="0" aria-controls="dataTable" rowspan="1"
                                                        colspan="1" aria-label="Office: activate to sort column ascending"
                                                        style="width: auto">Email
                                                    </th>
                                                    <th tabindex="0" aria-controls="dataTable" rowspan="1"
                                                        colspan="1" aria-label="Age: activate to sort column ascending"
                                                        style="width: auto;">Akcja
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:if test="${isAdmin==true && userFound!=true}">
                                                    <c:forEach items="${users}" var="user">
                                                        <tr role="row">
                                                            <td style="text-align: center"><c:out value='${user.id}'/></td>
                                                            <td><c:out value='${user.userName}'/></td>
                                                            <td><c:out value='${user.email}'/></td>
                                                            <td>
                                                                <form style="all: unset;" method="post"
                                                                      action="${pageContext.request.contextPath}/app/show">
                                                                    <button
                                                                            type="submit"
                                                                            style="outline: none;"
                                                                            class="button-list"
                                                                            name="id"
                                                                            value="<c:out value='${user.id}'/>">
                                                                        Pokaż
                                                                    </button>
                                                                </form>
                                                                <form style="all: unset;"
                                                                      method="POST"
                                                                      action="${pageContext.request.contextPath}/app/edit">
                                                                    <button
                                                                            type="submit"
                                                                            style="outline: none;"
                                                                            class="button-list"
                                                                            name="id"
                                                                            value="${user.id}">
                                                                        Edytuj
                                                                    </button>
                                                                </form>
                                                                <c:if test="${!user.isAdmin()}">
                                                                    <form style="all: unset;"
                                                                          action="${pageContext.request.contextPath}/app/delete">
                                                                        <button
                                                                                type="submit"
                                                                                style="outline: none; background: tomato;"
                                                                                class="button-list"
                                                                                name="id"
                                                                                value="<c:out value='${user.id}'/>">
                                                                            Usuń
                                                                        </button>
                                                                    </form>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:if>
                                                <c:if test="${isAdmin==false}">
                                                    <tr role="row">
                                                        <td style="text-align: center"><c:out
                                                                value='${sessionUser.id}'/></td>
                                                        <td><c:out value='${sessionUser.userName}'/></td>
                                                        <td><c:out value='${sessionUser.email}'/></td>
                                                        <td>
                                                            <form style="all: unset;" method="post"
                                                                  action="${pageContext.request.contextPath}/app/show">
                                                                <button
                                                                        type="submit"
                                                                        style="outline: none;"
                                                                        class="button-list"
                                                                        name="id"
                                                                        value="<c:out value='${sessionUser.id}'/>">
                                                                    Pokaż
                                                                </button>
                                                            </form>
                                                            <form style="all: unset;"
                                                                  method="POST"
                                                                  action="${pageContext.request.contextPath}/app/edit">
                                                                <button
                                                                        type="submit"
                                                                        style="outline: none;"
                                                                        class="button-list"
                                                                        name="id"
                                                                        value="<c:out value='${sessionUser.id}'/>">
                                                                    Edytuj
                                                                </button>
                                                            </form>
                                                            <form style="all: unset;"
                                                                  action="${pageContext.request.contextPath}/app/delete">
                                                                <button
                                                                        type="submit"
                                                                        style="outline: none; background: tomato;"
                                                                        class="button-list"
                                                                        name="id"
                                                                        value="<c:out value='${sessionUser.id}'/>">
                                                                    Usuń konto
                                                                </button>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                </tbody>
                                            </table>
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

</body>

</html>