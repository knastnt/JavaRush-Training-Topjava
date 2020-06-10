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
            <h3><a href="index.html">Home</a></h3>
            <h2>Meals</h2>
            <p>Максимальное число калорий в день: <b>${caloriesPerDay}</b></p>
            <div class="row">
                <table class="table table-hover col">
                    <thead class="thead-dark">
                        <tr>
                            <th>Дата</th>
                            <th>Описание</th>
                            <th>Калории</th>
                            <th>Редактирование</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${mealsTo}" var="meal">
                            <tr class="<c:if test="${meal.isExcess()}">table-danger</c:if>">
                                <td>
                                    <fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both"/>
                                    <fmt:formatDate value="${parsedDate}" type="date" pattern="dd.MM.yyyy HH:mm"/>
                                </td>
                                <td>${meal.getDescription()}</td>
                                <td>${meal.getCalories()}</td>
                                <td>
                                    <a href="${requestScope['javax.servlet.forward.request_uri']}/1" class="btn btn-primary btn-sm">Изменить</a>
                                    <form action="" method="delete" class="d-inline">
                                        <button type="button" class="btn btn-danger btn-sm">Удалить</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>