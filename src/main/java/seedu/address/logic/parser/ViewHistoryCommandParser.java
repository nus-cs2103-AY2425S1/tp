package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.InvalidIdException;
import seedu.address.logic.commands.ViewHistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ViewHistoryCommand object.
 */
public class ViewHistoryCommandParser implements Parser<ViewHistoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewHistoryCommand
     * and returns a ViewHistoryCommand object for execution.
     *
     * @param args the input arguments string.
     * @return a ViewHistoryCommand object.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public ViewHistoryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_ID);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_ID)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewHistoryCommand.MESSAGE_USAGE));
        }

        // Parse the patient ID
        int patientId;
        try {
            patientId = ParserUtil.parsePersonId(argumentMultimap.getAllValues(PREFIX_ID).get(0));
        } catch (InvalidIdException e) {
            throw new ParseException(MESSAGE_INVALID_ID, e);
        }

        // Parse the date from the x/ prefix, or set it to null if not provided
        LocalDateTime dateTime = null;
        Optional<String> dateTimeString = argumentMultimap.getValue(PREFIX_DATE);
        if (dateTimeString.isPresent()) {
            try {
                dateTime = ParserUtil.parseDateWithNoLimit(dateTimeString.get().trim());
            } catch (ParseException e) {
                throw new ParseException("Invalid date-time format, please use yyyy-MM-dd HH:mm.");
            }
        }

        return new ViewHistoryCommand(patientId, dateTime);
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
