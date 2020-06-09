<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
</head>
    <body>
        <h3><a href="index.html">Home</a></h3>
        <hr>
        <h2>Meals</h2>
        <table class="table table-hover">
            <thead class="thead-dark">
                <tr>
                    <th>Дата</th>
                    <th>Описание</th>
                    <th>Калории</th>
                    <th>Превышение</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${meals}" var="meal">
                    <tr class="<c:if test="${meal.isExcess()}">table-danger</c:if>">
                        <td>${meal.getDateTime()}</td>
                        <td>${meal.getDescription()}</td>
                        <td>${meal.getCalories()}</td>
                        <td>${meal.isExcess()}</td>
                    </tr>
                </c:forEach>
            </tbody>

        </table>
    </body>
</html>