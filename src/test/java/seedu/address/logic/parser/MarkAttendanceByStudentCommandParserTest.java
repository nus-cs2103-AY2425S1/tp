package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkAttendanceByStudentCommand;
import seedu.address.model.person.Attendance;

public class MarkAttendanceByStudentCommandParserTest {

    private MarkAttendanceByStudentCommandParser parser = new MarkAttendanceByStudentCommandParser();

    @Test
    public void parse_validArgs_returnsMarkAttendanceByStudentCommand() {
        Attendance attendance = new Attendance(LocalDate.parse("12/12/2024",
                Attendance.VALID_DATE_FORMAT));
        MarkAttendanceByStudentCommand expectedCommand =
                new MarkAttendanceByStudentCommand(INDEX_FIRST_PERSON, attendance, "Math");
        assertParseSuccess(parser, "1 attend/12/12/2024 tut/Math", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));
    }
}
