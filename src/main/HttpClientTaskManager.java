package tasks.main;

import com.google.gson.*;

import tasks.tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientTaskManager {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Gson gson = new Gson();
        Task task = new Task("aaa", "aaaaa", 0, "11.06.2000. 06:06", "1 1 1");
        Task task1 = new Task("bbb", "bbbbb", 1, "11.06.2001. 06:06", "2 2 2");

        String json = gson.toJson(task);
        String json1 = gson.toJson(task1);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(body1).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 && response1.statusCode() == 200) {
                System.out.println(response.body());
                System.out.println(response1.body());
            } else if (response.statusCode() >= 500) {
                System.out.println("something was wrong on server");
            } else if (response.statusCode() >= 400) {
                System.out.println("something was wrong, check your request");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
