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
                <form action="" method="post" class="shadow col">
                    <div class="form-group">
                        <label for="datetime-input" class="col-form-label">Время</label>
                        <input class="form-control" type="datetime-local" placeholder="01.01.1970 00:00" id="datetime-input">
                    </div>
                    <div class="form-group">
                        <label for="description-input" class="col-form-label">Описание</label>
                        <input class="form-control" type="text" id="description-input" placeholder="Описание">
                    </div>
                    <div class="form-group">
                        <label for="calories-input" class="col-xs-2 col-form-label">Калории</label>
                        <input class="form-control" type="number" id="calories-input" placeholder="0">
                    </div>

                    <button type="submit" class="btn btn-primary mb-3">Добавить</button>
                </form>
            </div>
        </div>
    </body>
</html>