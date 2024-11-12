package seedu.address.logic.parser;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a {@code HelpCommand} object.
 * The input is expected to be the "help" command with no additional arguments.
 * If extra arguments are provided, a {@code HelpCommand} object with a warning message is returned.
 */
public class HelpCommandParser implements Parser<HelpCommand> {
    private static final String WARNING_PARAMETERS_SUPPLIED = "Additional parameters supplied have been ignored";

    /**
     * Parses the given input arguments and creates a {@code HelpCommand} object.
     * The input is expected to contain only the "help" command with no additional arguments.
     *
     * <p>If extra arguments are provided, a {@code HelpCommand} object with a warning message is returned.</p>
     *
     * @param userInput The user input string containing the "help" command.
     * @return A {@code HelpCommand} object, with a warning message if extra arguments are supplied.
     * @throws ParseException If an error occurs while parsing the input, such as invalid format.
     */
    public HelpCommand parse(String userInput) throws ParseException {
        String[] words = userInput.trim().split(" ");
        if (words.length != 1) {
            return new HelpCommand(WARNING_PARAMETERS_SUPPLIED);
        } else {
            return new HelpCommand();
        }
    }
}
