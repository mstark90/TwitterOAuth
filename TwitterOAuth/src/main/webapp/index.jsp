<%-- 
    Document   : index
    Created on : Oct 20, 2016, 10:04:12 AM
    Author     : mstark
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Twitter OAuth Test</title>
        <script src="https://code.jquery.com/jquery-3.1.1.min.js"   integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="   crossorigin="anonymous"></script>
        <script type="text/javascript">
            function getName() {
                $.ajax({
                    url: "getName"
                }).done(function(data) {
                    $("#name").text(data.name);
                });
            }
        </script>
        <c:if test="${sessionScope.user != null}">
            <script type="text/javascript">
                $(document).ready(function() {
                    getName();
                })
            </script>
        </c:if>
    </head>
    <body>
        <c:if test="${sessionScope.user != null}">
            <span>
                Hello, 
            </span>
            <span id="name">
                
            </span>
            <span>
                !
            </span>
            <form action="logout" method="get">
                <button type="submit">
                    Log Out
                </button>
            </form>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <form action="login" method="post">
                <button type="submit">
                    Log in via Twitter
                </button>
            </form>
        </c:if>
    </body>
</html>
