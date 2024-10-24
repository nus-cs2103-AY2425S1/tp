package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows commands that ClinicConnect provides.\n"
            + "Example: " + COMMAND_WORD
            + "\n\n" + "OR" + "\n\n"
            + COMMAND_WORD + " <keyword>: Shows usage of the command of the specific keyword.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " add";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private final String keyword;

    public HelpCommand(String keyword) {
        this.keyword = keyword;
    }

    public HelpCommand() {
        this.keyword = "";
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, keyword, true, null, false, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HelpCommand)) {
            return false;
        }

        HelpCommand otherHelpCommand = (HelpCommand) other;
        return keyword.equals(otherHelpCommand.keyword);
    }
}
