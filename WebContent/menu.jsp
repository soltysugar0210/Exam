<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.UserBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>メニュー</title>
</head>
<body>
    <%
        UserBean user = (UserBean)session.getAttribute("loginUser");
        System.out.println("menu.jsp: loginUser = " + (user != null ? user.getName() : "null") + ", Session ID = " + session.getId());
        if (user != null) {
    %>
        <h1>ようこそ、<%= user.getName() %> さん！</h1>
        <p>ID: <%= user.getId() %></p>
    <%
        } else {
            System.out.println("menu.jsp: loginUser is null, displaying message");
    %>
        <p>ログインしてください。</p>
    <%
        }
    %>
    <a href="<%= request.getContextPath() %>/student_add.jsp">学生情報へ</a>
    <a href="<%= request.getContextPath() %>/student_add.html">科目情報へ</a>
    <a href="<%= request.getContextPath() %>/teacher_add.jsp">教員情報へ</a>
</body>
</html>