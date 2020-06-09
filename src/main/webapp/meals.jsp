<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
    <body>
        <h3><a href="index.html">Home</a></h3>
        <hr>
        <h2>Meals</h2>
        <table>
            <thead>
                <tr>
                    <th>jjjj</th>
                    <th>Тяжесть</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${meals}" var="meal">
                    <tr>
                        <td>${meal.getDescription()}</td>
                        <td>2расш</td>
                    </tr>
                </c:forEach>
            </tbody>

        </table>
    </body>
</html>