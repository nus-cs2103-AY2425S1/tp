package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.commands.SeeScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.SameWeekAsDatePredicate;

/**
 * Parses input arguments and creates a new SeeScheduleCommand object
 */
public class SeeScheduleCommandParser implements Parser<SeeScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SeeScheduleCommand
     * and returns a SeeScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SeeScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (args.isEmpty() || !AddCommandParser.arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SeeScheduleCommand.MESSAGE_USAGE));
        }
        try {
            LocalDate date = LocalDate.parse(
                    argMultimap.getValue(PREFIX_DATE).get(),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy")
            );
            return new SeeScheduleCommand(new SameWeekAsDatePredicate(date));
        } catch (DateTimeParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SeeScheduleCommand.MESSAGE_USAGE)
            );
        }
    }
}
