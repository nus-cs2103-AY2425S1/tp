package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Command List: add, delete, edit, find, list\n"
            + "Example for Add command: `add n/[NAME] th/[TELEGRAM HANDLE] e/[EMAIL] s/[STUDENT_STATUS] r/[ROLE]`\n"
            + "Example for Delete command: `delete [INDEX]` OR `delete [NAME]`\n"
            + "Example for Edit command: `edit [INDEX] [PREFIX] [new description]`\n"
            + "Example for Find command: `find [KEYWORD]`\n"
            + "Example for List command: `list` OR `list all` OR `list contacts` OR `list all contacts`\n"
            + "For more details, visit our user guide on the popup screen\n";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
