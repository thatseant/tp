package seedu.duke.commands.task;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seedu.duke.DukeExceptions;
import seedu.duke.project.Project;
import seedu.duke.task.Task;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskDoneCommandTest {
    static ArrayList<Project> projects;

    @BeforeAll
    static void createProjectsTasks() {
        projects = new ArrayList<>();
        Project projectOne = new Project("Drink Water");
        projects.add(projectOne);

        Project projectTwo = new Project("Fire");
        Task taskOne = new Task("test1");
        Task taskTwo = new Task("test2");
        projectTwo.addTask(taskOne);
        projectTwo.addTask(taskTwo);
        projects.add(projectTwo);

        Project projectThree = new Project("CS2113 Tutorial");
        projects.add(projectThree);
    }

    @Test
    void executeCommand_validIndex_addActualTime() throws DukeExceptions {
        HashMap<String, String> params = new HashMap<>();
        params.put("t", "2");
        TaskDoneCommand newCommand = new TaskDoneCommand(params, 1);
        String actualOutput = newCommand.executeCommand(projects, new ArrayList<>());
        String expectedOutput = "Task \"test2\" is done!";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void executeCommand_invalidTaskIndex_invalidIdException() throws DukeExceptions {
        HashMap<String, String> params = new HashMap<>();
        params.put("t", "5");
        TaskDoneCommand newCommand = new TaskDoneCommand(params, 1);
        Throwable actualOutputException = assertThrows(DukeExceptions.class,
            () -> newCommand.executeCommand(projects, new ArrayList<>()));
        String expectedOutput = "Task ID does not exist!";
        assertEquals(expectedOutput, actualOutputException.toString());
    }

}