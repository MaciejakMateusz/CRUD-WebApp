<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pl">

<!-- Header -->
<%@ include file="header.jsp" %>
<!-- End of Header -->

<body class="bg-gradient-primary">

<div class="container">

  <!-- Outer Row -->
  <div class="row justify-content-center">

    <div class="col-xl-10 col-lg-12 col-md-9">

      <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-2">
          <!-- Nested Row within Card Body -->
          <div class="row">
            <div class="col-lg-12">
              <div class="p-5">
                <div class="text-center">
                  <h1 class="h4 text-gray-900 mb-4">Witaj, zaloguj się!</h1>
                </div>
                <form class="user" action="${pageContext.request.contextPath}/login" method="POST">
                  <div class="form-group">
                    <input type="email" class="form-control form-control-user" placeholder="Wpisz email..." name="email" value="${email}">
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-user" placeholder="Podaj hasło..." name ="password" value="${password}">
                  </div>
                  <div class="form-group">
                    <div class="custom-control custom-checkbox small">
                      <input type="checkbox" class="custom-control-input" id="customCheck" name="rememberMe" checked>
                      <label class="custom-control-label" for="customCheck">Zapamiętaj mnie!</label>
                    </div>
                  </div>
                  <button type="submit" class="btn btn-primary btn-user btn-block" style="font-size: 1.1rem;">Zaloguj się</button>
                </form>
                <c:if test="${invalidInput==true}">
                  <p class="validation" style="font-size: 0.7rem">Niepoprawny email lub hasło, spróbuj ponownie</p>
                </c:if>
                <c:if test="${emailNotFound==true}">
                  <p class="validation" style="font-size: 0.7rem">Konto powiązane z <c:out value="${email}"/> nie istnieje.</p>
                </c:if>
                <hr>
                <div class="text-center">
                  <a class="small" href="${pageContext.request.contextPath}/register">Stwórz nowe konto</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>

  </div>

</div>

</body>

</html>