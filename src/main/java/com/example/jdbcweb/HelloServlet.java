package com.example.jdbcweb;

import java.io.*;
import java.sql.SQLException;
import java.util.LinkedList;
import DB.DbNotepadAgent;
import DB.Notepad;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) {}

    private LinkedList<Notepad> valueRequest(HttpServletRequest request) {
        DbNotepadAgent agent = new DbNotepadAgent(System.getenv("url"), System.getenv("userName"),
                System.getenv("password"));
        try {
            LinkedList<Notepad> list = agent.valueRequest(request.getParameter("subtask"),
                    request.getParameter(request.getParameter("subtask")));
            agent.closeConnection();
            return list;
        } catch (SQLException e) {
            System.out.println("exception in value request");
            throw new RuntimeException(e);
        }
    }
    private LinkedList<Notepad> rangeRequest(HttpServletRequest request) {
        DbNotepadAgent agent = new DbNotepadAgent(System.getenv("url"), System.getenv("userName"),
                System.getenv("password"));
        try {
            LinkedList<Notepad> list = agent.rangeRequest(request.getParameter("subtask"),
                    request.getParameter("minPages"), request.getParameter("maxPages"));
            agent.closeConnection();
            return list;
        } catch (SQLException e) {
            System.out.println("exception in range request");
            throw new RuntimeException(e);
        }
    }

    private LinkedList<Integer> idsRequest() throws SQLException {
        DbNotepadAgent agent = new DbNotepadAgent(System.getenv("url"), System.getenv("userName"),
                System.getenv("password"));
        LinkedList<Integer> list = agent.idsRequest();
        agent.closeConnection();
        return list;
    }

    private String editRequest(HttpServletRequest request) throws SQLException {
        DbNotepadAgent agent = new DbNotepadAgent(System.getenv("url"), System.getenv("userName"),
                System.getenv("password"));
        agent.updateRequest(request);
        return "editRequest" + request.getParameter("id") + request.getParameter("brand")
                + request.getParameter("name");
    }


    private Object chooseRequest(HttpServletRequest request) throws SQLException {
        if (request.getParameter("subtask").equals("country") ||
                request.getParameter("subtask").equals("pageType") ||
                request.getParameter("subtask").equals("id"))
            return valueRequest(request);
        if (request.getParameter("subtask").equals("pageAmount"))
            return rangeRequest(request);
        if (request.getParameter("subtask").equals("edit"))
            return editRequest(request);
        else return null;
    }

    private Object chooseType(HttpServletRequest request) throws SQLException {
        if (request.getParameter("type").equals("request"))
            return chooseRequest(request);
        if (request.getParameter("type").equals("requestIds"))
            return idsRequest();
        else return null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("****** doPost is working ******");
        //НАПИСАТЬ ОТДЕЛЬНЫЙ СКРИПТ НА КАЖДЫЙ ЭЛЕМЕНТ
        PrintWriter writer = resp.getWriter();

        GsonBuilder builder = new GsonBuilder();
        Gson response = builder.create();


        try {
            writer.print(response.toJson(chooseType(req)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
}
