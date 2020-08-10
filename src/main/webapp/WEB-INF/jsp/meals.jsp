<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meals.js" defer></script>
<section>
    <div class="jumbotron pt-4">
        <div class="container">
            <h3 class="text-center"><spring:message code="meal.title"/></h3>

            <form id="filterForm" method="get" action="meals/filter">
                <div class="form-group">
                    <label for="startDate"><spring:message code="meal.startDate"/>:</label>
                    <input class="form-control" id="startDate" type="date" name="startDate" value="${param.startDate}">
                </div>
                <div class="form-group">
                    <label for="endDate"><spring:message code="meal.endDate"/>:</label>
                    <input class="form-control" id="endDate" type="date" name="endDate" value="${param.endDate}">
                </div>

                <div class="form-group">
                    <label for="startTime"><spring:message code="meal.startTime"/>:</label>
                    <input class="form-control" id="startTime" type="time" name="startTime" value="${param.startTime}">
                </div>
                <div class="form-group">
                    <label for="endTime"><spring:message code="meal.endTime"/>:</label>
                    <input class="form-control" id="endTime" type="time" name="endTime" value="${param.endTime}">
                </div>
                <button type="submit" class="btn btn-secondary"><spring:message code="meal.filter"/></button>
                <a onclick="filter()" class="btn btn-secondary"><spring:message code="meal.filter"/> AJAX</a>
            </form>
            <hr>
            <a href="meals/create" class="btn btn-primary"><spring:message code="meal.add"/></a>
            <button class="btn btn-primary" onclick="add()">
                <span class="fa fa-plus"></span>
                <spring:message code="common.add"/>
            </button>
             <table class="table table-striped table-hover" id="datatable"><!-- border="1" cellpadding="8" cellspacing="0">-->
                <thead>
                <tr>
                    <th><spring:message code="meal.dateTime"/></th>
                    <th><spring:message code="meal.description"/></th>
                    <th><spring:message code="meal.calories"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${meals}" var="meal">
                    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
<%--                    <tr data-mealExcess="${meal.excess}">--%>
                    <tr class="<%= meal.isExcess() ? "table-danger" : "" %>" data-id="${meal.id}">
                        <td>
                                <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                                ${fn:formatDateTime(meal.dateTime)}
                        </td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
<%--                        <td><a href="meals/update?id=${meal.id}" class="btn"><span class="fa fa-pencil"></span></a></td>--%>
                        <td><a class="edit"><span class="fa fa-pencil"></span></a></td>
                        <td><a class="delete"><span class="fa fa-remove"></span></a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</section>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="meal.add"/></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form method="post" action="meals" id="detailsForm" content="app">
                    <input type="hidden" name="id" value="${meal.id}">
                    <div class="form-group">
                        <label for="dateTime"><spring:message code="meal.dateTime"/>:</label>
                        <input class="form-control" id="dateTime" type="datetime-local" name="dateTime" value="${meal.dateTime}" required>
                    </div>
                    <div class="form-group">
                        <label for="description"><spring:message code="meal.description"/>:</label>
                        <input class="form-control" id="description" type="text" size=40 name="description" value="${meal.description}" required>
                    </div>
                    <div class="form-group">
                        <label for="calories"><spring:message code="meal.calories"/>:</label>
                        <input class="form-control" id="calories" type="number" name="calories" value="${meal.calories}" required>
                    </div>
                    <a class="btn btn-primary" onclick="save()"><spring:message code="common.save"/></a>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>