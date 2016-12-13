<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Иван
  Date: 12.12.2016
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>Edit meal</title>
</head>
<body>

<form method="POST" action='meals' name="frmAddMeal">

    Meal ID : <input type="text" disabled="true" name="mealId"
                     value="<c:out value="${meal.getId()}" />" /> <br />

    DATE ("HH:mm uuuu-MM-dd") : <input
        type="datetime" name="dob"
        value="${meal.getTime()} ${meal.getDate()}" /> <br />

    Description : <input type="text" name="mealdescription"
                         value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br />


    <input type="submit" value="Отправить" />

</form>

</body>
</html>
