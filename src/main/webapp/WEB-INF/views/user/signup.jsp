<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang='ko'>
    <head>
        <meta charset='UTF-8'>
        <title>Document</title>
    </head>
    <body>
        <div class="container">
            <form action="/signup" method="POST">
                <input type="text" name="loginId" placeholder="아이디"><br>
                <input type="password" name="password" placeholder="비밀번호"><br>
                <input type="text" name="email" placeholder="이메일"><br>
                <button type="submit">회원가입</button>
            </form>
        </div>
    </body>
</html>