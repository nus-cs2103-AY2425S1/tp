package seedu.ddd.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_INVALID_FLAGS;
import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_EVENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_VENDOR;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ddd.logic.parser.CommandFlag.CLIENT;
import static seedu.ddd.logic.parser.CommandFlag.EVENT;
import static seedu.ddd.logic.parser.CommandFlag.VENDOR;
import static seedu.ddd.logic.parser.ParserUtil.parseFlags;

import java.util.stream.Stream;

import seedu.ddd.logic.commands.Command;
import seedu.ddd.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandFlagParser implements Parser<Command> {

    public static final String ADD_FLAG_PARSE_ERROR = " to add a client contact, vendor contact, "
            + "or event respectively.";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public Command parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_SERVICE, FLAG_CLIENT, FLAG_VENDOR,
                        FLAG_EVENT);

        CommandFlag commandFlag;

        try {
            commandFlag = parseFlags(argMultimap);
        } catch (ParseException pe) {
            throw new ParseException(String.format(pe.getMessage(), ADD_FLAG_PARSE_ERROR));
        }

        if (commandFlag == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_FLAGS, ADD_FLAG_PARSE_ERROR));
        }

        switch (commandFlag) {
        case CLIENT:
            return new AddContactCommandParser().parse(argMultimap, CLIENT);
        case VENDOR:
            return new AddContactCommandParser().parse(argMultimap, VENDOR);
        case EVENT:
            return new AddEventCommandParser().parse(argMultimap, EVENT);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_FLAGS, ADD_FLAG_PARSE_ERROR));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
