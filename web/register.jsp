<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8" />
    <title>Sheffield CloudBase</title>
    <link rel="stylesheet" href="css/login.css" />
</head>

<body>
<!-- Login -->
<div class="top"><img src="images/shef1.png" /></div>
<div class="content">
    <div class="login">
        <form action="user" accept-charset="utf-8" id="user" method="post">
            <input type="hidden" name="method" value="register">
            <div class="title">Sheffield CloudBase</div>
            <div class="line">
                <img class="smallImg" src="images/icon1.png" />
                <input placeholder="Please input username" type="text" id="u2" name="username"/>
            </div>
            <div class="line">
                <img class="smallImg" src="images/icon2.png" />
                <input placeholder="Please input password" type="password" id="p1" name="password"/>
            </div>
            <div class="line">
                <img class="smallImg" src="images/icon2.png" />
                <input placeholder="Confirm password" type="password" id="p2" name="password2"/>
            </div>
            <input type="submit" value="Sign up" class="logBut">
        </form>

    </div>
</div>
</body>

</html>