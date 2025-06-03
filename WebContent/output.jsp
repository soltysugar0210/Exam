<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="bean.StulistBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>書籍情報確認</title>
</head>
<body>
    <h2>書籍情報の確認</h2>

    <%
        StulistBean Sl = (StulistBean) request.getAttribute("Sl");
    %>
        <p>【学生番号】<%= Sl.getId() %></p>
        <p>【名前】<%= Sl.getName() %></p>
        <p>【クラス】<%= Sl.getClass_name() %></p>

    <br>
        <input type="button" value="ホームに戻る" onclick="location.href='index.jsp'">
</body>
</html>
