package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UnmarkAttendanceCommand;
import seedu.address.model.person.AttendanceList;

public class UnmarkAttendanceCommandParserTest {
    private UnmarkAttendanceCommandParser parser = new UnmarkAttendanceCommandParser();

    @Test
    public void parse_validArgs_success() {
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(Index.fromOneBased(3),
                LocalDateTime.of(2024, 1, 1, 12, 0));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "3 d/01/01/2024 12:00", command);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n 3 \t d/01/01/2024 12:00  ", command);
    }

    @Test
    public void parse_missingFields_failure() {
        String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkAttendanceCommand.MESSAGE_USAGE);

        // empty
        assertParseFailure(parser, "     ", MESSAGE_INVALID_FORMAT);

        // no prefix
        assertParseFailure(parser, "3 01/01/2024 12:00", MESSAGE_INVALID_FORMAT);

        // missing index
        assertParseFailure(parser, "d/01/01/2024 12:00", MESSAGE_INVALID_FORMAT);

        // missing datetime
        assertParseFailure(parser, "3", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_duplicateFields_failure() {
        // duplicate datetimes
        assertParseFailure(parser, "3 d/01/01/2024 12:00 d/08/01/2024 12:00",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));
    }

    @Test
    public void parse_invalidDate_failure() {
        String MESSAGE_DATE_CONSTRAINTS = "Date must be in the format: " + AttendanceList.DATE_TIME_FORMAT;

        // wrong date part
        assertParseFailure(parser, "3 d/01-01-2024 12:00", MESSAGE_DATE_CONSTRAINTS);

        // missing hour and minute
        assertParseFailure(parser, "3 d/01/01/2024", MESSAGE_DATE_CONSTRAINTS);
    }
}
