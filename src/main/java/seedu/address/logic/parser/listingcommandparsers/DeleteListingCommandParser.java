package seedu.address.logic.parser.listingcommandparsers;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.listingcommands.DeleteListingCommand;
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
        Index index = ParserUtil.parseIndexWithInvalidCommandFormatMessage(args, DeleteListingCommand.MESSAGE_USAGE);
        return new DeleteListingCommand(index);
    }
}
