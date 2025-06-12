<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, bean.StulistBean" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>生徒一覧</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h2>生徒一覧</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <div id="error" style="color: red;"><%= error %></div>
    <% } %>

    <% List<StulistBean> students = (List<StulistBean>) request.getAttribute("students"); %>
    <% if (students != null && !students.isEmpty()) { %>
        <table>
            <tr>
                <th>学生番号</th>
                <th>学校コード</th>
                <th>クラス名</th>
                <th>氏名</th>
                <th>入学年</th>
                <th>生徒フラグ</th>
            </tr>
            <% for (StulistBean student : students) { %>
                <tr>
                    <td><%= student.getId() %></td>
                    <td><%= student.getSchool_id() %></td>
                    <td><%= student.getClass_name() != null ? student.getClass_name() : "N/A" %></td>
                    <td><%= student.getName() != null ? student.getName() : "N/A" %></td>
                    <td><%= student.getEntrance_year() %></td>
                    <td><%= student.getStudent_flag() %></td>
                </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>登録されている生徒はいません。</p>
    <% } %>

    <br>
    <input type="button" value="ホームに戻る" onclick="location.href='<%= request.getContextPath() %>/logout.jsp'">
    <input type="button" value="メニューに戻る" onclick="location.href='<%= request.getContextPath() %>/menu.jsp'">
</body>
</html>