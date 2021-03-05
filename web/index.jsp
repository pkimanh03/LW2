<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Quiz Online</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <form action="register" method="POST">
            <input type="email" name="email" required="true" placeholder="Email"/><br/>
            <input type="password" name="password" required="true" placeholder="Password" /><br/>
            <input type="text" name="fullname" required="true" placeholder="Fullname" /><br/>
            <input type="submit" name="action" value="Register" />
        </form>
        <form action="login" method="POST">
            <input type="email" name="email" required="true" placeholder="Email"/><br/>
            <input type="password" name="password" required="true" placeholder="Password" /><br/>
            <input type="submit" name="action" value="Login" />
        </form>
    </body>
</html>
