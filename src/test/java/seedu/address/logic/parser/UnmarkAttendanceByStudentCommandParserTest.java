package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnmarkAttendanceByStudentCommand;
import seedu.address.model.person.Attendance;
import seedu.address.model.tutorial.Tutorial;

public class UnmarkAttendanceByStudentCommandParserTest {
    private UnmarkAttendanceByStudentCommandParser parser = new UnmarkAttendanceByStudentCommandParser();

    @Test
    public void parse_validArgs_returnsUnmarkAttendanceByStudentCommand() {
        Attendance attendance = new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT));
        UnmarkAttendanceByStudentCommand expectedCommand =
                new UnmarkAttendanceByStudentCommand(INDEX_FIRST_PERSON, attendance, new Tutorial("Mathematics"));
        assertParseSuccess(parser, "1 attend/12/12/2024 tut/Mathematics", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkAttendanceByStudentCommand.MESSAGE_USAGE));
    }
}
