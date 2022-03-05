package tasks.manager;

import tasks.tasks.Epic;
import tasks.tasks.Subtask;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        while (true) {
            System.out.println("Что бы вы хотели сделать?\n" +
                    "1.Получить список всех задач\n" +
                    "2.Найти задачу по ID\n" +
                    "3.Создать новую задачу\n" +
                    "4.Изменить статус задачи\n" +
                    "5.Удалить задачу\n" +
                    "6.Очистить список задач\n" +
                    "7.Выход");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    while (true) {
                        System.out.println("Доступные виды задач:\n" +
                                "1.Задача\n" +
                                "2.Эпик\n" +
                                "3.Подзадача");
                        input = scanner.nextLine();
                        if (input.equals("1")) {
                            System.out.println(inMemoryTaskManager.returnTaskList());
                            break;
                        } else if (input.equals("2")) {
                            System.out.println(inMemoryTaskManager.returnEpicList());
                            break;
                        } else if (input.equals("3")) {
                            System.out.println("введите номер Эпика для которого хотите посмотреть список подзадач");
                            if (scanner.hasNextInt()) {
                                int id = Integer.parseInt(scanner.nextLine());
                                if (inMemoryTaskManager.getEpicList().containsKey(id)) {
                                    System.out.println(inMemoryTaskManager.returnSubTaskList(id));
                                } else System.out.println("нет такой подзадачи");
                            } else System.out.println("ошибочка");
                            break;
                        } else System.out.println("нет такой команды(");
                    }
                    break;
                case "2":
                    System.out.println("Введите номер задачи которую хотите найти");
                    if (scanner.hasNextInt()) {
                        int id = Integer.parseInt(scanner.nextLine());
                        if (inMemoryTaskManager.getTaskList().containsKey(id)) {
                            System.out.println(inMemoryTaskManager.findTask(id).toString());
                            break;
                        }
                        if (inMemoryTaskManager.getEpicList().containsKey(id)) {
                            System.out.println(inMemoryTaskManager.findEpic(id).toString());
                            break;
                        } else System.out.println("нет такой задачи(");
                        break;
                    } else System.out.println("ошибочка");
                    break;
                case "3":
                    while (true) {
                        System.out.println("Какую задачу вы хотите добавить?\n" +
                                "1.Задача\n" +
                                "2.Эпик");
                        input = scanner.nextLine();
                        if (input.equals("1")) {
                            System.out.println("Введите название");
                            String name = scanner.nextLine();
                            System.out.println("Теперь описание");
                            String description = scanner.nextLine();
                            inMemoryTaskManager.addNewTask(inMemoryTaskManager.createNewTask(name, description));
                            break;
                        } else if (input.equals("2")) {
                            System.out.println("Введите название");
                            String name = scanner.nextLine();
                            Epic epic = inMemoryTaskManager.createNewEpic(name);
                            inMemoryTaskManager.addNewEpic(epic);
                            System.out.println("теперь добавьте подзадачи\n" +
                                    "Сколько подзадач будет?");
                            if (scanner.hasNextInt()) {
                                int count = Integer.parseInt(scanner.nextLine());
                                HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();
                                for (int i = 0; i < count; i++) {
                                    System.out.println("Введите название");
                                    String nameSubTask = scanner.nextLine();
                                    System.out.println("Теперь описание");
                                    String descriptionSubTask = scanner.nextLine();
                                    subtaskHashMap.put(i, inMemoryTaskManager.createNewSubTask(nameSubTask, descriptionSubTask, inMemoryTaskManager.getCounter() - 1));
                                }
                                epic.setSubTaskList(subtaskHashMap);
                                break;
                            } else System.out.println("ошибочка");
                            break;
                        } else System.out.println("Нет такой задачи");
                    }
                    break;
                case "4":
                    System.out.println("Введите номер задачи в которой нужно изменить статус");
                    if (scanner.hasNextInt()) {
                        int taskNumber = Integer.parseInt(scanner.nextLine());
                        if (inMemoryTaskManager.getEpicList().containsKey(taskNumber)) {
                            System.out.println("Вот список подзадач этого эпика\n" +
                                    "выберите подзадачу");
                            System.out.println(inMemoryTaskManager.getEpicList().get(taskNumber).getSubTaskList());
                            if (scanner.hasNextInt()) {
                                int subTaskNumber = Integer.parseInt(scanner.nextLine());
                                System.out.println("укажите статус задачи\n" +
                                        "1 - новая\n" +
                                        "2 - в процессе\n" +
                                        "3 - выполнена");
                                if (scanner.hasNextInt()) {
                                    int status = Integer.parseInt(scanner.nextLine());
                                    inMemoryTaskManager.getEpicList().get(taskNumber).getSubTaskList().get(subTaskNumber).setStatus(status);
                                    inMemoryTaskManager.changeStatus(taskNumber);
                                } else System.out.println("ошибочка");
                                break;
                            } else System.out.println("ошибочка");
                        } else if (inMemoryTaskManager.getTaskList().containsKey(taskNumber)) {
                            System.out.println("укажите статус задачи\n" +
                                    "1 - новая\n" +
                                    "2 - в процессе\n" +
                                    "3 - выполнена");
                            if (scanner.hasNextInt()) {
                                int status = Integer.parseInt(scanner.nextLine());
                                inMemoryTaskManager.updateTask(taskNumber, status);
                            } else System.out.println("ошибочка");
                        } else System.out.println("Нет такой задачи");
                    } else System.out.println("ошибочка");
                    break;
                case "5":
                    System.out.println("Введите номер задачи которую вы хотели бы удалить");
                    if (scanner.hasNextInt()) {
                        int taskNumber = Integer.parseInt(scanner.nextLine());
                        inMemoryTaskManager.deleteTask(taskNumber);
                    }
                    System.out.println("ошибочка");
                    break;
                case "6":
                    System.out.println("Вы уверены?\n" +
                            "если всё же уверены то нажмите '0'");
                    String input0 = scanner.nextLine();
                    if (input0.equals("0")) {
                        inMemoryTaskManager.deleteAllTasks();
                    }
                    break;
                case "7":
                    System.exit(7);
                default:
                    System.out.println("нет такой команды");
                    break;
            }
        }
    }
}
