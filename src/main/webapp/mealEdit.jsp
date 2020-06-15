<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
</head>
    <body>
        <div class="container">
            <h3><a href="${pageContext.request.contextPath}/meals">Back</a></h3>
            <h2>Meal</h2>

            <div class="row">
                <form action="" method="post" class="shadow col">
<%--                    <jsp:useBean id="mealTo" scope="page" class="ru.javawebinar.topjava.model.MealTo"></jsp:useBean>--%>
                    <input type="hidden" name="id" value="${mealTo.id}"/>
                    <div class="form-group">
                        <label for="datetime-input" class="col-form-label">Время</label>
                        <c:set var="outDate" value=""></c:set>
                        <c:if test="${mealTo.dateTime!=null}">
                            <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both"/>
                            <fmt:formatDate value="${parsedDate}" type="date" pattern="dd.MM.yyyy HH:mm" var="outDate"/>
                        </c:if>

                        <input class="form-control" type="datetime-local" placeholder="01.01.1970 00:00" id="datetime-input" value="${outDate}" name="dateTime">
                    </div>
                    <div class="form-group">
                        <label for="description-input" class="col-form-label">Описание</label>
                        <input class="form-control" type="text" id="description-input" placeholder="Описание" value="${mealTo.description == null ? "" : mealTo.description}" name="description">
                    </div>
                    <div class="form-group">
                        <label for="calories-input" class="col-xs-2 col-form-label">Калории</label>
                        <input class="form-control" type="number" id="calories-input" placeholder="0" value="${mealTo.calories == null ? "" : mealTo.calories}" name="calories">
                    </div>

                    <button type="submit" class="btn btn-primary mb-3">${mealTo.id == null ? "Добавить" : "Изменить"}</button>
                </form>
            </div>
        </div>
    </body>
</html>