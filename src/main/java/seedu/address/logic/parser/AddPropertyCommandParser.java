package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.Ask;
import seedu.address.model.property.Bid;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;

/**
 * Parses input arguments and creates a new {@link AddPropertyCommand} object.
 * The parser processes the input string to extract the necessary parameters
 * (postalCode, Unit) for creating a {@link Property}.
 */
public class AddPropertyCommandParser implements Parser<AddPropertyCommand> {

    private static final Logger logger = LogsCenter.getLogger(FilterPropertyCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPropertyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.info("Parsing filter property command: " + args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POSTALCODE, PREFIX_UNITNUMBER, PREFIX_TYPE, PREFIX_ASK,
                        PREFIX_BID);
        if (!arePrefixesPresent(argMultimap, PREFIX_POSTALCODE, PREFIX_UNITNUMBER, PREFIX_TYPE, PREFIX_ASK, PREFIX_BID)
                || !argMultimap.getPreamble().isEmpty()) {
            logger.warning("Wrong prefixes.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POSTALCODE, PREFIX_UNITNUMBER, PREFIX_TYPE, PREFIX_ASK,
                PREFIX_BID);
        PostalCode postalCode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTALCODE).get());
        Unit unit = ParserUtil.parseUnit(argMultimap.getValue(PREFIX_UNITNUMBER).get());
        Type type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        Ask ask = ParserUtil.parseAsk(argMultimap.getValue(PREFIX_ASK).get());
        Bid bid = ParserUtil.parseBid(argMultimap.getValue(PREFIX_BID).get());

        Property property = new Property(postalCode, unit, type, ask, bid);
        logger.info(String.format("Successfully parsed add property command: %s. Sending for execution.", args));

        return new AddPropertyCommand(property);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
