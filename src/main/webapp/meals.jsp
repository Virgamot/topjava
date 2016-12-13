<%--
  Created by IntelliJ IDEA.
  User: Иван
  Date: 10.12.2016
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal List</title>

    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }
        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;


        }
        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }
        .tg .tg-4eph {
            background-color: #f9f9f9
        }
    </style>

</head>
<body>

<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>


<table class="tg">
    <tr>
        <th width="60">ID</th>
        <th width="120">Дата / Время</th>
        <th width="120">Описание</th>
        <th width="80">Калории</th>
        <th width="80" colspan="2">Action</th>
    </tr>
<%--@elvariable id="meals" type="java.util.List"--%>
<c:forEach items="${meals}" var="meal" >
    <tr ${meal.exceed ? 'style="color: red"' : 'style="color: green"'}   >
    <td>${meal.getId()}</td>
    <td>${meal.getDateTime().toLocalDate()}  ${meal.getDateTime().toLocalTime()}</td>
    <td>${meal.getDescription()}</td>
    <td>${meal.getCalories()}</td>
        <td><a href="meals?action=delete&mealId=<c:out value="${meal.getId()}"/>">Delete</a></td>
        <td><a href="meals?action=edit&mealId=<c:out value="${meal.getId()}"/>">Edit</a></td>
    </tr>
</c:forEach>
</table>

<form action="meals">
    <input type="hidden" name="action" value="insert">
    <button type="submit">Add meal</button>
</form>


</body>
</html>
