package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MISSING_CLIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.DeletePropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new {@code DeletePropertyCommand} object.
 */
public class DeletePropertyCommandParser implements Parser<DeletePropertyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeletePropertyCommand}
     * and returns a {@code DeletePropertyCommand} object for execution.
     *
     * @param args the input arguments from the user.
     * @return a new instance of {@code DeletePropertyCommand}.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeletePropertyCommand parse(String args) throws ParseException {
        String namePrefix = PREFIX_NAME.toString();
        String trimmedArgs = args.trim();

        // Check if the input contains the "n/" prefix
        if (!trimmedArgs.startsWith(namePrefix)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePropertyCommand.MESSAGE_USAGE));
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
            return new DeletePropertyCommand(name);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePropertyCommand.MESSAGE_USAGE), pe);
        }
    }
}
