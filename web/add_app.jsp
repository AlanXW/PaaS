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

<div class="xt-center">
    <div class="xt-container">
      <p style="font-size: 28px; text-align: center">Upload Your App</p>
      <p style="margin-top: 30px; margin-bottom: 15px">The platform requires Independent Software Vendors (ISVs) to upload an icon, a compressed war package for project files and an SQL file. App must contain filters to prevent apps from being accessed directly through links (without login). In addition, the app must implement the API provided by the platform. The API will send a Username by Cookie"username", and the filter needs to decide whether to intercept based on the Username. At the same time, the Username can be used as the unique identity of the app store.</p>
        <p style="margin-bottom: 15px">The user name sent is encrypted. The encryption algorithm is in the jar package developed by our team. You can download <input type="button" value="cloudx.jar" onclick="window.location.href='download'" style="background: #21b6b4; color: #fff; width: 70px;padding: 6px 10px; border: none; border-radius: 8px; font-size: 12px; margin: 0"/> here. There is one method of decryption and encryption for the conversion of plaintext to cryptographic text, and another method of verifying user names to ensure that the username received is real and not from a request tampered.</p>
      <p style="margin-bottom: 30px">Hint: Database connection username and password in your app must be "root" and "123456".</p>
      <form method="post" action="application?method=addApplication" enctype="multipart/form-data">
          <span>App Icon:</span>
          <input type="file" class="int-text" name="icon"/>
          <span>App .war File:</span>
          <input type="file" class="int-text" name="war"/>
          <span>App SQL File:</span>
          <input type="file" class="int-text" name="sql"/>
          <span>App Description:</span>
          <textarea class="int-text" style="width: 700px; height: 300px; margin-left: 25%; margin-top:5px; font-size: 16px; color: #666; font-family: 'Microsoft YaHei', Helvetica, Arial, sans-serif" name="description"></textarea>
          <input type="submit" value="SUBMIT" style="background: #ffb849; color: #fff; width: 28%; padding: 6px 20px; border: none; border-radius: 8px; font-size: 14px; margin-left: 36%; margin-top: 30px;">
      </form>
    </div>
</div>
  <footer>
    <div class="xt-footer">© 2019 Team 10, University of Sheffield</div>
  </footer>

</body>

</html>
