<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
</head>
<body>
    <h2>ログイン</h2>
    <form action="Login.action" method="post">
        ユーザー名：<input type="text" name="username" required><br><br>
        パスワード：<input type="password" name="password" required><br><br>
        <input type="submit" value="ログイン">
    </form>

    <p style="color:red;">
        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
    </p>
</body>
</html>
