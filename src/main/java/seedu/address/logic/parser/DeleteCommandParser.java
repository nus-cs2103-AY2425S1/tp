package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MISSING_CLIENT_NAME;

import seedu.address.logic.commands.DeleteClientProfileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteClientProfileCommand object
 */
public class DeleteCommandParser implements Parser<DeleteClientProfileCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteClientProfileCommand
     * and returns a DeleteClientProfileCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteClientProfileCommand parse(String args) throws ParseException {
        String namePrefix = "n/";
        String trimmedArgs = args.trim();

        // Check if the input contains the "n/" prefix
        if (!trimmedArgs.startsWith(namePrefix)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteClientProfileCommand.MESSAGE_USAGE));
        }

        // Extract the name after "n/"
        String nameString = trimmedArgs.substring(namePrefix.length()).trim();

        // Check if the name is empty
        if (nameString.isEmpty()) {
            throw new ParseException(MISSING_CLIENT_NAME);
        }

        try {
            // Parse the name and return the DeleteClientProfileCommand
            Name name = ParserUtil.parseName(nameString);
            return new DeleteClientProfileCommand(name);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteClientProfileCommand.MESSAGE_USAGE), pe);
        }
    }

}
