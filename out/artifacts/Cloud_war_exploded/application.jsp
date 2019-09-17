<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Sheffield CloudBase</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- top -->
<div id="xt-top">
    <div class="xt-logo"><img src="images/logo.png" /></div>
    <div class="xt-geren">
        <div class="xt-exit"><span class="xt-span"><c:choose><c:when test="${empty sessionScope.user}">Please login before using any apps.</c:when><c:otherwise>Welcome,</c:otherwise></c:choose><span class="xt-colour">${sessionScope.user.username}</span></span><a href="user?method=logout" class="exit">exit</a></div>
    </div>
</div>

<div class="xt-center">
<div class="xt-bt">
    <input type="button" value="Login" onclick="window.location.href='login.jsp'" class="yellow-int" />
    <input type="button" value="Register" onclick="window.location.href='register.jsp'" class="yellow-int" />
    <input type="button" value="Balance" onclick="window.location.href='application?method=getBalance'" class="green-int" />
    <input type="button" value="Upload" onclick="window.location.href='add_app.jsp'" class="blue-int" />
    <input type="button" value="Transaction" onclick="window.location.href='transaction'" class="red-int" />
</div>
    <div class="xt-container">
        <p style="font-size: 28px; text-align: center;">Sheffield CloudBase</p>
        <p style="font-size: 16px; text-align: center;">Platform-as-a-Service</p>
        <p style="font-size: 12px; text-align: center; margin-bottom: 50px;">Hint: The price of every app is 5 peanuts.</p>
        <!-- display application list -->
        <% int i = 1;%>
        <c:forEach items="${list}" var="application">
        <div class="xt-card">
          <%
              if(i==1||i==5) out.write("<header class=\"yellow-header\">");
              else if (i==2||i==6) out.write("<header class=\"green-header\">");
              else if (i==3||i==7) out.write("<header class=\"blue-header\">");
              else if (i==4||i==8) out.write("<header class=\"red-header\">");
              i++;
          %>
            <h2 style="font-style: Arial; padding-top: 18px">${application.name}</h2>
          </header>
          <div>
            <a href="application?method=useApplication&id=${application.owner}&name=${application.name}">
            <img src="${application.icon}" style="height:100px; width: 100px; padding: 20px 100px 30px  100px">
            </a>
          </div>

          <div>
            <p style="padding: 0px 10px 0px 10px">${application.description}</p>
          </div>
        </div>
        </c:forEach>
        <%
            for(;i<=8;i++){
                out.write("<div class=\"xt-card\">");
                if(i==1||i==5) out.write("<header class=\"yellow-header\">");
                else if (i==2||i==6) out.write("<header class=\"green-header\">");
                else if (i==3||i==7) out.write("<header class=\"blue-header\">");
                else if (i==4||i==8) out.write("<header class=\"red-header\">");
        %>
            <h2 style="font-style: Arial; padding-top: 18px">App</h2>
            </header>
            <div>
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <a href="login.jsp">
                    </c:when>
                    <c:otherwise>
                        <a href="add_app.jsp">
                    </c:otherwise>
                </c:choose>
                    <img src="images/plus.png" style="height:100px; width: 100px; padding: 20px 100px 30px  100px">
                </a>
            </div>

            <div>
                <p style="padding: 0px 10px 0px 10px">Description of App.</p>
            </div>
            </div>
        <%
            }
        %>
    </div>
</div>
  <footer>
    <div class="xt-footer">© 2019 Team 10, University of Sheffield</div>
  </footer>
</body>
</html>