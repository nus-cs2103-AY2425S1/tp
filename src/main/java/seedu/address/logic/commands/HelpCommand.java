package seedu.address.logic.commands;

import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.KeywordCommandResult;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows commands that ClinicConnect provides\n"
            + "Please fix the command keyword typo";
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.\n"
            + "Press Esc to close help window.";
    private final String keyword;

    public HelpCommand(String keyword) {
        this.keyword = keyword;
    }

    public HelpCommand() {
        this.keyword = "";
    }

    @Override
    public CommandResult execute(Model model) {
        return new KeywordCommandResult(SHOWING_HELP_MESSAGE, keyword);
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
