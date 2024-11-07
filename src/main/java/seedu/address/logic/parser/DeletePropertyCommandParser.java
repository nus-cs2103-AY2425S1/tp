package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;

import java.util.logging.Logger;

import seedu.address.logic.commands.DeletePropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Unit;

/**
 * Parses input arguments and creates a new DeletePropertyCommand object
 */
public class DeletePropertyCommandParser implements Parser<DeletePropertyCommand> {
    private static final Logger logger = Logger.getLogger(DeletePropertyCommandParser.class.getName());

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePropertyCommand
     * and returns a DeletePropertyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePropertyCommand parse(String args) throws ParseException {
        requireNonNull(args, "Arguments cannot be null.");

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POSTALCODE, PREFIX_UNITNUMBER);

        validateArgumentsFormat(argMultimap, args);
        PostalCode postalCode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTALCODE).get());
        Unit unitNumber = ParserUtil.parseUnit(argMultimap.getValue(PREFIX_UNITNUMBER).get());
        logger.info("Parsed DeletePropertyCommand with PostalCode: " + postalCode + " and UnitNumber: " + unitNumber);
        return new DeletePropertyCommand(postalCode, unitNumber);
    }
    /**
     * Validates the format of the arguments in the ArgumentMultimap.
     *
     * @param argMultimap The tokenized arguments map.
     * @param args The original input arguments string.
     * @throws ParseException if there are duplicate prefixes, missing prefixes, or excess tokens.
     */
    private void validateArgumentsFormat(ArgumentMultimap argMultimap, String args) throws ParseException {
        requireNonNull(argMultimap, "ArgumentMultimap cannot be null.");

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POSTALCODE, PREFIX_UNITNUMBER);

        if (ParserUtil.hasExcessToken(args, PREFIX_POSTALCODE, PREFIX_UNITNUMBER)) {
            logger.warning("Excess tokens detected in input: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePropertyCommand.MESSAGE_USAGE));
        }

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_POSTALCODE, PREFIX_UNITNUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            logger.warning("Missing required prefixes or unexpected preamble in input: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePropertyCommand.MESSAGE_USAGE));
        }
    }
}
