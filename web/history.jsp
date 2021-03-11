<%-- 
    Document   : history
    Created on : Feb 7, 2021, 12:17:17 AM
    Author     : QH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FPT University | History</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <style>
            * {
                margin: 0;
                padding: 0;
            }

            .nav-begin {
                height: 130px;
                background-color: #198754;
                text-align: center;
            }

            .nav-item {
                margin-right: 20px;
                font-size: 20px;
            }

            a {
                color: black;
                text-decoration: none;
            }

            a:hover {
                border: 2px solid #198754;
                color: #198754;
            }

            .paging{
                text-align: center;
                margin-top: 50px;
            }
            .paging a{
                padding: 0 20px;
                font-size: 20px;
                border: 1px solid gray;
                margin: 0 10px;
                transition: 0.3s;
            }
            .paging a:hover{
                transition: 0.3s;
                background-color: green;
                color: white;
            }

            footer {
                background-color: #53b881;
                height: 200px;
                margin-top: 80px;
            }
            .disable{
                pointer-events: none;
                opacity: 0.6;
            }
        </style>
    </head>
    <body>
        <div class="nav-begin">
            <img style="margin-top: 15px;" src="https://daihoc.fpt.edu.vn/media/2020/06/logo.jpg">
        </div>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <div>
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item disable"><a href="#">Welcome ${sessionScope.USERINFO.fullname}</a></li>
                        <li class="nav-item"><a href="home">Home</a></li>
                        <li class="nav-item"><a href="logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <form action="searchHistory" method="POST">
            <select name="dropSubject">
                <c:forEach var="sub" items="${sessionScope.LISTSUB}">
                    <option <c:if test="${sub eq sessionScope.SUBCHOOSE}">selected</c:if>>${sub}</option>
                </c:forEach>
            </select>
            <input type="submit" name="btAction" value="Search" />
        </form>
        <c:if test="${empty sessionScope.HISMAP}">
            <h2 style="color: red">Empty History </h2>
        </c:if>
        <c:if test="${not empty sessionScope.HISMAP}">

            <div class="body-result container" style="border: 2px solid gray; margin-top:20px; padding: 20px;  ">
                <h2 style="color: green">${sessionScope.SUBCHOOSE}</h2>
                <c:forEach var="map" items="${sessionScope.HISMAP}">
                    <div class="col-auto">
                        <input readonly style="font-weight: bold; color: red; <c:if test="${map.key.score >= 5}"> color: green;</c:if>" type="text" id="inputPassword6" class="form-control" aria-describedby="passwordHelpInline" 
                               value="Email: ${map.key.email} , Date: ${map.key.date} , Score: ${map.key.score}">
                    </div>

                    <c:forEach var="hisdetailDTO" items="${map.value}">
                        <div class="col-auto">
                            <input readonly style="font-weight: bold; color: black" type="text" id="inputPassword6" class="form-control" aria-describedby="passwordHelpInline" 
                                   value="Question: ${hisdetailDTO.question},
                                   <c:if test="${hisdetailDTO.correct_answer eq hisdetailDTO.user_answer}">True</c:if>
                                   <c:if test="${hisdetailDTO.correct_answer ne hisdetailDTO.user_answer}">False</c:if>"
                                       >
                            </div>

                            - Correct answer:  <font style="color: green"> ${hisdetailDTO.correct_answer}</font> <br/>
                        - Your answer: <font <c:if test="${hisdetailDTO.correct_answer eq hisdetailDTO.user_answer}">style="color: green;"</c:if>><c:if test="${empty hisdetailDTO.user_answer}">
                            No select
                        </c:if> 
                        <c:if test="${not empty hisdetailDTO.user_answer}">
                            ${hisdetailDTO.user_answer}
                        </c:if>
                        </font>
                        <br/>
                    </c:forEach>
                    <br/> <br/>
                </c:forEach>
            </div>

            <c:if test="${sessionScope.ENDPAGE > 1}">
                <div class="paging">
                    <c:forEach var="i" begin="1" end="${sessionScope.ENDPAGE}">
                        <a href="searchHistory?index=${i}" <c:if test="${sessionScope.INDEX1 eq i}">style="background-color: green"</c:if>>${i}</a>
                    </c:forEach>
                </div>
            </c:if>
        </c:if>

        <footer id="colorlib-footer" role="contentinfo">
            <div class="container" style="margin-top: 20px;">
                <div class="row row-pb-md">
                    <div class="col footer-col colorlib-widget">
                        <img style="margin-top: 15px;" src="https://daihoc.fpt.edu.vn/media/2020/06/logo.jpg">
                    </div>
                    <div class="col footer-col colorlib-widget" style="margin-left: 70px; margin-top: 30px;">
                        <h4>Customer Care</h4>
                        <p>
                        <ul class="colorlib-footer-links">
                            <li><a href="#">About us</a></li>
                            <li><a href="#">FAQ</a></li>
                            <li><a href="#">Support</a></li>
                        </ul>
                        </p>
                    </div>
                    <div class="col footer-col colorlib-widget" style="margin-top: 30px;">
                        <h4>Information</h4>
                        <p>
                        <ul class="colorlib-footer-links">
                            <li><a href="#">Information</a></li>
                            <li><a href="#">Privacy Policy</a></li>
                        </ul>
                        </p>
                    </div>
                    <div class="col footer-col" style="margin-top: 30px;">
                        <h4>Contact Information</h4>
                        <ul class="colorlib-footer-links">
                            <li>9 District - Ho Chi Minh City, <br> Hightech 9 district</li>
                            <li><a href="tel://1234567920">84+ 123456789</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </footer>
        <div style="background-color:  #198754; height: 30px; text-align: center;">
            Write@ 2021 by Thai Quoc Hoai
        </div>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"
                integrity="sha384-q2kxQ16AaE6UbzuKqyBE9/u/KzioAlnx2maXQHiDX9d4/zp8Ok3f+M7DPm+Ib6IU"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js"
                integrity="sha384-pQQkAEnwaBkjpqZ8RU1fF1AKtTcHJwFl3pblpTlHXybJjHpMYo79HY3hIi4NKxyj"
        crossorigin="anonymous"></script>


    </body>
</html>
