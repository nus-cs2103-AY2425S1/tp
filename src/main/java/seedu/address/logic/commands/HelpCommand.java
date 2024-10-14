package seedu.address.logic.commands;

import seedu.address.model.Model;

import java.util.Objects;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD + " for overview of commands\n"
            + "Example: " + COMMAND_WORD + " add for detailed usage of add command\n";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public final String usage;

    public HelpCommand(String usage) {
        this.usage = usage;
    }

    public HelpCommand() {
        this.usage = "";
    }

    @Override
    public CommandResult execute(Model model) {
        if (Objects.equals(usage, "")) {
            return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        } else {
            return new CommandResult(usage, false, false);
        }
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
        return usage.equals(otherHelpCommand.usage);
    }
}
