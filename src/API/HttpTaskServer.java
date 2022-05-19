package tasks.API;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import tasks.manager.HttpTaskManager;
import tasks.manager.Managers;
import tasks.manager.TaskManager;
import tasks.tasks.Epic;
import tasks.tasks.Subtask;
import tasks.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;


public class HttpTaskServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        HttpServer httpServer = null;
        try {
            httpServer = HttpServer.create();
            new KVServer().start();
            httpServer.bind(new InetSocketAddress(PORT), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        //   httpServer.stop(1);
    }

    static class TaskHandler implements HttpHandler {

        TaskManager manager = Managers.getDefaultHttpTaskManager("http://localhost:8082/");

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String method = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String response = "";
            int id;
            Gson gson = new Gson();

            switch (method) {
                case "GET":
                    if (path.contains("find")) {
                        id = Integer.parseInt(httpExchange.getRequestURI().getQuery().substring(3));
                        response = gson.toJson(manager.getEpicList().get(id).getSubTaskList());
                    } else if (path.contains("find")) {
                        id = Integer.parseInt(httpExchange.getRequestURI().getQuery().substring(3));
                        response = manager.findTask(id).toString();
                    } else if (path.split("/")[2].equals("history")) {
                        response = manager.history().toString();
                    } else if (path.split("/")[2].equals("getPrioritizedTasks")) {
                        response = manager.getPrioritizedTasks().toString();
                    } else if (path.split("/")[2].equals("task")) {
                        response = manager.getTaskList().toString();
                    } else if (path.split("/")[2].equals("epic")) {
                        response = manager.getEpicList().toString();
                    } else if (path.split("/")[2].equals("load")) {
                        manager.load();
                        response = "download";
                    } else httpExchange.sendResponseHeaders(404, 0);
                    break;
                case "POST":

                    InputStream inputStream = httpExchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

                    if (path.split("/")[2].equals("task")) {
                        Task task = gson.fromJson(body, Task.class);
                        manager.addNewTask(task);
                        manager.save();
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write("Задача добавлена.".getBytes());
                        }
                    } else if (path.split("/")[2].equals("subtask")) {
                        Subtask subtask = gson.fromJson(body, Subtask.class);
                        manager.addNewTask(subtask);
                        manager.save();
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write("Задача добавлена.".getBytes());
                        }
                    } else if (path.split("/")[2].equals("epic")) {
                        Epic epic = gson.fromJson(body, Epic.class);
                        manager.addNewEpic(epic);
                        manager.save();
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write("Задача добавлена.".getBytes());
                        }
                    }
                    break;
                case "DELETE":
                    if (path.equals("/tasks/task")) {
                        manager.deleteAllTasks();
                        manager.save();
                    } else if (path.contains("/tasks/find")) {
                        id = Integer.parseInt(httpExchange.getRequestURI().getQuery().substring(3));
                        manager.deleteTask(id);
                        manager.save();
                    }
                    break;
            }
            httpExchange.sendResponseHeaders(200, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}