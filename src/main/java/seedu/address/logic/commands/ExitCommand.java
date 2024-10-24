package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.Model;


/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = ":exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";
    public static final String COMMAND_SUMMARY_ACTION = "Exit";
    public static final String COMMAND_SUMMARY_FORMAT =
            ":exit";
    public static final String COMMAND_SUMMARY_EXAMPLES =
            ":exit";



    public static final List<String> INVALID_VARIANTS = Arrays.asList("exit", "q", ":q");
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
