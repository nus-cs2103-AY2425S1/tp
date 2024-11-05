package seedu.address.logic.parser;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a HelpCommand object.
 * <p>
 * This class implements the {@link Parser} interface and is responsible for
 * interpreting the user input specific to the help command. It validates the
 * input format and ensures that it complies with the expected structure.
 * </p>
 */
public class HelpCommandParser implements Parser<HelpCommand> {
    private static final String WARNING_PARAMETERS_SUPPLIED = "Additional parameters supplied have been ignored";

    /**
     * Parses user input to create a HelpCommand object.
     * <p>
     * This class implements the {@link Parser} interface and is responsible for
     * interpreting the user input specific to help command. It validates the
     * input format and ensures that it complies with the expected structure.
     * </p>
     *
     * @author wnayar
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
