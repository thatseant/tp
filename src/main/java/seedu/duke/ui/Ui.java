package seedu.duke.ui;

import seedu.duke.member.TeamMember;
import seedu.duke.project.Project;
import seedu.duke.project.ProjectList;
import seedu.duke.task.Task;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Ui {

    private static final String MESSAGE_SINGLE_LINE = "____________________________________________________________";
    private static final String MESSAGE_WELCOME = "Hello from Duke!\n"
            + "What can I do for you?";
    private static final String MESSAGE_GOODBYE = "See you again!";
    private static final String MESSAGE_LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";


    public void printWelcome() {
        System.out.println(MESSAGE_WELCOME);
        System.out.println(MESSAGE_SINGLE_LINE);
    }

    public static String printGoodbyeMessage() {
        return MESSAGE_GOODBYE;
    }

    public void printLine() {
        System.out.println(MESSAGE_SINGLE_LINE);
    }

    public static void printOutput(String output) {
        System.out.println(output);
    }

    public static String printMemberAddedMessage(String name) {
        return "Team member \"" + name + "\" has been added";
    }

    public static String printMemberRemovedMessage(String name) {
        return "Team member \"" + name + "\" has been removed";
    }

    public static String printProjectDeletedMessage(Project project) {
        return "Project \"" + project.getProjectName() + "\" deleted";
    }

    public static String printProjectListMessage(ArrayList<Project> projects) {
        String output = "";
        output += "List of Projects:";
        for (int i = 0; i < projects.size(); i++) {
            output += "\n     " + (i + 1) + "." + projects.get(i).getProjectName();
            if (projects.get(i).getProjectDeadline() != null) {
                output += " (" + projects.get(i).getProjectDeadline() + ") ";
            }
        }
        return output;
    }

    public static String printTaskListMessage(Project project) {
        int numberOfTasks = project.getTaskList().size();
        String output = "List of Tasks:";
        for (int i = 0; i < numberOfTasks; i++) {
            output += "\n     " + (i + 1) + "." + project.getTask(i);
        }
        return output;
    }

    public static String printProjectCreatedMessage(String projectName) {
        return "Project \"" + projectName + "\" created!";
    }

    public static String printProjectDescriptionAddedMessage(Project project) {
        return "Project description added \"" + project.getDescription() + "\".";
    }

    public static String printProjectDeadlineAddedMessage(Project project, LocalDate date) {
        return "Deadline " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " added to Project " + project.getProjectName();
    }

    public static String printEmptyAdditionalProjectInformationMessage() {
        return "<project description empty> | <project deadline empty> | "
                + "<team members involved empty>";
    }

    public static String printTaskCreatedMessage(String taskName) {
        return "Task \"" + taskName + "\" created!";
    }

    public static String printEstimateAddedMessage(String taskName, int hours, int minutes) {
        return "Task \"" + taskName + "\" has estimated time of " + hours + " hours and " + minutes + " minutes";
    }

    public static String printActualDurationAddedMessage(String taskName, int hours, int minutes) {
        return "Task \"" + taskName + "\" took " + hours + " hours and " + minutes + " minutes to be completed.";
    }

    public static String printTaskDoneMessage(String taskName) {
        return "Task \"" + taskName + "\" is done!";
    }

    public static String printTaskDeletedMessage(String taskName) {
        return "Task \"" + taskName + "\" removed!";
    }

    public static String printHomeView(ArrayList<Project> projects, ArrayList<TeamMember> teamMembers) {
        String output = "Hello! Welcome to EZ Manager!\n";
        output += "\n ---------------------- ";
        output += "\n| PROJECT LIST         |";
        output += "\n ---------------------- \n";
        output += "\nStatus     Project Name                       Deadline      Tasks Completed";
        output += "\n---------------------------------------------------------------------------";
        int i = 0;
        for (Project project : projects) {
            String projectName = project.getProjectName();
            String paddedProjectName = String.format("%-35s", (i + 1) + ") " + projectName);
            String paddedProjectDeadline;
            if (project.getProjectDeadline() != null) {
                String projectDeadline = project.getProjectDeadline().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                paddedProjectDeadline = String.format("%-14s", projectDeadline);
            } else {
                paddedProjectDeadline = String.format("%-14s", "-");
            }
            String taskCompleted = project.getNumberOfFinishedTask() + "/"
                    + project.getNumberOfTask();
            String paddedTaskCompleted = String.format("%-15s", taskCompleted);
            output += "\n           " + paddedProjectName + paddedProjectDeadline + paddedTaskCompleted;
            i++;
        }
        output += "\n\n ---------------------- ";
        output += "\n| MEMBERS LIST         |";
        output += "\n ---------------------- \n";
        output += "\nName                      Role              Projects Involved              ";
        output += "\n---------------------------------------------------------------------------";
        int j = 0;
        for (TeamMember member : teamMembers) {
            String memberName = member.getName();
            String concatenatedName;
            if (member.getName().length() >= 23) {
                concatenatedName = member.getName().substring(0, 22);
            } else {
                concatenatedName = memberName;
            }
            String paddedMemberName = String.format("%-26s", (j + 1) + ") " + concatenatedName);
            //String memberRole;
            output += "\n" + paddedMemberName + "                  ";
            if (member.getAssignedProjects() != null) {
                for (int k = 0; k < member.getAssignedProjects().size(); k++) {
                    Project assignedProject = member.getAssignedProjects().get(k);
                    String paddedAssignedProject = String.format("%-28s", assignedProject.getProjectName());
                    if (k == 0) {
                        output += "1. " + paddedAssignedProject;
                    } else {
                        output += "\n                                            "
                                + (k + 1) + ". " + paddedAssignedProject;
                    }
                }
            }
            j++;
            output += System.lineSeparator();
        }
        return output;
    }

    public static String printTaskSelectedMessage(String taskName) {
        return "Selected Task: " + taskName;
    }

    public static String printInHomeViewMessage() {
        return "Already in Home View!";
    }

    public static String printSwitchedToHomeViewMessage() {
        return "Switched to Home View";
    }

    public static String printMemberAssignedToTaskMessage(String memberName, String taskName) {
        return "Member \"" + memberName + "\" has been assigned to \"" + taskName + "\"";
    }

    public static String printPriorityAssignedToTaskMessage(String priority, String taskName) {
        return "Priority \"" + priority + "\" has been assigned to \"" + taskName + "\"";
    }

}
