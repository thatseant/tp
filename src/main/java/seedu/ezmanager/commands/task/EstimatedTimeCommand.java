//@@author thatseant

package seedu.ezmanager.commands.task;

import seedu.ezmanager.EZExceptions;
import seedu.ezmanager.EZLogger;
import seedu.ezmanager.commands.Command;
import seedu.ezmanager.member.TeamMember;
import seedu.ezmanager.project.Project;
import seedu.ezmanager.task.Task;
import seedu.ezmanager.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import static seedu.ezmanager.Parser.getHashValue;
import static seedu.ezmanager.Util.HOUR_INDEX_KEY;
import static seedu.ezmanager.Util.INVALID_TASK_ID;
import static seedu.ezmanager.Util.MINUTES_IN_HOUR_INT;
import static seedu.ezmanager.Util.MINUTE_INDEX_KEY;
import static seedu.ezmanager.Util.TASK_INDEX_KEY;
import static seedu.ezmanager.Util.USER_JAVA_INDEX_DIFF;

public class EstimatedTimeCommand extends Command {

    private int taskIndex;
    private int durationInMinutes;
    private int projectIndex;
    HashMap<String, String> params;

    public EstimatedTimeCommand(HashMap<String, String> params, int projectIndex)
            throws EZExceptions {
        assert projectIndex >= 0 : "projectIndex must be positive integer!";
        this.params = params;
        this.projectIndex = projectIndex;
        this.parse();
    }

    public void parse() throws EZExceptions {
        try {
            taskIndex = Integer.parseInt(getHashValue(params, TASK_INDEX_KEY)) - USER_JAVA_INDEX_DIFF;
            int hours = Integer.parseInt(getHashValue(params, HOUR_INDEX_KEY));
            int minutes = Integer.parseInt(getHashValue(params, MINUTE_INDEX_KEY));
            durationInMinutes = hours * MINUTES_IN_HOUR_INT + minutes;
        } catch (NumberFormatException e) {
            throw new EZExceptions(INVALID_TASK_ID);
        }
    }

    public String executeCommand(ArrayList<Project> projects, ArrayList<TeamMember> members) throws EZExceptions {
        try {
            EZLogger.log(Level.INFO, "Executing Command");
            Project project = projects.get(projectIndex);
            Task task = project.getTask(taskIndex);
            EZLogger.log(Level.INFO, "Task Retrieved");
            task.addEstimate(durationInMinutes);
            EZLogger.log(Level.INFO, "Estimated Time Taken added to task.");
            int hours = task.getEstimate() / MINUTES_IN_HOUR_INT;
            int minutes = task.getEstimate() % MINUTES_IN_HOUR_INT;
            return Ui.printEstimateAddedMessage(task.getDescription(), hours, minutes);
        } catch (IndexOutOfBoundsException e) {
            EZLogger.log(Level.WARNING, "Task Not Found.");
            throw new EZExceptions(INVALID_TASK_ID);
        }
    }

    public Boolean isExit() {
        return false;
    }

}