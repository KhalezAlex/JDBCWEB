let taskChoice = 4;
let subtask = "country";
subtaskSelectionDraw(taskChoice);
drawValueRequestForm(subtask);


function sendCountryRequest(subtask) {
    $.ajax({
        url: 'http://localhost:8080/JDBCWEB_war_exploded/hello-servlet',
        method: 'post',
        dataType:'html',
        data: {
            type: 'request',
            subtask: subtask,
            country: document.forms.requestForm.country.value
        },
        success: function (data) {
            let jsonObject = JSON.parse(data);
            console.log(jsonObject);
        }
    });
}
function sendPageTypeRequest(subtask) {
    $.ajax({
        url: 'http://localhost:8080/JDBCWEB_war_exploded/hello-servlet',
        method: 'post',
        dataType:'html',
        data: {
            type: 'request',
            subtask: subtask,
            pageType: document.forms.requestForm.pageType.value
        },
        success: function (data) {
            let jsonObject = JSON.parse(data);
            console.log(jsonObject);
        }
    });
}
function sendPageAmountRequest(subtask) {
    $.ajax({
        url: 'http://localhost:8080/JDBCWEB_war_exploded/hello-servlet',
        method: 'post',
        dataType:'html',
        data: {
            type: 'request',
            subtask: subtask,
            minPages: document.forms.requestForm.minPages.value,
            maxPages: document.forms.requestForm.maxPages.value
        },
        success: function (data) {
            let jsonObject = JSON.parse(data);
            console.log(jsonObject);
        }
    });
}
function sendInsertRequest(subtask) {}


function sendEditRequest(subtask) {
    $.ajax({
        url: 'http://localhost:8080/JDBCWEB_war_exploded/hello-servlet',
        method: 'post',
        dataType:'html',
        data: {
            type: 'request',
            subtask: subtask,
            id : document.forms.requestForm.selectId.value,
            brand: document.forms.requestForm.brand.value,
            name: document.forms.requestForm.name.value,
            pageAmount: document.forms.requestForm.pageAmount.value,
            cover: document.forms.requestForm.cover.value,
            country: document.forms.requestForm.country.value,
            pageType: document.forms.requestForm.pageType.value,
        },
        success: function (data) {
            let jsonObject = JSON.parse(data);
            console.log(jsonObject);
        }
    });
}


function sendDeleteRequest(subtask) {}

function sendRequest(subtask) {
    if (subtask === "country") sendCountryRequest(subtask);
    if (subtask === "pageType") sendPageTypeRequest(subtask);
    if (subtask === "pageAmount") sendPageAmountRequest(subtask);
    if (subtask === "insert") sendInsertRequest(subtask);
    if (subtask === "edit") sendEditRequest(subtask);
    if (subtask === "delete") sendDeleteRequest(subtask);
}


function subtaskSelectionDraw(task) {
    if (document.getElementById("choiceTaskDiv").lastChild.id === "requestForm")
        document.getElementById("choiceTaskDiv").lastChild.remove();

    let subTaskChoice = document.createElement("div");
    subTaskChoice.setAttribute("class", "requestForm");
    subTaskChoice.id = "requestForm";
    let selectTask = document.createElement("select");
    selectTask.id = "selectTask";
    selectTask.setAttribute("class", "requestInput");
    document.getElementById("choiceTaskDiv").appendChild(subTaskChoice);
    selectTask.addEventListener("change", function () {
        selectListener(this.value)
    });
    let opt1 = document.createElement("option");
    let opt2 = document.createElement("option");
    let opt3 = document.createElement("option");
    if (task === 4) {
        opt1.innerHTML = "country";
        opt2.innerHTML = "pageType";
        opt3.innerHTML = "pageAmount";
    } else {
        opt1.innerHTML = "insert";
        opt2.innerHTML = "edit";
        opt3.innerHTML = "delete";
    }
    selectTask.appendChild(opt1);
    selectTask.appendChild(opt2);
    selectTask.appendChild(opt3);
    subTaskChoice.appendChild(selectTask);
}
function getInput(value) {
    let input = document.createElement("input");
    input.type = "text";
    input.name = value;
    input.setAttribute("class", "requestInput");
    input.placeholder = value;
    return input;
}
function getButton() {
    let button = document.createElement("button");
    button.setAttribute("class", "requestButton");
    button.innerHTML = "request";
    button.setAttribute("onclick", "sendRequest(\"" + subtask.toString() + "\")")
    // button.innerHTML = "<script src=\"buttonScript.js\"></script>";
    return button;
}

