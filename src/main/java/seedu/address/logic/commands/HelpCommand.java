package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

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
        requireNonNull(model);
        if (usage.isEmpty()) {
            return new CommandResult(SHOWING_HELP_MESSAGE, false, true, false);
        }
        return new CommandResult(usage, false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HelpCommand otherHelpCommand)) {
            return false;
        }

        return usage.equals(otherHelpCommand.usage);
    }
}
