package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.Model;


/**
 * Terminates the program.
 */
public class QuitCommand extends Command {

    public static final String SHORT_COMMAND_WORD = ":q";
    public static final String LONG_COMMAND_WORD = ":quit";

    public static final String MESSAGE_QUIT_ACKNOWLEDGEMENT = "Quitting VBook!";
    public static final String COMMAND_SUMMARY_ACTION = "Quit";
    public static final String COMMAND_SUMMARY_FORMAT =
            ":quit";
    public static final String COMMAND_SUMMARY_EXAMPLES =
            ":quit";



    public static final List<String> INVALID_VARIANTS = Arrays.asList("quit", "q");
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_QUIT_ACKNOWLEDGEMENT, false, true);
    }

}
