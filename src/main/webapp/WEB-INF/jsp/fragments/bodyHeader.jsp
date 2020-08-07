<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav class="navbar navbar-dark bg-dark py-0">
    <div class="container">
        <a href="" class="navbar-brand"><img src="resources/images/icon-meal.png"> <spring:message code="app.title"/></a>
        <form class="form-inline my-2">
            <div class="btn-group" role="group">
                <a class="btn btn-light" href="meals"><spring:message code="meal.title"/></a>
                <a class="btn btn-light" href="users"><spring:message code="user.title"/></a>
                <a class="btn btn-light" href="">
                    <span class="fa fa-sign-in"></span>
                </a>
            </div>
        </form>
    </div>
</nav>
