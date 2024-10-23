package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.AttendanceMarkingCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.commands.UnmarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Telegram;

/**
 * Parses input arguments and creates a new MarkAttendanceCommand or UnmarkAttendance object based on user input
 */
public class AttendanceMarkingCommandParser implements Parser<AttendanceMarkingCommand> {
    private String markType;
    public AttendanceMarkingCommandParser(String markType) {
        this.markType = markType;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttendanceCommand or
     * UnmarkAttendanceCommand based on {@code markType}
     * and returns a MarkAttendanceCommand or UnmarkAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AttendanceMarkingCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TELEGRAM, PREFIX_DATE);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_TELEGRAM, PREFIX_DATE)
                || !argumentMultimap.getPreamble().isEmpty()) {
            if (markType.equals(MarkAttendanceCommand.COMMAND_WORD)) {
                throw new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
            } else {
                throw new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, UnmarkAttendanceCommand.MESSAGE_USAGE));
            }
        }
        List<String> telegramKeywords = argumentMultimap.getAllValues(PREFIX_TELEGRAM);
        Attendance attendance = ParserUtil.parseAttendance(argumentMultimap.getValue(PREFIX_DATE).get());


        List<Telegram> telegrams = new ArrayList<>();
        for (String telegramStr : telegramKeywords) {
            String trimmedTelegram = telegramStr.trim();
            telegrams.add(ParserUtil.parseTelegram(trimmedTelegram));
        }

        if (markType.equals(MarkAttendanceCommand.COMMAND_WORD)) {
            return new MarkAttendanceCommand(telegrams, attendance);
        } else {
            return new UnmarkAttendanceCommand(telegrams, attendance);
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
