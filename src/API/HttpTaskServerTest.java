package tasks.API;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import tasks.tasks.Epic;
import tasks.tasks.Subtask;
import tasks.tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    @Test
    public void main() {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/");
        URI url1 = URI.create("http://localhost:8081/tasks/find/?id=0");
        URI url2 = URI.create("http://localhost:8081/tasks/find/?id=2");
        URI url8 = URI.create("http://localhost:8081/tasks/find/?id=3");
        URI url3 = URI.create("http://localhost:8081/tasks/epic");
        URI url4 = URI.create("http://localhost:8081/tasks/subtask");
        URI url5 = URI.create("http://localhost:8081/tasks/history");
        URI url6 = URI.create("http://localhost:8081/tasks/getPrioritizedTasks");
        URI url7 = URI.create("http://localhost:8081/tasks/load");
        Gson gson = new Gson();
        Task task = new Task("aaa", "aaaaa", 0, "11.06.2000. 06:06", "1 1 1");
        Task task1 = new Task("bbb", "bbbbb", 1, "11.06.2001. 06:06", "2 2 2");
        Epic epic = new Epic("asd", 2, "dsa");
        Subtask subtask = new Subtask("sda", "sda", 2, 3, "15.05.2003. 03:03", "3 3 3");
        String json = gson.toJson(task);
        String json1 = gson.toJson(task1);
        String jsonEpic = gson.toJson(epic);
        String jsonSubtask = gson.toJson(subtask);

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpRequest requestGet = HttpRequest.newBuilder().uri(url).GET().build();
        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(body1).build();
        HttpRequest.BodyPublisher bodyEpic = HttpRequest.BodyPublishers.ofString(jsonEpic);
        HttpRequest requestEpic = HttpRequest.newBuilder().uri(url3).POST(bodyEpic).build();
        HttpRequest requestEpicGet = HttpRequest.newBuilder().uri(url).GET().build();
        HttpRequest.BodyPublisher bodySubtask = HttpRequest.BodyPublishers.ofString(jsonSubtask);
        HttpRequest requestSubtask = HttpRequest.newBuilder().uri(url4).POST(bodySubtask).build();
        HttpRequest requestSubtaskGet = HttpRequest.newBuilder().uri(url).GET().build();
        HttpRequest requestFindTask = HttpRequest.newBuilder().uri(url1).GET().build();
        HttpRequest requestFindEpic = HttpRequest.newBuilder().uri(url2).GET().build();
        HttpRequest requestFindSubtask = HttpRequest.newBuilder().uri(url8).GET().build();
        HttpRequest requestHistory = HttpRequest.newBuilder().uri(url5).GET().build();
        HttpRequest requestGetPrioritizedTasks = HttpRequest.newBuilder().uri(url6).GET().build();
        HttpRequest requestDeleteTask = HttpRequest.newBuilder().uri(url1).DELETE().build();
        HttpRequest requestDeleteAll = HttpRequest.newBuilder().uri(url1).DELETE().build();
        HttpRequest requestLoad = HttpRequest.newBuilder().uri(url5).GET().build();


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseEpic = client.send(requestEpic, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseSubtask = client.send(requestSubtask, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseGet = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseEpicGet = client.send(requestEpicGet, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseSubtaskGet = client.send(requestSubtaskGet, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseFindTask = client.send(requestFindTask, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseFindEpic = client.send(requestFindEpic, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseFindSubtask = client.send(requestFindSubtask, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseHistory = client.send(requestHistory, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseGetPrioritizedTasks = client.send(requestGetPrioritizedTasks, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseDeleteTask = client.send(requestDeleteTask, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseDeleteAll = client.send(requestDeleteAll, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseLoad = client.send(requestLoad, HttpResponse.BodyHandlers.ofString());
            assertAll(
                    () -> assertEquals(response.statusCode(), 200),
                    () -> assertEquals(response1.statusCode(), 200),
                    () -> assertEquals(responseEpic.statusCode(), 200),
                    () -> assertEquals(responseSubtask.statusCode(), 200),
                    () -> assertEquals(responseGet.statusCode(), 200),
                    () -> assertEquals(responseEpicGet.statusCode(), 200),
                    () -> assertEquals(responseSubtaskGet.statusCode(), 200),
                    () -> assertEquals(responseFindTask.statusCode(), 200),
                    () -> assertEquals(responseFindEpic.statusCode(), 200),
                    () -> assertEquals(responseFindSubtask.statusCode(), 200),
                    () -> assertEquals(responseHistory.statusCode(), 200),
                    () -> assertEquals(responseGetPrioritizedTasks.statusCode(), 200),
                    () -> assertEquals(responseDeleteTask.statusCode(), 200),
                    () -> assertEquals(responseDeleteAll.statusCode(), 200),
                    () -> assertEquals(responseLoad.statusCode(), 200)
            );

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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
