<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="pl">

<!-- Header -->
<%@ include file="/users/header.jsp" %>
<!-- End of Header -->

<body class="bg-gradient-primary">

<div class="container">

  <!-- Outer Row -->
  <div class="row justify-content-center">

    <div class="col-xl-10 col-lg-12 col-md-9">

      <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
          <!-- Nested Row within Card Body -->
          <div class="row">
            <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
            <div class="col-lg-6">
              <div class="p-5">
                <div class="text-center">
                  <h1 class="h4 text-gray-900 mb-4">Witaj, zaloguj się!</h1>
                </div>
                <form class="user" action="${pageContext.request.contextPath}/user/login" method="POST">
                  <div class="form-group">
                    <input type="email" class="form-control form-control-user" placeholder="Wpisz email..." name="email" value="${email}">
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-user" placeholder="Podaj hasło..." name ="password" value="${password}">
                  </div>
                  <div class="form-group">
                    <div class="custom-control custom-checkbox small">
                      <input type="checkbox" class="custom-control-input" id="customCheck" name="rememberMe">
                      <label class="custom-control-label" for="customCheck">Zapamiętaj mnie!</label>
                    </div>
                  </div>
                  <button type="submit" class="btn btn-primary btn-user btn-block">Zaloguj się</button>
                </form>
                <c:if test="${invalidInput==true}">
                  <p class="validation" style="font-size: 0.7rem">Niepoprawny email lub hasło, spróbuj ponownie</p>
                </c:if>
                <hr>
                <div class="text-center">
                  <a class="small" href="${pageContext.request.contextPath}/user/register">Stwórz nowe konto</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>

  </div>

</div>

<!-- Bootstrap core JavaScript-->
<script src="<c:url value="/theme/vendor/jquery/jquery.min.js"/>"></script>
<script src="<c:url value="/theme/vendor/bootstrap/js/bootstrap.bundle.min.js"/>"></script>

<!-- Core plugin JavaScript-->
<script src="<c:url value="/theme/vendor/jquery-easing/jquery.easing.min.js"/>"></script>

<!-- Custom scripts for all pages-->
<script src="<c:url value="/theme/js/sb-admin-2.min.js"/>"></script>

</body>

</html>