package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
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

        // arguments specified with leading and trailing white space
        assertParseSuccess(parser, "  1   tut/Math attend/12/12/2024", expectedCommand);
        assertParseSuccess(parser, "1 attend/  12/12/2024   tut/Math", expectedCommand);
        assertParseSuccess(parser, "1 attend/12/12/2024 tut/  Math  ", expectedCommand);
    }

    @Test
    public void parse_noWhiteSpaceBetweenArgs_throwsParseException() {
        assertParseFailure(parser, "1attend/12/12/2024 tut/Math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 attend/12/12/2024tut/Math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1attend/12/12/2024tut/Math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_throwsParseException() {
        // no arguments specified
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));

        // index not specified
        assertParseFailure(parser, "attend/12/12/2024 tut/Math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));

        // attendance not specified
        assertParseFailure(parser, "1 tut/Math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));

        // tutorial not specified
        assertParseFailure(parser, "1 attend/12/12/2024",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefix_throwsParseException() {
        // duplicate attendance
        assertParseFailure(parser, "1 attend/12/12/2024 attend/12/12/2024 tut/Math",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ATTENDANCE));

        // duplicate tutorial
        assertParseFailure(parser, "1 attend/12/12/2024 tut/Math tut/Math",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL));

        // duplicate attendance and tutorial
        assertParseFailure(parser, "1 attend/12/12/2024 attend/12/12/2024 tut/Math tut/Math",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ATTENDANCE, PREFIX_TUTORIAL));
    }

    @Test
    public void parse_invalidAttendance_throwsParseException() {
        // white spaces for attendance field
        assertParseFailure(parser, "1 attend/   tut/Math", Attendance.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1 attend/12/12/24 tut/Math", Attendance.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 attend/12-12-2024 tut/@Math", Attendance.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidTutorial_throwsParseException() {
        // white spaces for tutorial field
        assertParseFailure(parser, "1 attend/12/12/2024 tut/  ", Tutorial.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1 attend/12/12/2024 tut/@Math", Tutorial.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 attend/12/12/2024 tut/-Math", Tutorial.MESSAGE_CONSTRAINTS);
    }
}
