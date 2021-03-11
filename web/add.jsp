<%-- 
    Document   : add
    Created on : Feb 1, 2021, 1:37:35 AM
    Author     : QH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FPT University | Add</title>
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

            footer {
                background-color: #53b881;
                height: 200px;
                margin-top: 80px;
            }
            .disable{
                pointer-events: none;
                opacity: 0.6;
            }

            #ans{
                margin-top: 10px;
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
                        <li class="nav-item"><a href="searchSub?dropSub=${sessionScope.SUBSELECT}">Admin Page</a></li>
                        <li class="nav-item"><a href="logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="add">
            <div class="container" style="border: 1px solid green; width: 1400px; height: auto; border-radius: 10px; padding: 20px; margin-top: 50px;">
                <form action="addNew" method="POST">
                    <select name="dropSub">
                        <c:forEach var="sub" items="${sessionScope.SUB}">
                            <option <c:if test="${sessionScope.SUBSELECT eq sub.name}">selected</c:if>>${sub.name}</option>
                        </c:forEach>
                    </select>
                    <div class="body-result container" style="border: 2px solid gray; margin-top:20px; padding: 20px;  ">
                        <c:if test="${not empty requestScope.ANSWERERROR}">
                            <font style="color: red">${requestScope.ANSWERERROR}</font>
                        </c:if>
                        <div class="col-auto">
                            <input style="font-weight: bold;" type="text" id="inputPassword6" class="form-control" aria-describedby="passwordHelpInline"
                                   name="txtQuestion" value="${param.txtQuestion}" placeholder="Question"/>
                        </div>
                        <input type="radio" name="rdoAnswer" value="1"/>
                        <input type="text" id="ans" name="txtAnswer1"  value="${param.txtAnswer1}" placeholder="Answer 1"/><br/>
                        <input type="radio" name="rdoAnswer" value="2" />
                        <input type="text" id="ans" name="txtAnswer2" value="${param.txtAnswer2}" placeholder="Answer 2"/><br/>
                        <input type="radio" name="rdoAnswer" value="3" />
                        <input type="text" id="ans" name="txtAnswer3" value="${param.txtAnswer3}" placeholder="Answer 3"/><br/>
                        <input type="radio" name="rdoAnswer" value="4" />
                        <input type="text" id="ans" name="txtAnswer4" value="${param.txtAnswer4}" placeholder="Answer 4"/><br/>
                        <input type="submit" style="margin-top: 20px;" class="btn btn-outline-success" value="Add" name="btAction" />
                    </div> 
                </form>
            </div>
        </div>

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
