package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.MarkAttendanceByTutorialCommand;
import seedu.address.model.person.Attendance;
import seedu.address.model.tutorial.Tutorial;

public class MarkAttendanceByTutorialCommandParserTest {

    private final MarkAttendanceByTutorialCommandParser parser = new MarkAttendanceByTutorialCommandParser();

    @Test
    public void parse_validArgs_returnsMarkAttendanceByTutorialCommand() {
        Attendance attendance = new Attendance(LocalDate.parse("10/10/2024",
                Attendance.VALID_DATE_FORMAT));
        Tutorial tutorial = new Tutorial("Math");
        MarkAttendanceByTutorialCommand expectedCommand =
                new MarkAttendanceByTutorialCommand(tutorial, attendance);

        assertParseSuccess(parser, " attend/10/10/2024 tut/Math", expectedCommand);
        assertParseSuccess(parser, " tut/Math attend/10/10/2024", expectedCommand);

        // arguments specified with leading and trailing white space
        assertParseSuccess(parser, "  tut/Math attend/10/10/2024  ", expectedCommand);
        assertParseSuccess(parser, " attend/  10/10/2024   tut/Math", expectedCommand);
        assertParseSuccess(parser, " attend/10/10/2024 tut/  Math  ", expectedCommand);
    }

    @Test
    public void parse_noWhiteSpaceBetweenArgs_throwsParseException() {
        assertParseFailure(parser, "attend/10/10/2024 tut/Math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByTutorialCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "attend/10/10/2024tut/Math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_throwsParseException() {
        // no arguments specified
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByTutorialCommand.MESSAGE_USAGE));

        // attendance not specified
        assertParseFailure(parser, "tut/Math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByTutorialCommand.MESSAGE_USAGE));

        // tutorial not specified
        assertParseFailure(parser, "attend/10/10/2024",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexSpecified_throwsParseException() {
        assertParseFailure(parser, "1 attend/10/10/2024 tut/Math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefix_throwsParseException() {
        // duplicate attendance
        assertParseFailure(parser, " attend/10/10/2024 attend/10/10/2024 tut/Math",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ATTENDANCE));

        // duplicate tutorial
        assertParseFailure(parser, " attend/10/10/2024 tut/Math tut/Math",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL));

        // duplicate attendance and tutorial
        assertParseFailure(parser, " attend/10/10/2024 attend/10/10/2024 tut/Math tut/Math",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ATTENDANCE, PREFIX_TUTORIAL));
    }

    @Test
    public void parse_invalidAttendance_throwsParseException() {
        // white spaces for attendance field
        assertParseFailure(parser, " attend/   tut/Math", Attendance.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, " attend/10/10/24 tut/Math", Attendance.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " attend/10-10-2024 tut/Math", Attendance.MESSAGE_CONSTRAINTS);

        // future date as input
        String tomorrowDate = LocalDate.now().plusDays(1).format(Attendance.VALID_DATE_FORMAT);
        assertParseFailure(parser, " attend/" + tomorrowDate + " tut/Math", Attendance.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidTutorial_throwsParseException() {
        // white spaces for tutorial field
        assertParseFailure(parser, " attend/10/10/2024 tut/  ", Tutorial.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, " attend/10/10/2024 tut/@Math", Tutorial.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " attend/10/10/2024 tut/-Math", Tutorial.MESSAGE_CONSTRAINTS);
    }
}
