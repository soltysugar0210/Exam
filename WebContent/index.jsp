<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
</head>
<body>
    <h1>ログイン</h1>
    <form action="Login.action" method="post">
        <label>ID: <input type="text" name="id"></label><br>
        <label>パスワード: <input type="password" name="password"></label><br>
        <input type="submit" value="ログイン">
        <%
            String error = (String)request.getAttribute("error");
            if (error != null) {
        %>
            <p style="color:red"><%= error %></p>
        <%
            }
        %>
    </form>
</body>
</html>