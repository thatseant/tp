package seedu.ezmanager.commands.member;

import seedu.ezmanager.EzExceptions;
import seedu.ezmanager.commands.Command;
import seedu.ezmanager.member.TeamMember;
import seedu.ezmanager.project.Project;
import seedu.ezmanager.task.Task;
import seedu.ezmanager.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;

import static seedu.ezmanager.Parser.getHashValue;

public class TeamMemberAssignToTaskCommand extends Command {

    private int taskIndex;
    private int projectIndex;
    private int memberIndex;
    HashMap<String, String> params;

    public TeamMemberAssignToTaskCommand(HashMap<String, String> params, int projectIndex)
            throws EzExceptions {
        this.params = params;
        this.projectIndex = projectIndex;
        this.parse();
    }

    public void parse() throws EzExceptions {
        try {
            taskIndex = Integer.parseInt(getHashValue(params, "t")) - 1;
        } catch (NumberFormatException e) {
            throw new EzExceptions("invalidTaskID");
        }
        try {
            memberIndex = Integer.parseInt(getHashValue(params, "m")) - 1;
        } catch (NumberFormatException e) {
            throw new EzExceptions("invalidTeamMemberID");
        }
    }

    public String executeCommand(ArrayList<Project> projects,
                                 ArrayList<TeamMember> teamMembers) throws EzExceptions {
        Task selectedTask;
        TeamMember teamMember;
        assert projectIndex >= 0 : "projectIndex should be minus one "
                + "of the current project it was selected";
        Project project = projects.get(projectIndex);
        try {
            if (project.getTaskList().isEmpty()) {
                throw new EzExceptions("emptyTaskList");
            }
            selectedTask = project.getTask(taskIndex);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new EzExceptions("invalidTaskID");
        }

        try {
            if (project.getTeamMembers().isEmpty()) {
                throw new EzExceptions("emptyTeamMembersList");
            }
            teamMember = project.getTeamMembers().get(memberIndex);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new EzExceptions("invalidTeamMemberID");
        }
        selectedTask.setMember(teamMember);
        return Ui.printMemberAssignedToTaskMessage(teamMember.getName(), selectedTask.getTaskDescription());
    }

    public Boolean isExit() {
        return false;
    }
}
