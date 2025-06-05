<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.StulistBean" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>生徒登録</title>
    <script src="<%= request.getContextPath() %>/js/student.js"></script>
</head>
<body>
    <h2>生徒登録</h2>

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

    <%-- 登録データの表示（nullチェック付き） --%>
    <% StulistBean Sl = (StulistBean) request.getAttribute("Sl"); %>
    <% if (Sl != null) { %>
        <p>
            【学生番号】<%= Sl.getId() %><br>
            【クラス】<%= Sl.getClass_name() %><br>
            【名前】<%= Sl.getName() %><br>
            【入学年度】<%= Sl.getEntrance_year() %><br>
        </p>
    <% } %>

    <form action="<%= request.getContextPath() %>/Beansystem.action" method="POST" onsubmit="return validateForm()">
        【学生番号】<br>
        <input type="number" name="id" value="<%= request.getParameter("id") != null ? request.getParameter("id") : "" %>" required><br><br>
        【学校コード】<br>
        <input type="number" id="school_id" name="school_id" oninput="updateClassNames()" value="<%= request.getParameter("school_id") != null ? request.getParameter("school_id") : "" %>" required><br><br>
        【クラス】<br>
        <select name="class_name" id="class_name" required>
            <option value="">選択してください</option>
        </select><br><br>
        【名前】<br>
        <input type="text" name="name" maxlength="50" value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>" required><br><br>
        【入学年】<br>
        <input type="number" name="entrance_year" min="1900" max="9999" value="<%= request.getParameter("entrance_year") != null ? request.getParameter("entrance_year") : "" %>" required><br><br>
        <input type="checkbox" name="student_flag" <%= request.getParameter("student_flag") != null && request.getParameter("student_flag").equals("on") ? "checked" : "" %>>在学中<br><br>
        <input type="submit" value="登録"><br><br>
    </form>
    <input type="button" value="ホームに戻る" onclick="location.href='<%= request.getContextPath() %>/logout.jsp'">
    <input type="button" value="メニューに戻る" onclick="location.href='<%= request.getContextPath() %>/menu.jsp'">
</body>
</html>