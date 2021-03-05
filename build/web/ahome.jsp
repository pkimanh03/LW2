<%-- 
    Document   : ahome
    Created on : Jan 25, 2021, 7:25:55 PM
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
        <script type="text/javascript">
            function displayCreateForm() {
                if (document.getElementById('btnInsert').value === 'hide') {
                    document.getElementById('btnInsert').value = 'insert new question';
                    document.getElementById('createForm').style.display = 'none';
                } else {
                    document.getElementById('btnInsert').value = 'hide';
                    document.getElementById('createForm').style.display = 'block';
                }
            }
            function displaySearchForm() {
                if (document.getElementById('btnSearch').value === 'hide') {
                    document.getElementById('btnSearch').value = 'search question';
                    document.getElementById('searchForm').style.display = 'none';
                } else {
                    document.getElementById('btnSearch').value = 'hide';
                    document.getElementById('searchForm').style.display = 'block';
                }
            }
        </script>
        <h1>Admin Page</h1>
        <h2>Welcome, ${sessionScope.ACCOUNT.accountName}</h2>
        <h3><a href="logout">Logout</a></h3>
        <!--CREATE-->
        <input type="button" id="btnInsert" value="insert new question" onclick="displayCreateForm()"/>
        <p style="color: red">${requestScope.MSG}</p>
        <div id="createForm" style="border: 1px solid black; display: none">
            <h3 style="text-align: center">INSERT A QUESTION</h3>
            <form action="CreateQuestion" method="POST" style="width: 100%">
                <table style="width: 100%">
                    <tr>
                        <td style="width: 10%">Subject</td>
                        <td style="width: 90%">
                            <select style="width: 99%" name="subject">
                                <c:forEach var="s" items="${sessionScope.SUBJECTS}">
                                    <option value="${s.id}">${s.subjectName}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 10%">Question</td>
                        <td style="width: 90%">
                            <textarea style="width: 99%" name="question" required="true"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 10%">Answer 1</td>
                        <td style="width: 90%">
                            <textarea style="width: 99%" name="answer1" required="true"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 10%">Answer 2</td>
                        <td style="width: 90%">
                            <textarea style="width: 99%" name="answer2" required="true"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 10%">Answer 3</td>
                        <td style="width: 90%">
                            <textarea style="width: 99%" name="answer3" required="true"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 10%">Answer 4</td>
                        <td style="width: 90%">
                            <textarea style="width: 99%" name="answer4" required="true"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 10%">Correct answer</td>
                        <td style="width: 90%">
                            <select style="width: 99%" name="correctAnswer">
                                <option value="1">Answer 1</option>
                                <option value="2">Answer 2</option>
                                <option value="3">Answer 3</option>
                                <option value="4">Answer 4</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input style="width: 90%; float: right" type="submit" name="action" value="Create" />
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <!--SEARCH-->
        <input type="button" id="btnSearch" value="hide" onclick="displaySearchForm()"/>
        <div id="searchForm" style="border: 1px solid black; display: block">
            <h3 style="text-align: center">SEARCH QUESTION</h3>
            <form action="SearchQuestion" method="POST">
                <input type="hidden" name="index" value="1" />
                <select name="subject">
                    <c:forEach var="s" items="${sessionScope.SUBJECTS}">
                        <option value="${s.id}">${s.subjectName}</option>
                    </c:forEach>
                </select>
                <input type="submit" name="search" value="search by subject" />
                <input type="text" name="keyword" />
                <input type="submit" name="search" value="search by keyword" />
            </form>
            <c:if test="${requestScope.QUESTIONS != null}" var="checkQuestion">
                <div style="width: 48%; float: left">
                    <table border="1" style="border: 1px solid black; width: 100%">
                        <thead>
                            <tr>
                                <td>ID</td>
                                <td>Subject</td>
                                <td>Question</td>
                                <td>Answers</td>
                                <td>Action</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="question" items="${requestScope.QUESTIONS}">
                                <tr>
                                    <td>${question.id}</td>
                                    <td>
                                        <select>
                                            <c:forEach var="s" items="${sessionScope.SUBJECTS}">
                                                <c:if test="${s.id == question.subjectId}" var="checkSelected">
                                                    <option value="${s.id}" selected="true">${s.subjectName}</option>
                                                </c:if>
                                                <c:if test="${!checkSelected}">
                                                    <option value="${s.id}">${s.subjectName}</option>
                                                </c:if>

                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>${question.questionContent}</td>
                                    <td>
                                        <ul>
                                            <c:forEach var="answer" items="${question.answers}">
                                                <c:if var="checkAnswerCorrect" test="${answer.isCorrect == true}">
                                                    <li style="color: red">${answer.answerContent}</li>
                                                    </c:if>
                                                    <c:if test="${!checkAnswerCorrect}">
                                                    <li>${answer.answerContent}</li>
                                                    </c:if>

                                            </c:forEach>
                                        </ul>
                                    </td>
                                    <td>
                                        <form action="ViewQuestion">
                                            <input type="hidden" name="id" value="${question.id}" />
                                            <input type="hidden" name="subject" value="${param.subject}" />
                                            <input type="hidden" name="index" value="${param.index}" />
                                            <input type="hidden" name="keyword" value="${param.keyword}" />
                                            <input type="hidden" name="search" value="${param.search}" />
                                            <input type="submit" value="View" />
                                        </form>
                                        <form action="DeleteQuestion">
                                            <input type="hidden" name="id" value="${question.id}" />
                                            <input type="hidden" name="subject" value="${param.subject}" />
                                            <input type="hidden" name="index" value="${param.index}" />
                                            <input type="hidden" name="keyword" value="${param.keyword}" />
                                            <input type="hidden" name="search" value="${param.search}" />
                                            <input type="submit" value="Delete" />
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <!--PAGING-->
                    <div>
                        <c:forEach begin="1" end="${requestScope.TOTAL_PAGE}" step="1" varStatus="counter">
                            <a href="SearchQuestion?subject=${param.subject}&keyword=${param.keyword}&index=${counter.count}&search=${param.search}">${counter.count}</a>
                        </c:forEach>
                    </div>
                </div>
                <!--UPDATE-->
                <div style="width: 48%; float: right; border: 1px solid black">
                    <c:if test="${requestScope.QUESTION != null}">
                        <form action="UpdateQuestion" method="POST">
                            <input type="hidden" name="search" value="${param.search}" />
                            <input type="hidden" name="index" value="${param.index}" />
                            <input type="hidden" name="questionId" value="${requestScope.QUESTION.id}" />
                            <table style="width: 100%">
                                <tr>
                                    <td style="width: 10%">Subject</td>
                                    <td style="width: 90%">
                                        <select style="width: 99%" name="subject">
                                            <c:forEach var="s" items="${sessionScope.SUBJECTS}">
                                                <c:if test="${s.id == requestScope.QUESTION.subjectId}" var="checkSelected">
                                                    <option selected="true" value="${s.id}">${s.subjectName}</option>
                                                </c:if>
                                                <c:if test="${!checkSelected}">
                                                    <option value="${s.id}">${s.subjectName}</option>
                                                </c:if>

                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 10%">Question</td>
                                    <td style="width: 90%">
                                        <textarea style="width: 99%" name="questionContent" required="true">${requestScope.QUESTION.questionContent}</textarea>
                                    </td>
                                </tr>
                                <c:forEach var="answer" items="${requestScope.QUESTION.answers}" varStatus="index">
                                    <tr>

                                        <td style="width: 10%">Answer ${index.count}</td>
                                        <td style="width: 90%">
                                            <textarea style="width: 99%" name="answer${index.count}" required="true">${answer.answerContent}</textarea>
                                        </td>
                                    </tr>

                                </c:forEach>
                                <tr>
                                    <td style="width: 10%">Correct answer</td>
                                    <td style="width: 90%">
                                        <select style="width: 99%" name="correctAnswer">
                                            <c:forEach var="answer" items="${requestScope.QUESTION.answers}" varStatus="index">
                                                <c:if var="checkAnswer" test="${answer.isCorrect == true}">
                                                    <option selected="true" value="${index.count}">Answer ${index.count}</option>
                                                </c:if>
                                                <c:if test="${!checkAnswer}">
                                                    <option value="${index.count}">Answer ${index.count}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <input style="width: 90%; float: right" type="submit" name="action" value="Update" />
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </c:if>
                </div>
            </c:if>
        </div>
    </body>
</html>
