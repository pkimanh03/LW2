<%-- 
    Document   : error.jsp
    Created on : Jan 25, 2021, 7:05:11 PM
    Author     : pkimanh03
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Online</title>
    </head>
    <body>
        <h1>Oops! Something went wrong ... </h1>
        <h3>${requestScope.ERROR}</h3>
    </body>
</html>
