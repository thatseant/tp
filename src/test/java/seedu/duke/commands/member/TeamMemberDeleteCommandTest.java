package seedu.duke.commands.member;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.DukeExceptions;
import seedu.duke.commands.PrintHomeViewCommand;
import seedu.duke.commands.task.TaskListCommand;
import seedu.duke.member.TeamMember;
import seedu.duke.project.Project;
import seedu.duke.task.Task;
import seedu.duke.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TeamMemberDeleteCommandTest {
    private ArrayList<Project> projects;
    private ArrayList<TeamMember> teamMembers;
    private Ui ui = new Ui();
    private HashMap<String, String> params;

    @BeforeEach
    void createTeamMembersList() throws DukeExceptions {
        projects = new ArrayList<>();
        teamMembers = new ArrayList<>();
        Project project1 = new Project("Project One");
        TeamMember member1 = new TeamMember("John Doe");
        TeamMember member2 = new TeamMember("Sarah Hopkins");
        projects.add(project1);
        teamMembers.add(member1);
        teamMembers.add(member2);
        project1.addTeamMemberToProject(member1);
        project1.addTeamMemberToProject(member2);
        member1.assignProject(project1);
        member2.assignProject(project1);
        Task task1 = new Task("Task One");
        project1.addTask(task1);
        task1.setMember(member1);
        task1.setMember(member2);
        member1.setTask(task1);
        member2.setTask(task1);
        params = new HashMap<>();
    }

    @Test
    void executeCommand_validMemberIdInProjectView_memberRemovedMessage() throws DukeExceptions {
        int projectIndex = 0;
        params.put("m", "2");
        TeamMemberDeleteCommand command = new TeamMemberDeleteCommand(params, projectIndex);
        String expectedOutput = "Team member \"Sarah Hopkins\" has been removed from Project \"Project One\"";
        String actualOutput = command.executeCommand(projects, teamMembers);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void executeCommand_nonExistentMemberIdInProjectView_invalidMemberIdException()
            throws DukeExceptions {
        int projectIndex = 0;
        params.put("m", "10");
        TeamMemberDeleteCommand command = new TeamMemberDeleteCommand(params, projectIndex);
        String expectedOutput = "Team Member ID does not exist!";
        DukeExceptions exception = assertThrows(DukeExceptions.class, () ->
                command.executeCommand(projects, teamMembers));
        assertEquals(expectedOutput, exception.toString());
    }

    @Test
    void executeCommand_validMemberIdInProjectView_projectViewMember1RemovedFromListAndTaskAssignment()
            throws DukeExceptions {
        int projectIndex = 0;
        params.put("m", "1");
        TeamMemberDeleteCommand command = new TeamMemberDeleteCommand(params, projectIndex);
        command.executeCommand(projects, teamMembers);
        String expectedOutput = "Project \"Project One\"\n"
                + "\n"
                + "Description:\n"
                + "<project description empty>\n"
                + "\n"
                + " ---------------------\n"
                + "| TASK LIST           |\n"
                + " ---------------------\n"
                + "Index  Status   Description        Deadline        Priority      "
                + "Expected Hrs     Actual Hrs   | Members Involved\n"
                + "-----------------------------------------------------------"
                + "------------------------------------|------------------\n"
                + "1      (N)      Task One           -               -             "
                + "-                -            |Sarah Hopkins|\n"
                + "\n"
                + " \n"
                + "\n"
                + " ---------------------\n"
                + "| MEMBERS LIST        |\n"
                + " ---------------------\n"
                + "1. Sarah Hopkins\n";
        TaskListCommand listCommand = new TaskListCommand(projectIndex);
        String actualOutput = listCommand.executeCommand(projects, teamMembers);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void executeCommand_validMemberIdInHomeView_memberRemovedMessage() throws DukeExceptions {
        int projectIndex = -1;
        params.put("m", "2");
        TeamMemberDeleteCommand command = new TeamMemberDeleteCommand(params, projectIndex);
        String expectedOutput = "Team member \"Sarah Hopkins\" has been removed from program entirely";
        String actualOutput = command.executeCommand(projects, teamMembers);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void executeCommand_nonExistentMemberIdInHomeView_invalidMemberIdException()
            throws DukeExceptions {
        int projectIndex = -1;
        params.put("m", "10");
        TeamMemberDeleteCommand command = new TeamMemberDeleteCommand(params, projectIndex);
        String expectedOutput = "Team Member ID does not exist!";
        DukeExceptions exception = assertThrows(DukeExceptions.class, () ->
                command.executeCommand(projects, teamMembers));
        assertEquals(expectedOutput, exception.toString());
    }


    @Test
    void executeCommand_validMemberIdInHomeView_homeViewMember2RemovedFromList()
            throws DukeExceptions {
        int projectIndex = -1;
        params.put("m", "2");
        TeamMemberDeleteCommand command = new TeamMemberDeleteCommand(params, projectIndex);
        command.executeCommand(projects, teamMembers);
        String expectedOutput = "EZ Manager Home View\n"
                + "\n"
                + " ----------------------\n"
                + "| PROJECT LIST         |\n"
                + " ----------------------\n"
                + "\n"
                + "Index   Status   Project Name             Project Description                "
                + "Deadline     Tasks Completed     Remarks\n"
                + "-----------------------------------------------------------------------------"
                + "-------------------------------------------------------------------------\n"
                + "1.      N        Project One              -                                  "
                + "-            0/1                 -\n"
                + "\n"
                + " ----------------------\n"
                + "| MEMBERS LIST         |\n"
                + " ----------------------\n"
                + "\n"
                + "Index   Member Name                   Projects Involved\n"
                + "-----------------------------------------------------------------------------\n"
                + "1.      John Doe                      1. Project One";
        PrintHomeViewCommand homeViewCommand = new PrintHomeViewCommand();
        String actualOutput = homeViewCommand.executeCommand(projects, teamMembers);
        assertEquals(expectedOutput.trim(), actualOutput.trim());
    }

    @Test
    void executeCommand_validMemberIdInHomeView_projectViewMember2RemovedFromList()
            throws DukeExceptions {
        int homeView = -1;
        int projectIndex = 0;
        params.put("m", "2");
        TeamMemberDeleteCommand command = new TeamMemberDeleteCommand(params, homeView);
        command.executeCommand(projects, teamMembers);
        String expectedOutput = "Project \"Project One\"\n"
                + "\n"
                + "Description:\n"
                + "<project description empty>\n"
                + "\n"
                + " ---------------------\n"
                + "| TASK LIST           |\n"
                + " ---------------------\n"
                + "Index  Status   Description        Deadline        Priority      "
                + "Expected Hrs     Actual Hrs   | Members Involved\n"
                + "-------------------------------------------------------------------"
                + "----------------------------|------------------\n"
                + "1      (N)      Task One           -               -             "
                + "-                -            |John Doe|\n"
                + "\n"
                + " \n"
                + "\n"
                + " ---------------------\n"
                + "| MEMBERS LIST        |\n"
                + " ---------------------\n"
                + "1. John Doe\n";
        TaskListCommand listCommand = new TaskListCommand(projectIndex);
        String actualOutput = listCommand.executeCommand(projects, teamMembers);
        assertEquals(expectedOutput, actualOutput);
    }

}