Task manager

Tracker where you can enter your tasks and track their completion.

![Untitled_25_1639469823](https://user-images.githubusercontent.com/94691893/200191483-0e3bfed1-8d64-4786-ac9f-a01b679c6a86.png)

Types of tasks
The simplest building block of such a system is the task. The task has the following properties:
* A name that briefly describes the essence of the task (for example, "Moving").
* A description that reveals the details.
* The unique identification number of the task by which it can be found.
* Status showing her progress. We will highlight the following stages of the task's life:
- - NEW — the task has just been created, but it has not yet been started.
- - IN_PROGRESS — the task is being worked on.
- - DONE — the task is completed.
Sometimes, to perform some large-scale task, it is better to break it into subtasks. A large task, which is divided into subtasks, we will call an epic.
Thus, there can be three types of tasks in our system: regular tasks, epics and subtasks. The following conditions must be met for them:
* For each subtask, it is known within which epic it is performed.
* Every epic knows what subtasks it includes.
* The completion of all epic subtasks is considered the completion of the epic.
Manager
The manager interface includes the following functions:
* Ability to store tasks of all types. To do this, you need to choose a suitable collection.
* Methods for each of the task types(Task/Epic/Subtask):
- -  Getting a list of all tasks.
- - Delete all tasks.
- - Getting by ID.
- -  Creation. The object itself must be passed as a parameter.
- - Update. The new version of the object with the correct identifier is passed as a parameter.
- -  Deletion by ID.
* Additional methods:
-  Getting a list of all subtasks of a certain epic.
* Statuses are managed according to the following rule:
- - The manager does not choose the status for the task himself. Information about it comes to the manager along with information about the task itself. According to these data, in some cases it will retain the status, in others it will count.
* For epics:
- - if the epic has no subtasks or all of them have the status NEW, then the status should be NEW.
- -  if all subtasks have the status DONE, then the epic is considered completed — with the status DONE.
- -  in all other cases, the status should be IN_PROGRESS.
* You can view the history of viewing tasks.
