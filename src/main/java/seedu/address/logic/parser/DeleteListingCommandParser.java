package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MISSING_LISTING_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.DeleteListingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new {@code DeleteListingsCommand} object.
 */
public class DeleteListingCommandParser implements Parser<DeleteListingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteListingsCommand}
     * and returns a {@code DeleteListingsCommand} object for execution.
     *
     * @param args the input arguments from the user.
     * @return a new instance of {@code DeleteListingsCommand}.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeleteListingCommand parse(String args) throws ParseException {
        String namePrefix = PREFIX_NAME.toString();
        String trimmedArgs = args.trim();

        // Check if the input contains the "n/" prefix
        if (!trimmedArgs.startsWith(namePrefix)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteListingCommand.MESSAGE_USAGE));
        }

        // Extract the name after "n/"
        String nameString = trimmedArgs.substring(namePrefix.length()).trim();

        // Check if the name is empty
        if (nameString.isEmpty()) {
            throw new ParseException(MISSING_LISTING_NAME);
        }

        try {
            Name name = ParserUtil.parseName(nameString);
            return new DeleteListingCommand(name);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteListingCommand.MESSAGE_USAGE), pe);
        }
    }
}
