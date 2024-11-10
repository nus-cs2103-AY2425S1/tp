package seedu.address.logic.parser.clientcommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.clientcommands.DeleteClientProfileCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteClientProfileCommand object
 */
public class DeleteClientProfileCommandParser implements Parser<DeleteClientProfileCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteClientProfileCommand
     * and returns a DeleteClientProfileCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteClientProfileCommand parse(String args) throws ParseException {

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args);

        try {
            // Parse the name and return the DeleteClientProfileCommand
            Index index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
            return new DeleteClientProfileCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteClientProfileCommand.MESSAGE_USAGE), pe);
        }
    }

}
