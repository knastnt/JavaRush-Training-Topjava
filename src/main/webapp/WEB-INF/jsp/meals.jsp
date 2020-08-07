<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <div class="jumbotron pt-4">
        <div class="container">
            <h3 class="text-center"><spring:message code="meal.title"/></h3>

            <form method="get" action="meals/filter">
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
            </form>
            <hr>
            <a href="meals/create" class="btn btn-primary"><spring:message code="meal.add"/></a>
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
                    <tr class="<%= meal.isExcess() ? "table-danger" : "" %>">
                        <td>
                                <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                                ${fn:formatDateTime(meal.dateTime)}
                        </td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a href="meals/update?id=${meal.id}" class="btn"><span class="fa fa-pencil"></span></a></td>
                        <td><a href="meals/delete?id=${meal.id}" class="btn"><span class="fa fa-remove"></span></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>