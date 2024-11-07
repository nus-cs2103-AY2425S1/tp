package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHSERVICE;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.logic.commands.BookApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Appt;
import seedu.address.model.patient.Nric;

/**
 * Parses input arguments and creates a new ApptCommand object
 * @throws ParseException
 */
public class BookApptCommandParser implements Parser<BookApptCommand> {
    private static final Logger logger = Logger.getLogger(BookApptCommandParser.class.getName());

    /**
     * Parses the given {@code String} of arguments in the context of the ApptCommand
     * and returns an ApptCommand object for execution.
     * @param args
     * @return ApptCommand
     * @throws ParseException if the user input does not conform the expected format
     */
    public BookApptCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.info("Parsing BookApptCommand");
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATETIME,
            PREFIX_HEALTHSERVICE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATETIME, PREFIX_HEALTHSERVICE)
            || argMultimap.getPreamble().isEmpty()) {
            logger.warning("Invalid command format");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BookApptCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATETIME, PREFIX_HEALTHSERVICE);
        String dateTime = argMultimap.getValue(PREFIX_DATETIME).get();
        logger.info("Parsing date and time");
        String healthService = argMultimap.getValue(PREFIX_HEALTHSERVICE).get();
        logger.info("Parsing health service");

        Appt appt = ParserUtil.parseSingleAppt(dateTime, healthService);
        logger.info("Parsing appt");
        Nric nric = ParserUtil.parseNric(argMultimap.getPreamble());
        logger.info("Parsing NRIC");

        return new BookApptCommand(nric, appt);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
