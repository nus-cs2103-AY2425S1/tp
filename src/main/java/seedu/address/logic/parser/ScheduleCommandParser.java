package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Date;
import seedu.address.model.person.SchedulePredicate;

/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
              ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        //at least one feature is present
        if (!arePrefixesPresent(argMultimap, PREFIX_DATE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }
        //can only see schedule for one day at a time
        if (argMultimap.getAllValues(PREFIX_DATE).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }

        Date date;
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            String dateString = argMultimap.getValue(PREFIX_DATE).get();
            date = ParserUtil.parseDate(dateString);
        } else {
            date = new Date(LocalDateTime.MIN); // Default constructor if no date is provided
        }

        return new ScheduleCommand(new SchedulePredicate(date));
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
