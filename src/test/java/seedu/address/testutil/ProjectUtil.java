package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;

import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.model.project.Project;

/**
 * A utility class for Employee.
 */
public class ProjectUtil {

    /**
     * Returns an add command string for adding the {@code employee}.
     */
    public static String getAddProjectCommand(Project project) {
        return AddProjectCommand.COMMAND_WORD + " " + getProjectDetails(project);
    }

    /**
     * Returns the part of command string for the given {@code employee}'s details.
     */
    public static String getProjectDetails(Project project) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_PROJECT_ID + project.getId().fullId + " ");
        sb.append(PREFIX_PROJECT_NAME + project.getName().fullName + " ");
        return sb.toString();
    }
}
