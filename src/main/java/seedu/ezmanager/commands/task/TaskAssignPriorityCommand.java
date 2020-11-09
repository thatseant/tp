package seedu.ezmanager.commands.task;

import seedu.ezmanager.EZExceptions;
import seedu.ezmanager.commands.Command;
import seedu.ezmanager.member.TeamMember;
import seedu.ezmanager.project.Project;
import seedu.ezmanager.task.Task;
import seedu.ezmanager.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;

import static seedu.ezmanager.Parser.getHashValue;

public class TaskAssignPriorityCommand extends Command {

    private int taskIndex;
    private int projectIndex;
    private int priority;
    HashMap<String, String> params;

    public TaskAssignPriorityCommand(HashMap<String, String> params, int projectIndex)
            throws EZExceptions {
        assert projectIndex >= 0 : "projectIndex must be positive integer!";
        this.params = params;
        this.projectIndex = projectIndex;
        this.parse();
    }

    public void parse() throws EZExceptions {
        try {
            taskIndex = Integer.parseInt(getHashValue(params, "t")) - 1;
            priority = Integer.parseInt(getHashValue(params, "p"));
        } catch (NumberFormatException e) {
            throw new EZExceptions("indexNonInteger");
        }
    }

    public String executeCommand(ArrayList<Project> projects,
                                 ArrayList<TeamMember> teamMembers) throws EZExceptions {
        if (projects.size() == 0) {
            throw new EZExceptions("emptyProjectList");
        }
        try {
            Task selectedTask = projects.get(projectIndex).getTask(taskIndex);
            selectedTask.setPriority(priority);
            return Ui.printPriorityAssignedToTaskMessage(priority, selectedTask.getTaskDescription());
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new EZExceptions("invalidTaskID");
        }
    }

    public Boolean isExit() {
        return false;
    }

}
