package seedu.address.logic.parser.listingcommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.listingcommands.DeleteListingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

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
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args);
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteListingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteListingCommand.MESSAGE_USAGE), pe);
        }
    }
}
