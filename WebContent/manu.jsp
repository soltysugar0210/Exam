<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String loginUser = (String) session.getAttribute("loginUser");
    if (loginUser == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>メニュー</title>
</head>
<body>
    <h3>ようこそ <%= loginUser %> さん</h3>
    <ul>
        <li><a href="student.jsp">学生情報</a></li>
        <li><a href="grade.jsp">成績</a></li>
        <li><a href="admin.jsp">管理者用</a></li>
        <li><a href="logout.jsp">ログアウト</a></li>
    </ul>
</body>
</html>
