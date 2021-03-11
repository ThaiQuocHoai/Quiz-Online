<%-- 
    Document   : quiz
    Created on : Feb 6, 2021, 3:40:35 PM
    Author     : QH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">  
        <title>FPT University | Quiz</title>
    </head>
    <body>

        <div style="display: flex">
            <div class="col-2 text-center" style="border: 2px solid black; border-radius: 10px; ">
                Subject: ${sessionScope.SUBJECT} <br/>
                Time: ${sessionScope.TIME} minutes <br/>
                <table>
                    <tr>
                        <td>
                            <form action="http://www.google.fr">
                                <input type="hidden" id="time" class="btn" formtarget="_blank" value="">
                            </form>
                        </td>
                        <td>${sessionScope.HOUR}:${sessionScope.MIN}:${sessionScope.SECOND}</td>
                    </tr>
                </table>
                <form action="submit" method="POST">
                    <input type="submit" id="submit" value="Submit" name="btAction" />
                </form>
            </div>
            <form action="takeQuiz" method="POST">
                <div class="col-10">
                    <p>${sessionScope.QUESTIONQUIZ.content}</p>
                    <input type="hidden" name="txtQuestion" value="${sessionScope.QUESTIONQUIZ.id}" />
                    <c:forEach var="ans" items="${sessionScope.LISTANSWER}">
                        <input type="radio" <c:if test="${ANSWER_CHOOSE eq ans.content}">checked</c:if> onclick="Click()" name="Answer" value="${ans.content}" /> ${ans.content} <br/>

                    </c:forEach>
                    <c:forEach var="i" begin="1" end="${sessionScope.ENDPAGE}">
                        <a <c:if test="${sessionScope.INDEX eq i}">style="border: 2px solid black"</c:if> href="quiz?index=${i}">${i}</a>
                    </c:forEach>
                    <input type="submit" id="button" hidden="true" value="" />
                </div>
            </form>
        </div>


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>

        <script>
                            function Click() {
                                document.getElementById('button').click();
                            }
                            ;
                            function toTimeString(seconds) {
                                return (new Date(seconds * 1000)).toUTCString().match(/(\d\d:\d\d:\d\d)/)[0];
                            }

                            function startTimer() {
                                var nextElem = $(this).parents('td').next();
                                var duration = nextElem.text();
                                var a = duration.split(':');
                                var seconds = (+a[0]) * 60 * 60 + (+a[1]) * 60 + (+a[2]);
                                setInterval(function () {
                                    seconds--;
                                    if (seconds >= 0) {
                                        nextElem.html(toTimeString(seconds));
                                    }
                                    if (seconds <= 20) {
                                        nextElem.css('color', 'red');
                                    }
                                    if (seconds === 0) {
                                        document.getElementById('submit').click();
                                        clearInterval(seconds);
                                    }
                                }, 1000);
                            }
                            $('#time').on('click', startTimer);
                            jQuery(function () {
                                jQuery('#time').click();
                            });

        </script>       

    </body>
</html>
