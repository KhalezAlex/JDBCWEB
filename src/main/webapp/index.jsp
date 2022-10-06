<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - HomeWork</title>
    <link rel="stylesheet" href="index.css">
</head>
<body>
<%--    <form name="countryRequest">--%>
<%--        <input type="text" name="country" id="country">--%>
<%--    </form>--%>
<%--        <button onclick="sendRequest()">request</button>--%>
<%--    <script src="index.js"></script>--%>
<%--    <p id="paragraph">Запрос</p>--%>
<div class="container">
    <div id="choiceTaskDiv" class="modal">
        <div id="taskChoice" class="taskChoice">
            <p class="parChoice" id="Task4" onclick="chooseTask4()"><b>Task4</b></p>
            <p class="parChoice" id="Task5" onclick="chooseTask5()">Task5</p>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.js"></script>
<script src="index.js"></script>
</body>
</html>