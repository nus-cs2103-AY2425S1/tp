package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDateTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Attendance;

/**
 * Parses input arguments and creates a new MarkAttendanceCommand object
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttendanceCommand
     * and returns a MarkAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_ATTENDANCE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATE, PREFIX_ATTENDANCE);
        if (!argMultimap.getValue(PREFIX_DATE).isPresent() || !argMultimap.getValue(PREFIX_ATTENDANCE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAttendanceCommand.MESSAGE_USAGE));
        }
        LocalDateTime date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Attendance attendance = ParserUtil.parseAttendance(argMultimap.getValue(PREFIX_ATTENDANCE).get());

        return new MarkAttendanceCommand(index, date, attendance);
    }
}
