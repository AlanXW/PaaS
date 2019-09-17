<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta charset="UTF-8">
<title>Sheffield CloudBase</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="css/style.css"/>

</head>

<body>
<!-- top -->
<div id="xt-top">
    <div class="xt-logo"><img src="images/logo.png" /></div>
    <div class="xt-geren">
        <div class="xt-exit"><span class="xt-span"><c:choose><c:when test="${empty sessionScope.user}">Please login before using any apps.</c:when><c:otherwise>Welcome,</c:otherwise></c:choose><span class="xt-colour">${sessionScope.user.username}</span></span><a href="user?method=logout" class="exit">exit</a></div>
    </div>
</div>

<div class="xt-center" style="min-height: 800px;">
    <div class="xt-table">
        <table cellpadding="0" cellspacing="0" border="0" bgcolor="#dcdcdc" width="100%">
            <tr>
                <th>Order</th>
                <th>User</th>
                <th>Application</th>
                <th>Transaction</th>
                <th>Date</th>
            </tr>
            <!-- display the list -->
            <% int i = 0;%>
            <c:forEach items="${list}" var="transaction">
                <%
                    i++;
                    if(i%2!=0) out.write("<tr>");
                    else out.write("<tr class=\"xt-bg\">");
                %>
                <%out.write("<td>"+i+"</td>");%>
                <td>${transaction.user}</td>
                <td>${transaction.app}</td>
                <td>${transaction.user} use ${transaction.app}, 5 peanuts for deduction.</td>
                <td>${transaction.date}</td>
                </tr>
            </c:forEach>

        </table>
    </div>
</div>
  <footer>
    <div class="xt-footer">© 2019 Team 10, University of Sheffield</div>
  </footer>

</body>

</html>