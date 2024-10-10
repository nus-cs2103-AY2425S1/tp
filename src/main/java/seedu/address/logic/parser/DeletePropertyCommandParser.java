package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
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

        if (!arePrefixesPresent(argMultimap, PREFIX_POSTALCODE, PREFIX_UNITNUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POSTALCODE, PREFIX_UNITNUMBER);
        PostalCode postalCode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTALCODE).get());
        Unit unitNumber = ParserUtil.parseUnit(argMultimap.getValue(PREFIX_UNITNUMBER).get());

        return new DeletePropertyCommand(postalCode, unitNumber);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
