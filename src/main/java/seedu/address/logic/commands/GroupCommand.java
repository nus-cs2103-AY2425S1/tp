package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Groups students together in the application.
 */
public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a new group with the specified students.\n"
            + "Groups the students by the group name and the list of students provided.\n"
            + "Existing groups with the same name will not be overwritten.\n"
            + "Parameters: GROUPNAME (alphanumeric) "
            + "STUDENT1 STUDENT2... (must be valid student names)\n"
            + "Example: " + COMMAND_WORD + " StudyGroup1 Benjamin Candice\n"
            + "Example: " + COMMAND_WORD + " TeamA Martin Candice";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Not implemented";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Group feature!");
    }
}
