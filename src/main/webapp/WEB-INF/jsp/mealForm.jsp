<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<%--    `meal.new` cause javax.el.ELException - bug tomcat --%>
    <div class="jumbotron pt-4">
        <div class="container">
            <h3 class="text-center"><spring:message code="${meal.isNew() ? 'meal.add' : 'meal.edit'}"/></h3>
            <hr>
            <form method="post" action="meals" id="detailsForm">
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
                <button type="submit" class="btn btn-primary"><spring:message code="common.save"/></button>
                <button onclick="window.history.back()" type="button" class="btn btn-secondary"><spring:message code="common.cancel"/></button>
            </form>
        </div>
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
