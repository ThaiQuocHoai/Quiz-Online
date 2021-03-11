<%-- 
    Document   : login
    Created on : Jan 28, 2021, 9:27:28 PM
    Author     : QH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>FPT University | Login</title>
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

            .form-login1 {
                background-color: #fafafa;
                width: 600px;
                height: 500px;
                margin-top: 80px;
                padding: 100px;
            }

            p{
                margin-bottom: 0px;
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
                        <li class="nav-item"><a href="#">Home</a></li>
                        <li class="nav-item"><a href="#">Login</a></li>
                        <li class="nav-item"><a href="#">Course</a></li>
                    </ul>
                </div>

                <div>
                    <form class="d-flex">
                        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                        <button class="btn btn-outline-success" type="submit">Search</button>
                    </form>
                </div>
            </div>
        </nav>

        <div class="form-login1 container">
            <div class="row">
                <div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <c:if test="${not empty requestScope.SIGNUP}">
                                <font style="color: green">${requestScope.SIGNUP}</font>
                            </c:if>
                            <h3 class="panel-title">Please sign in</h3>
                        </div>
                        <div class="panel-body">
                            <form accept-charset="UTF-8" role="form" action="login" method="POST">
                                <c:if test="${not empty requestScope.INVALID}">
                                    <font style="color: red">${requestScope.INVALID}</font>
                                </c:if>
                                <fieldset>
                                    <p>Username</p>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="E-mail" name="txtUsername" value="${param.txtUsername}" type="text">
                                    </div>
                                    <p>Password</p>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="Password" name="txtPassword" type="password"
                                               value="">
                                    </div>
                                    <input style="margin-top: 30px;" class="btn btn-lg btn-success btn-block" type="submit" name="btAction" value="Sign in"><br/>
                                    <a href="signup.jsp">Create a new account?</a>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
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
