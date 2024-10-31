package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;

import seedu.address.logic.commands.DeletePropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Unit;

/**
 * Parses input arguments and creates a new DeletePropertyCommand object
 */
public class DeletePropertyCommandParser implements Parser<DeletePropertyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePropertyCommand
     * and returns a DeletePropertyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePropertyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POSTALCODE, PREFIX_UNITNUMBER);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POSTALCODE, PREFIX_UNITNUMBER);
        if (ParserUtil.hasExcessToken(args, PREFIX_POSTALCODE, PREFIX_UNITNUMBER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePropertyCommand.MESSAGE_USAGE));
        }
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_POSTALCODE, PREFIX_UNITNUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePropertyCommand.MESSAGE_USAGE));
        }

        PostalCode postalCode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTALCODE).get());
        Unit unitNumber = ParserUtil.parseUnit(argMultimap.getValue(PREFIX_UNITNUMBER).get());

        return new DeletePropertyCommand(postalCode, unitNumber);
    }
}
