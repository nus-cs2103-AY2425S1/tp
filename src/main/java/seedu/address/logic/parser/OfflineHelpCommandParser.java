package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.OfflineHelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a HelpCommand object.
 * <p>
 * This class implements the {@link Parser} interface and is responsible for
 * interpreting the user input specific to the help command. It validates the
 * input format and ensures that it complies with the expected structure.
 * </p>
 */
public class OfflineHelpCommandParser implements Parser<OfflineHelpCommand> {
    /**
     * Parses user input to create an OfflineHelpCommand object.
     * <p>
     * This class implements the {@link Parser} interface and is responsible for
     * interpreting the user input specific to help command. It validates the
     * input format and ensures that it complies with the expected structure.
     * </p>
     *
     * @author gabriellegtw
     */
    public OfflineHelpCommand parse(String userInput) throws ParseException {
        String[] words = userInput.split(" ");
        if (words.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, OfflineHelpCommand.MESSAGE_USAGE));
        } else {
            return new OfflineHelpCommand();
        }
    }
}
