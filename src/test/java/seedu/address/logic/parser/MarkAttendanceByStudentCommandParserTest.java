package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkAttendanceByStudentCommand;
import seedu.address.model.person.Attendance;
import seedu.address.model.tutorial.Tutorial;

public class MarkAttendanceByStudentCommandParserTest {

    private MarkAttendanceByStudentCommandParser parser = new MarkAttendanceByStudentCommandParser();

    @Test
    public void parse_validArgs_returnsMarkAttendanceByStudentCommand() {
        Attendance attendance = new Attendance(LocalDate.parse("12/12/2024",
                Attendance.VALID_DATE_FORMAT));
        MarkAttendanceByStudentCommand expectedCommand =
                new MarkAttendanceByStudentCommand(INDEX_FIRST_PERSON, attendance, new Tutorial("Math"));

        assertParseSuccess(parser, "1 attend/12/12/2024 tut/Math", expectedCommand);
        assertParseSuccess(parser, "1 tut/Math attend/12/12/2024", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //no arguments specified
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));

        //index not specified
        assertParseFailure(parser, "attend/12/12/2024 tut/Math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));

        //attendance not specified
        assertParseFailure(parser, "1 tut/Math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));

        //tutorial not specified
        assertParseFailure(parser, "1 attend/12/12/2024",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));
    }
}
