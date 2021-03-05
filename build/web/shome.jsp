<%-- 
    Document   : shome
    Created on : Jan 25, 2021, 7:28:13 PM
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
            function goNextQuestion(current) {
                if (document.getElementById('question' + (current + 1))) {
                    document.getElementById('question' + current).style.display = 'none';
                    document.getElementById('question' + (current + 1)).style.display = 'block';
                }
                if (!document.getElementById('question' + (current + 2))) {
                    document.getElementById('btnSubmit').style.display = 'block'
                }

            }
            function startQuiz() {
                document.getElementById('btnStart').style.display = 'none';
                document.getElementById('quiz').style.display = 'block';
                document.getElementById('question1').style.display = 'block';
                setTimeout(function(){
                    alert("Time up! We submit your test");
                    document.getElementById("testForm").submit();
                }, ${requestScope.TIMER} * 60 * 1000);
            }
            
            function handleViewHistory() {
                document.getElementById('history').style.display = 'block';
            }
        </script>
        <h1>Student Page</h1>
        <h2>Welcome, ${sessionScope.ACCOUNT.accountName}</h2>
        <h3><a href="logout">Logout</a></h3>
        <!--HISTORY-->
        <input id="view-history" type="button" value="View history" onclick="handleViewHistory()" />
        <div id="history" style="display: none">
            <form action="GetHistory" method="POST">
                <select name="subject">
                    <c:forEach items="${sessionScope.SUBJECTS}" var="subject">
                        <option value="${subject.id}">${subject.subjectName}</option>
                    </c:forEach>
                </select>
                <input type="submit" name="action" value="Get history" />
            </form>
            <c:if test="${requestScope.HISTORIES != null}">
                <c:forEach varStatus="counter" begin="1" end="${requestScope.TOTAL_PAGE}">
                    <a href="GetHistory?subject=${param.subject}&index=${counter.count}">${counter.count}</a>
                </c:forEach>
                    <c:forEach items="${requestScope.HISTORIES}" var="history">
                        <div>
                            <p>Date: ${history.createdAtString}</p>
                            <p>Score: ${history.result}</p>
                        </div>
                </c:forEach>
            </c:if>
        </div>
        <!--GET QUIZ-->
        <form action="TakeQuiz" method="POST">
            <select style="width: 50%" name="subject">
                <c:forEach var="s" items="${sessionScope.SUBJECTS}">
                    <option value="${s.id}">${s.subjectName}</option>
                </c:forEach>
            </select>
            <input type="submit" name="search" value="Take Quiz" />
        </form>
        <h4>You have ${requestScope.TIMER} minute(s) to answer ${requestScope.NUMBER_QUESTIONS} question(s)</h4>
        <h4>Your result: ${(10 / requestScope.TOTAL) * requestScope.RIGHT} </h4>
        <!--MAKE QUIZ-->
        <form action="CheckQuiz" method="POST" id="testForm">
            <c:if test="${requestScope.QUIZ != null}">
                <input type="button" id="btnStart" value="Start" onclick="startQuiz()" />
                <input type="hidden" id="score" value="0" />
                <div id="quiz" style="border: 1px solid black; display: none">
                    <c:forEach var="question" varStatus="counter" items="${requestScope.QUIZ}">
                        <div id="question${counter.count}" style="border: 1px solid black; display: none">
                            <h3>Question ${counter.count}</h3>
                            <h3>${question.questionContent}</h3>
                                <c:forEach var="answer" varStatus="subCounter" items="${question.answers}">
                                    <p>${subCounter.count}/ ${answer.answerContent}</p>
                                </c:forEach>
                            <select style="width: 100%" name="answer${question.id}">
                                <option value="0">Select Answer</option>
                                <c:forEach var="answer" varStatus="subCounter" items="${question.answers}">
                                    <option value="${answer.id}">${answer.answerContent}</option>
                                </c:forEach>
                            </select>
                            <input type="button" id="btnNext" style="display: block" value="Next" onclick="goNextQuestion(${counter.count})" />
                        </div>
                    </c:forEach>
                    <input type="submit" value="Submit" style="display: none" id="btnSubmit" />
                </div>
            </c:if>
        </form>
    </body>
</html>
