<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>教師登録</title>
    <script src="<%= request.getContextPath() %>/js/teacher.js"></script>
</head>
<body>
    <h2>教師登録</h2>

    <%-- エラーメッセージの表示 --%>
    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <div id="error"><%= error %></div>
    <% } %>

    <%-- 成功メッセージの表示 --%>
    <% String success = (String) request.getAttribute("success"); %>
    <% if (success != null) { %>
        <div id="success"><%= success %></div>
    <% } %>

    <form action="<%= request.getContextPath() %>/UserbeanAction" method="POST" onsubmit="return validateForm()">
        【教師ID】<br>
        <input type="number" name="id" value="<%= request.getParameter("id") != null ? request.getParameter("id") : "" %>" required><br><br>
        【学校コード】<br>
        <input type="number" name="school_id" value="<%= request.getParameter("school_id") != null ? request.getParameter("school_id") : "" %>" required><br><br>
        【名前】<br>
        <input type="text" name="name" maxlength="50" value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>" required><br><br>
        【パスワード】<br>
        <input type="password" name="password" required><br><br>
        【権限】<br>
        <input type="number" name="privilege" value="<%= request.getParameter("privilege") != null ? request.getParameter("privilege") : "" %>" required><br><br>
        <input type="submit" value="登録"><br><br>
    </form>
    <input type="button" value="ホームに戻る" onclick="location.href='<%= request.getContextPath() %>/logout.jsp'">
    <input type="button" value="メニューに戻る" onclick="location.href='<%= request.getContextPath() %>/menu.jsp'">
</body>
</html>