package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String SHORT_COMMAND_WORD = ":h";
    public static final String LONG_COMMAND_WORD = ":help";

    public static final String MESSAGE_USAGE =
            "\"" + SHORT_COMMAND_WORD + "\"" + " OR "
            + "\"" + LONG_COMMAND_WORD + "\""
            + ": Shows program usage instructions.\n"
            + "Example 1: " + LONG_COMMAND_WORD + "\n"
            + "Example 2: " + SHORT_COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened command cheatsheet.";
    public static final String COMMAND_SUMMARY_ACTION = "Help";
    public static final String COMMAND_SUMMARY_FORMAT = LONG_COMMAND_WORD + "\n" + SHORT_COMMAND_WORD;
    public static final String COMMAND_SUMMARY_EXAMPLES = LONG_COMMAND_WORD + "\n" + SHORT_COMMAND_WORD;


    public static final List<String> INVALID_VARIANTS = Arrays.asList("help", ":h", "h");

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
