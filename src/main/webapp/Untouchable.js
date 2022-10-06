async function sendRequest() {
    console.log("кнопка нажата, отправляем запрос " + document.forms.countryRequest.country.value);
    let response = await fetch('http://localhost:8080/JDBCWEB_war_exploded/hello-servlet', {
        method: 'post',
        headers: {'Content-Type': 'text/html;charset=utf-8'},
        body: document.forms.countryRequest.country.value
    });
    let result = await response.text();
    document.getElementById("paragraph").innerHTML = result;
}


// async function sendRequest() {
//     let resp;
//     fetch('http://localhost:8080/JDBCWEB_war_exploded/hello-servlet?country=' + document.forms.countryRequest.country.value).
//         then((response) => response.json()).
//             then((data) => resp = data.answer).
//                 then((resp) => document.getElementById("paragraph").innerHTML = resp);
// }

// protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//     System.out.println("dopost отработал");
//     StringBuffer country = new StringBuffer();
//     String line;
//     PrintWriter out = resp.getWriter();
//
//     BufferedReader br = req.getReader();
//     while ((line = br.readLine()) != null)
//         country.append(line);
//     System.out.println(country);
//
//     DbNotepadAgent agent = new DbNotepadAgent(System.getenv("url"), System.getenv("userName"),
//         System.getenv("password"));
//     try {
//         String respString = agent.task_4_1(country.toString()) ;
//         System.out.println(respString);
//         out.print(respString);
//     } catch (SQLException e) {
//         System.out.println("exception in 4_1");
//         throw new RuntimeException(e);
//     }
//     agent.closeConnection();
// }