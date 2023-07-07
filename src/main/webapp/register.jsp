<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
                                <c:if test="${created==null}">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">Rejestracja nowego użytkownika</h1>
                                </div>
                                    <form class="user" action="${pageContext.request.contextPath}/register" method="POST">
                                        <div class="form-group">
                                            <input type="text"
                                                   class="form-control form-control-user"
                                                   placeholder="Wpisz nazwę użytkownika..."
                                                   name="name"
                                                   value="${userName}">
                                        </div>
                                        <c:if test="${validUserName==false}">
                                            <p class="validation" style="font-size: 0.7rem">Nazwa użytkownika musi składać się z
                                                minimum 5 znaków</p>
                                        </c:if>
                                        <c:if test="${usernameExists==true}">
                                            <p class="validation" style="font-size: 0.7rem">Nazwa użytkownika jest zajęta</p>
                                        </c:if>
                                        <div class="form-group">
                                            <input type="email"
                                                   class="form-control form-control-user"
                                                   aria-describedby="emailHelp"
                                                   placeholder="Wpisz email..."
                                                   name="email"
                                                   value="${email}">
                                        </div>
                                        <c:if test="${validEmail==false}">
                                            <p class="validation" style="font-size: 0.7rem">Niepoprawny email</p>
                                        </c:if>
                                        <c:if test="${emailExists==true}">
                                            <p class="validation" style="font-size: 0.7rem">Użytkownik z podanym adresem email już istnieje.<br>
                                            Masz już konto? <a href="${pageContext.request.contextPath}/login">Zaloguj się.</a></p>
                                        </c:if>
                                        <div class="form-group">
                                            <input type="password"
                                                   class="form-control form-control-user"
                                                   placeholder="Wpisz hasło..."
                                                   name="password"
                                                   value="${password}">
                                        </div>
                                        <c:if test="${validPassword==false}">
                                            <p class="validation" style="font-size: 0.7rem">Hasło musi posiadać: <br> - od 5 do
                                                20 znaków<br> - minimum 1 małą literę<br> - minimum 1 dużą literę<br> - minimum
                                                jeden znak specjalny</p>
                                        </c:if>
                                        <div class="form-group">
                                            <input type="password"
                                                   class="form-control form-control-user"
                                                   placeholder="Powtórz hasło..."
                                                   name="repeatedPassword">
                                        </div>
                                        <c:if test="${passwordMatch==false}">
                                            <p class="validation" style="font-size: 0.7rem">Hasła nie są identyczne</p>
                                        </c:if>
                                        <button
                                                type="submit"
                                                class="btn btn-primary btn-user btn-block"
                                                style="font-size: 1.1rem">
                                            Zarejestruj się!
                                        </button>
                                    </form>
                                    <hr>
                                    <div class="text-center">
                                        <a class="small" href="${pageContext.request.contextPath}/login">Masz już konto? Zaloguj się!</a>
                                    </div>
                                </c:if>
                                <c:if test="${created==true}">
                                    <div class="text-center">
                                        <p style="color: green">Rejestracja przebiegła pomyślnie.</p>
                                        <hr>
                                        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Zaloguj się!</a>
                                    </div>
                                </c:if>
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