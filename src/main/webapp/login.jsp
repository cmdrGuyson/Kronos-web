<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="icon" href="images/timetable.png"/>
    <%@ include file="util/head_imports.jsp" %>
    <link rel="stylesheet" type="text/css" href="css/index.css"/>
    <title>Kronos</title>

</head>
<body class="body-login">
<div class="container" style="padding-left: 300px; padding-right: 300px;">
    <div class="card align-self-center login-card" style="margin-top: 70px; width: 80%;">
        <div class="c   ard-body" style="padding: 50px;">
            <div style="text-align: center;">
                <img
                        src="./images/timetable.png"
                        class="card-img-top"
                        alt=""
                        style="width: 50%;"
                />
                <h1 class="display-4 login-heading">
                    Kronos
                </h1>
            </div>
            <form class="form-signin" method="POST">
                <br/>
                <div class="text-center">
                    <label for="inputUsername" class="sr-only">Username</label>
                    <input
                            type="text"
                            id="inputUsername"
                            class="form-control round-border big-padding"
                            name="username"
                            placeholder="Username"
                            maxlength="50"
                            required
                            autofocus
                    />
                    <br/>
                    <label for="inputPassword" class="sr-only">Password</label>
                    <input
                            type="password"
                            maxlength="255"
                            id="inputPassword"
                            class="form-control round-border big-padding"
                            name="password"
                            placeholder="Password"
                            required
                    />
                    <br/>

                    <c:set var="fail" value='<%= request.getParameter("errors")%>'/>
                    <c:if test="${fail == true}">
                        <p class="errors">Incorrect Username or Password</p>
                        <br/>
                    </c:if>

                    <button class="btn btn-md btn-primary btn-primary-login btns-block round-border" type="submit">
                        Login
                    </button>
                    <p class="mt-5 mb-3 text-muted remember">
                        Don't remember password?
                    </p>
                    <a href="#" style="color: #32bea6;">Please contact Administrator!</a>
                </div>
            </form>

        </div>
    </div>
</div>

<%@ include file="util/script_imports.jsp" %>
</body>
</html>