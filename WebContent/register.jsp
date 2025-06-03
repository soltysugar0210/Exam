<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.UserBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>生徒登録結果</title>
</head>
<body>
    <h2>生徒登録結果</h2>
    <%
        UserBean user = (UserBean) session.getAttribute("loginUser");
        System.out.println("register.jsp: loginUser=" + (user != null ? user.getName() : "null") + ", Session ID=" + session.getId());
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
    %>
    <%
        String error = (String) request.getAttribute("error");
        String success = (String) request.getAttribute("success");
        if (error != null) {
    %>
        <p style="color:red"><%= error %></p>
    <%
        }
        if (success != null) {
    %>
        <p style="color:green"><%= success %></p>
    <%
        }
    %>
    <input type="button" value="続けて登録" onclick="location.href='<%= request.getContextPath() %>/student_add.html'">
    <input type="button" value="メニューに戻る" onclick="location.href='<%= request.getContextPath() %>/menu.jsp'">
    <input type="button" value="ホームに戻る" onclick="location.href='<%= request.getContextPath() %>/index.jsp'">
</body>
</html>