function selectSelectListener(id) {
    console.log(id);
    $.ajax({
        url: 'http://localhost:8080/JDBCWEB_war_exploded/hello-servlet',
        method: 'post',
        dataType:'html',
        data: {
            type: 'request',
            subtask: 'id',
            id: id,
            brand: document.forms.requestForm.brand.value,
            name: document.forms.requestForm.name.value,
            pageAmount: document.forms.requestForm.pageAmount.value,
            cover: document.forms.requestForm.cover.value,
            country: document.forms.requestForm.country.value,
            pageType: document.forms.requestForm.pageType.value,
        },
        success: function (data) {
            let jsonObject = JSON.parse(data);
            document.forms.requestForm.brand.value = jsonObject[0].brand;
            document.forms.requestForm.name.value = jsonObject[0].name;
            document.forms.requestForm.pageAmount.value = jsonObject[0].pageAmount;
            document.forms.requestForm.cover.value = jsonObject[0].cover;
            document.forms.requestForm.country.value = jsonObject[0].country;
            document.forms.requestForm.pageType.value = jsonObject[0].pageType;
        }
    });
}
function drawIdOptions() {
    $.ajax({
        url: 'http://localhost:8080/JDBCWEB_war_exploded/hello-servlet',         /* Куда пойдет запрос */
        method: 'post',             /* Метод передачи (post или get) */
        dataType: 'html',          /* Тип данных в ответе (xml, json, script, html). */
        data: {
            type: 'requestIds',
            // subtask: 'requestIds'
        },     /* Параметры передаваемые в запросе. */
        success: function(data){   /* функция которая будет выполнена после успешного запроса.  */
            let jsonObject = JSON.parse(data);
            console.log(jsonObject);
            addOptions(jsonObject);/* В переменной data содержится ответ от index.php. */
        }
    });
}
function getOption(id) {
    let option = document.createElement("option");
    option.innerHTML = id;
    return option;
}
function addOptions(ids) {
    for (let i = 0; i < ids.length; i ++) {
        document.getElementById("selectId").appendChild(getOption(ids[i]));
    }
}
function getSelectId() {
    let select = document.createElement("select");
    select.setAttribute("class", "requestInput");
    select.id = "selectId";
    select.addEventListener("change", function () {
        selectSelectListener(this.value)
    });
    return select;
}

function drawValueRequestForm(subtask) {
    let div = document.createElement("div");
    div.id = "requestDiv";
    div.setAttribute("class", "requestForm");
    let form = document.createElement("form");
    form.id = "requestForm";
    form.setAttribute("class", "requestForm");
    let input = getInput(subtask);
    let button = getButton();
    form.appendChild(input);
    div.appendChild(form);
    div.appendChild(button);
    document.getElementById("choiceTaskDiv").appendChild(div);
}
function drawPageAmountRequestForm() {
    let div = document.createElement("div");
    div.id = "requestDiv";
    div.setAttribute("class", "requestForm");
    let form = document.createElement("form");
    form.id = "requestForm";
    form.setAttribute("class", "requestForm");
    let minPagesInput = getInput("minPages");
    let maxPagesInput = getInput("maxPages");
    let button = getButton();
    form.appendChild(minPagesInput);
    form.appendChild(maxPagesInput);
    div.appendChild(form);
    div.appendChild(button);
    document.getElementById("choiceTaskDiv").appendChild(div);
}
function drawInsertEditRequestForm() {
    let div = document.createElement("div");
    div.id = "requestDiv";
    div.setAttribute("class", "requestForm");
    let form = document.createElement("form");
    form.id = "requestForm";
    form.setAttribute("class", "requestForm");
    let brand = getInput("brand");
    let name = getInput("name");
    let pageAmount = getInput("pageAmount");
    let cover = getInput("cover")
    let country = getInput("country");
    let pageType = getInput("pageType")
    if (subtask === "edit") {
        let id = getSelectId();
        form.appendChild(id);
        drawIdOptions();
    }
    form.appendChild(brand);
    form.appendChild(name);
    form.appendChild(pageAmount);
    form.appendChild(cover);
    form.appendChild(country);
    form.appendChild(pageType);
    div.appendChild(form);
    let button = getButton()
    div.appendChild(button);
    document.getElementById("choiceTaskDiv").appendChild(div);
    if (subtask === "edit")
        selectSelectListener(1);
}

function selectListener(value) {
    if (document.getElementById("choiceTaskDiv").lastChild.id === "requestDiv")
        document.getElementById("choiceTaskDiv").lastChild.remove();
    subtask = value;
    if (value === "country") drawValueRequestForm(subtask);
    if (value === "pageType") drawValueRequestForm(subtask);
    if (value === "pageAmount") drawPageAmountRequestForm();
    if (value === "insert") drawInsertEditRequestForm();
    if (value === "edit") drawInsertEditRequestForm();
    if (value === "delete") drawValueRequestForm("id");
}
function chooseTask4() {
    taskChoice = 4;
    if (document.getElementById("choiceTaskDiv").lastChild.id === "requestDiv")
        document.getElementById("choiceTaskDiv").lastChild.remove();
    document.getElementById("Task4").innerHTML = "<b>Task4</b>";
    document.getElementById("Task5").innerHTML = "Task5";
    subtaskSelectionDraw(taskChoice);
    subtask = "country";
    drawValueRequestForm(subtask);
}
function chooseTask5() {
    taskChoice = 5
    if (document.getElementById("choiceTaskDiv").lastChild.id === "requestDiv")
        document.getElementById("choiceTaskDiv").lastChild.remove();
    document.getElementById("Task4").innerHTML = "Task4";
    document.getElementById("Task5").innerHTML = "<b>Task5</b>";
    subtaskSelectionDraw(taskChoice);
    subtask = "insert";
    drawInsertEditRequestForm(subtask);
}