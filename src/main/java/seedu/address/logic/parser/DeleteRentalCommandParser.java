package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteRentalCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRentalCommand object
 */
public class DeleteRentalCommandParser implements Parser<DeleteRentalCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRentalCommand
     * and returns a DeleteRentalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteRentalCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_CLIENT_INDEX, PREFIX_RENTAL_INDEX);

        if (argMultimap.getValue(PREFIX_CLIENT_INDEX).isEmpty()
                || argMultimap.getValue(PREFIX_RENTAL_INDEX).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRentalCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLIENT_INDEX, PREFIX_RENTAL_INDEX);

        try {
            Index clientIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLIENT_INDEX).get());
            Index rentalIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_RENTAL_INDEX).get());
            return new DeleteRentalCommand(clientIndex, rentalIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRentalCommand.MESSAGE_USAGE), pe);
        }
    }
}
