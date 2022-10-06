package DB;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;

public class DbNotepadAgent {
    private String url; /*= "jdbc:mysql://localhost:3306/notepads";*/
    private String name; /* = "root";*/
    private String password; /* = "ItsMyLife0203";*/
    private Connection connection;
    LinkedList<Notepad> list;

    public DbNotepadAgent(String url, String name, String password) {
        this.url = url;
        this.name = name;
        this.password = password;
        try {
            this.connection = DriverManager.getConnection(url, name, password);
            System.out.println("connection established");
        } catch (SQLException e) {
            System.out.println("connection failed");
            throw new RuntimeException(e);
        }
    }
    public LinkedList<Notepad> notepadsList() {
        list = new LinkedList<>();
        String query = "SELECT * FROM notebooks";
        Statement statement;
        try {
            statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(query);
//            int colsAmount = result.getMetaData().getColumnCount();  ПРОВЕРИТЬ ВТОРОЙ ВАРИАНТ
            while (result.next()) {
                int id = result.getInt("id");
                String brand = result.getString("brand");
                String name = result.getString("name");
                int pageAmount = result.getInt("pageAmount");
                String cover = result.getString("cover");
                String country = result.getString("country");
                String pageType = result.getString("pageType");
//                Notepad notepad = new Notepad(brand, name, pageAmount, cover, country, pageType); ВТОРОЙ ВАРИАНТ
//                notepad.setId(id);
                list.add(new Notepad(id, brand, name, pageAmount, cover, country, pageType));
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public void printList() {
        for (Notepad notepad: list) {
            System.out.println(notepad);
        }
    }
    public void closeConnection() {
        try {
            System.out.println("connection closed");
            this.connection.close();
        } catch (SQLException e) {
            System.out.println("connection close failed");
            throw new RuntimeException(e);
        }
    }


    public void showCountries() {
        for (Notepad notepad: list) {
            System.out.println(notepad.getCountry());
        }
    }
    public void task_2_Last() throws SQLException {
        String query = "SELECT country, COUNT(country) AS Amount FROM notebooks group by country";
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
            System.out.println(resultSet.getString("country") + " - " +
                    resultSet.getInt("Amount"));
    }
    public void task_3_1_2(String minMax) throws SQLException {
        String queryMax = "WITH temp AS (SELECT country, COUNT(country) AS Amount FROM notebooks group by country) " +
                "SELECT country FROM temp WHERE Amount = (SELECT MAX(Amount) FROM temp)";
        String queryMin = "WITH temp AS (SELECT country, COUNT(country) AS Amount FROM notebooks group by country) " +
                "SELECT country FROM temp WHERE Amount = (SELECT MIN(Amount) FROM temp)";
        Statement statement = this.connection.createStatement();
        ResultSet resultSet;
        if (minMax.equals("max")) {
            resultSet = statement.executeQuery(queryMax);
            while (resultSet.next())
                System.out.println(resultSet.getString("country"));
        } else if (minMax.equals("min")) {
            resultSet = statement.executeQuery(queryMin);
            while (resultSet.next())
                System.out.println(resultSet.getString("country"));
        }
    }
    public void task_3_3_4(String minMax) throws SQLException {
        String queryMax = "WITH temp AS (SELECT brand, COUNT(brand) AS Amount FROM notebooks group by brand) " +
                "SELECT brand FROM temp WHERE Amount = (SELECT MAX(Amount) FROM temp)";
        String queryMin = "WITH temp AS (SELECT brand, COUNT(brand) AS Amount FROM notebooks group by brand) " +
                "SELECT brand FROM temp WHERE Amount = (SELECT MIN(Amount) FROM temp)";
        Statement statement = this.connection.createStatement();
        ResultSet resultSet;

        if (minMax.equals("max")) {
            resultSet = statement.executeQuery(queryMax);
            while (resultSet.next())
                System.out.println(resultSet.getString("brand"));
        } else if (minMax.equals("min")) {
            resultSet = statement.executeQuery(queryMin);
            while (resultSet.next())
                System.out.println(resultSet.getString("brand"));
        }
    }
    public void task_3_5_6(boolean isSoft) throws SQLException {
        String querySoft = "SELECT brand, name FROM notebooks WHERE cover = \"soft\"";
        String queryHard = "SELECT brand, name FROM notebooks WHERE cover = \"hard\"";
        Statement statement = this.connection.createStatement();
        ResultSet resultSet;

        if (isSoft) {
            resultSet = statement.executeQuery(querySoft);
        } else {
            resultSet = statement.executeQuery(queryHard);
        }
        while (resultSet.next())
            System.out.println(resultSet.getString("brand") + " - " +
                    resultSet.getString("name"));
    }


    private LinkedList<Notepad> getNotepads(String query) throws SQLException {
        LinkedList<Notepad> list = new LinkedList<>();
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
            list.add(new Notepad(resultSet.getInt("id"), resultSet.getString("brand"),
                    resultSet.getString("name"), resultSet.getInt("pageAmount"),
                    resultSet.getString("cover"), resultSet.getString("country"),
                    resultSet.getString("pageType")));
        return list;
    }
    public LinkedList<Notepad> valueRequest(String subtask, String value) throws SQLException {
        String query = "SELECT id, brand, name, pageAmount, cover, country, pageType FROM notebooks WHERE " +
                subtask + " = \"" + value + ("\"");
        return getNotepads(query);
    }
    public LinkedList<Notepad> rangeRequest(String subtask, String min, String max) throws SQLException {
        String query = "SELECT id, brand, name, pageAmount, cover, country, pageType FROM notebooks WHERE " +
                "(" + subtask + " > " + min + " AND " + subtask + " < " + max + ")";
        return getNotepads(query);
    }
    public LinkedList<Integer> idsRequest() throws SQLException {
        String query = "SELECT id FROM notebooks";
        LinkedList<Integer> list = new LinkedList<>();
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
            list.add(resultSet.getInt("id"));
        return list;
    }




    private String getInsertQuery(Notepad notepad) {
        String query = "INSERT INTO notebooks (brand, name, pageAmount, cover, country, pageType) VALUES (\"";
        query = query.concat(notepad.getBrand() + "\", \"");
        query = query.concat(notepad.getName() + "\", ");
        query = query.concat(notepad.getPageAmount() + ", \"");
        query = query.concat(notepad.getCover() + "\", \"");
        query = query.concat(notepad.getCountry() + "\", \"");
        query = query.concat(notepad.getPageType() + "\");");
        return query;
    }


    public void task_5_1(Notepad notepad) throws SQLException {
        String query = getInsertQuery(notepad);
        Statement statement = this.connection.createStatement();
        statement.executeUpdate(query);
    }


    public void task_5_2(int id) throws SQLException {
        String query = "DELETE FROM notebooks WHERE id = ".concat(Integer.toString(id));
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }



    private String pageAmountUpdate(String str, HttpServletRequest request) {
        String temp = request.getParameter("pageAmount");
        for (int i = 0; i < temp.length(); i++)
            if (!Character.isDigit(temp.charAt(i))) temp = "";
        if (!temp.equals(""))
            str = str.concat( "pageAmount = " + temp + ", ");
        return str;
    }

    private String strUpdate(String str, String field, String parameter) {
            str = str.concat(field + " = \"" + parameter + "\", ");
        return str;
    }

    private String updateQuery(HttpServletRequest request) {
        String str = "";
        String[] help = {"brand", "name", "cover", "country", "pageType"};

        for (String hlp: help)
            str = strUpdate(str, hlp, request.getParameter(hlp));
        str = pageAmountUpdate(str, request);
        str = str.substring(0, str.length() - 2);
        System.out.println(str);
        return str;
    }


    public void updateRequest(HttpServletRequest request) throws SQLException {
        String query = "UPDATE notebooks SET " + updateQuery(request) + " WHERE id = "
                + request.getParameter("id");
        System.out.println(query);
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }
}