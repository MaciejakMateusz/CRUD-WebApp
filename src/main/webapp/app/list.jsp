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
                            <a href="${pageContext.request.contextPath}/app/add"
                               class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm" style="font-size: 1.1rem;"><i
                                    class="fas fa-download fa-sm text-white-50"></i> Dodaj użytkownika
                            </a>
                        </c:when>
                        <c:when test="${!sessionUser.isAdmin()}">
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
                        </c:when>
                    </c:choose>
                </div>
                <!-- /.container-fluid -->
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <c:choose>
                            <c:when test="${sessionUser.isAdmin()}">
                                <h6 class="m-0 font-weight-bold text-primary">Lista użytkowników</h6>
                            </c:when>
                            <c:when test="${!sessionUser.isAdmin()}">
                                <h6 class="m-0 font-weight-bold text-primary">Konto</h6>
                            </c:when>
                        </c:choose>
                    </div>
                    <div class="card-body">
                        <c:if test="${sessionUser.isAdmin()}">
                            <div>
                                <p>Wyszukaj użytkownika po:</p>
                                <form style="display: inline-block;" method="post" id="findId"
                                      action="${pageContext.request.contextPath}/app/list">
                                    <label>
                                        <input type="number"
                                               style="width: 80px;"
                                               min="1"
                                               name="findId"
                                               value="${findId}";
                                               placeholder="Id"
                                               form="findId">
                                    </label>
                                </form>
                                <p style="display: inline-block; white-space: pre;"> lub </p>
                                <form style="display: inline-block;" method="post" id="findEmail"
                                      action="${pageContext.request.contextPath}/app/list">
                                    <label>
                                        <input type="text"
                                               name="findEmail"
                                               value="${findEmail}"
                                               placeholder="Email"
                                               form="findEmail">
                                    </label>
                                    <c:if test="${wideResult==true}">
                                        <p style="color: red; display: inline-block;">Znaleziono więcej niż 50 wyników, spróbuj zawęzić wyszukiwanie.</p>
                                    </c:if>
                                </form>
                            </div>
                        </c:if>
                        <div class="table-responsive">
                            <div id="dataTable_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <c:if test="${userNotFound==true}">
                                            <p style="color: red">Nie znaleziono użytkownika.</p>
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
                                                        style="width: auto">Nazwa użytkownika
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
                                                <c:if test="${userFound==true}">
                                                    <tr role="row">
                                                        <td style="text-align: center">
                                                            <c:out value='${foundUser.id}'/></td>
                                                        <td><c:out value='${foundUser.userName}'/></td>
                                                        <td><c:out value='${foundUser.email}'/></td>
                                                        <td>
                                                            <form style="all: unset;" method="post"
                                                                  action="${pageContext.request.contextPath}/app/show">
                                                                <button
                                                                        type="submit"
                                                                        style="outline: none;"
                                                                        class="button-list"
                                                                        name="id"
                                                                        value="<c:out value='${foundUser.id}'/>">
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
                                                                        value="<c:out value='${foundUser.id}'/>">
                                                                    Edytuj
                                                                </button>
                                                            </form>
                                                            <c:if test="${!foundUser.isAdmin()}">
                                                                <form style="all: unset;"
                                                                      method="POST"
                                                                      action="${pageContext.request.contextPath}/app/delete">
                                                                    <button
                                                                            type="submit"
                                                                            style="outline: none; background: tomato;"
                                                                            class="button-list"
                                                                            name="id"
                                                                            value="<c:out value='${foundUser.id}'/>">
                                                                        Usuń
                                                                    </button>
                                                                </form>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:if>

                                                <c:if test="${usersFound==true}">
                                                    <c:forEach items="${foundUsers}" var="foundUser">
                                                        <tr role="row">
                                                            <td style="text-align: center">
                                                                <c:out value='${foundUser.id}'/></td>
                                                            <td><c:out value='${foundUser.userName}'/></td>
                                                            <td><c:out value='${foundUser.email}'/></td>
                                                            <td>
                                                                <form style="all: unset;" method="post"
                                                                      action="${pageContext.request.contextPath}/app/show">
                                                                    <button
                                                                            type="submit"
                                                                            style="outline: none;"
                                                                            class="button-list"
                                                                            name="id"
                                                                            value="<c:out value='${foundUser.id}'/>">
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
                                                                            value="<c:out value='${foundUser.id}'/>">
                                                                        Edytuj
                                                                    </button>
                                                                </form>
                                                                <c:if test="${!foundUser.isAdmin()}">
                                                                    <form style="all: unset;"
                                                                          method="POST"
                                                                          action="${pageContext.request.contextPath}/app/delete">
                                                                        <button
                                                                                type="submit"
                                                                                style="outline: none; background: tomato;"
                                                                                class="button-list"
                                                                                name="id"
                                                                                value="<c:out value='${foundUser.id}'/>">
                                                                            Usuń
                                                                        </button>
                                                                    </form>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:if>

                                                <c:if test="${isAdmin==true && userFound!=true && usersFound!=true}">
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
                                                                            value="<c:out value='${user.id}'/>">
                                                                        Edytuj
                                                                    </button>
                                                                </form>
                                                                <c:if test="${!user.isAdmin()}">
                                                                    <form style="all: unset;"
                                                                          method="POST"
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
                                                                  method="POST"
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
                                        <c:if test="${userNotFound==true || userFound==true || usersFound==true}">
                                            <div>
                                                <form style="all: unset;"
                                                      action="${pageContext.request.contextPath}/app/list">
                                                    <button
                                                            type="submit"
                                                            style="outline: none; font-size: 1.1rem"
                                                            class="button-list"
                                                            name="page"
                                                            value="${page}">
                                                        Powrót
                                                    </button>
                                                </form>
                                            </div>
                                        </c:if>
                                        <c:if test="${largeDB==null}">
                                            <c:forEach items="${pages}" var="page">
                                                <form style="all: unset;"
                                                      action="${pageContext.request.contextPath}/app/list">
                                                    <button
                                                            type="submit"
                                                            style="outline: none;"
                                                            class="button-list"
                                                            name="page"
                                                            value="${page}">
                                                            ${page}
                                                    </button>
                                                </form>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${largeDB==true && wideResult!=true && userNotFound!=true && usersFound!=true && userFound!=true}">
                                            <c:if test="${page==1}">
                                                <form method="POST" style="all: unset;"
                                                      action="${pageContext.request.contextPath}/app/list">
                                                    <button
                                                            type="submit"
                                                            style="outline: none; background: gray;"
                                                            class="button-list"
                                                            disabled>
                                                        <
                                                    </button>
                                                </form>
                                            </c:if>
                                        <c:if test="${page!=1}">
                                            <form method="POST" style="all: unset;"
                                                  action="${pageContext.request.contextPath}/app/list">
                                                <button
                                                        type="submit"
                                                        style="outline: none;"
                                                        class="button-list"
                                                        name="decrementPage"
                                                        value="${page}">
                                                    <
                                                </button>
                                            </form>
                                        </c:if>
                                            <c:forEach items="${fivePages}" var="page">
                                                <form style="all: unset;" action="${pageContext.request.contextPath}/app/list">

                                                <button
                                                        type="submit"
                                                        style="outline: none;"
                                                        class="button-list"
                                                        name="page"
                                                        value="${page}">
                                                        ${page}
                                                </button>
                                                </form>
                                            </c:forEach>
                                            <form style="all: unset;"
                                                  action="${pageContext.request.contextPath}/app/list">
                                                <button
                                                        type="button"
                                                        style="outline: none;"
                                                        class="button-list"
                                                disabled>
                                                    ...
                                                </button>
                                            </form>
                                            <form style="all: unset;"
                                                  action="${pageContext.request.contextPath}/app/list">
                                                <button
                                                        type="submit"
                                                        style="outline: none;"
                                                        class="button-list"
                                                        name="page"
                                                        value="${numberOfPages}">
                                                        ${numberOfPages}
                                                </button>
                                            </form>
                                            <c:if test="${page!=numberOfPages}">
                                                <form method="POST" style="all: unset;"
                                                      action="${pageContext.request.contextPath}/app/list">
                                                    <button
                                                            type="submit"
                                                            style="outline: none;"
                                                            class="button-list"
                                                            name="incrementPage"
                                                            value="${page}">
                                                        >
                                                    </button>
                                                </form>
                                            </c:if>
                                            <c:if test="${page==numberOfPages}">
                                                <form method="POST" style="all: unset;"
                                                      action="${pageContext.request.contextPath}/app/list">
                                                    <button
                                                            type="submit"
                                                            style="outline: none; background: gray;"
                                                            class="button-list"
                                                            disabled>
                                                        >
                                                    </button>
                                                </form>
                                            </c:if>
                                            <p style="display:inline-block; white-space: pre;">  Strona  </p>
                                            <form style="display: inline-block;">
                                                <label>
                                                    <input
                                                            type="number"
                                                            style="width: 4rem;"
                                                            name="page"
                                                            min="1"
                                                            max="${numberOfPages}"
                                                            value="${page}">
                                                </label>
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
        <%@ include file="/app/footer.jsp" %>
        <!-- End of Footer -->

        <!-- End of Content -->
    </div>
    <!-- End of Content Wrapper -->
</div>
<!-- End of Page Wrapper -->

</body>

</html>