<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.GradeBean, bean.UserBean" %>
<%
// CSRFトークン生成
String csrfToken = java.util.UUID.randomUUID().toString();
session.setAttribute("csrfToken", csrfToken);
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>成績登録</title>
    <script src="<%= request.getContextPath() %>/js/grade.js"></script>
</head>
<body>
    <h2>成績登録</h2>

    <%-- エラーメッセージ --%>
    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <div id="error" style="color: red;"><%= error %></div>
    <% } %>

    <%-- 成功メッセージ --%>
    <% String success = (String) request.getAttribute("success"); %>
    <% if (success != null) { %>
        <div id="success" style="color: green;"><%= success %></div>
    <% } %>

    <%-- 登録データ表示 --%>
	<% GradeBean grade = (GradeBean) request.getAttribute("grade"); %>
	<% if (grade != null) { %>
    	<p>
        	【学生番号】<%= grade.getStudentId() %><br>
   			【生徒名】<%= grade.getStudentName() != null ? grade.getStudentName() : "N/A" %><br>
        	【科目】<%= grade.getSubjectName() != null ? grade.getSubjectName() : "N/A" %><br>
        	【回数】<%= grade.getCount() %><br>
       		【点数】<%= grade.getScore() %><br>
    	</p>
	<% } %>

    <form action="<%= request.getContextPath() %>/Gradesystem.action" method="POST" onsubmit="return validateForm()">
        <input type="hidden" name="csrfToken" value="<%= csrfToken %>">
        【学校コード】<br>
        <input type="number" id="school_id" name="school_id" oninput="fetchSubjects()" value="<%= request.getParameter("school_id") != null ? request.getParameter("school_id") : "" %>" required><br><br>
        【学生番号】<br>
        <input type="number" id="student_id" name="student_id" oninput="fetchStudentName()" value="<%= request.getParameter("student_id") != null ? request.getParameter("student_id") : "" %>" required>
        <span id="student_name"></span><br><br>
        【科目】<br>
        <select name="subject_id" id="subject_id" required>
            <option value="">選択してください</option>
        </select><br><br>
        【回数】<br>
        <input type="number" name="count" min="1" value="<%= request.getParameter("count") != null ? request.getParameter("count") : "" %>" required><br><br>
        【点数】<br>
        <input type="number" name="score" min="0" max="100" value="<%= request.getParameter("score") != null ? request.getParameter("score") : "" %>" required><br><br>
        <%-- 教師IDはセッションから取得 --%>
        <input type="hidden" name="teacher_id" value="<%= ((UserBean) session.getAttribute("loginUser")).getId() %>">
        <input type="submit" value="登録"><br><br>
    </form>
    <input type="button" value="ホームに戻る" onclick="location.href='<%= request.getContextPath() %>/logout.jsp'">
    <input type="button" value="メニューに戻る" onclick="location.href='<%= request.getContextPath() %>/menu.jsp'">
</body>
</html>