package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkAttendanceByTutorialCommand;
import seedu.address.model.person.Attendance;
import seedu.address.model.tutorial.Tutorial;

public class MarkAttendanceByTutorialCommandParserTest {

    private MarkAttendanceByTutorialCommandParser parser = new MarkAttendanceByTutorialCommandParser();

    @Test
    public void parse_validArgs_returnsMarkAttendanceByTutorialCommand() {
        MarkAttendanceByTutorialCommand expectedCommand = new MarkAttendanceByTutorialCommand(
                new Tutorial("Math"),
                new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)));
        assertParseSuccess(parser, " attend/12/12/2024 tut/Math", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //no arguments specified
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByTutorialCommand.MESSAGE_USAGE));

        //attendance not specified
        assertParseFailure(parser, "1 tut/Math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByTutorialCommand.MESSAGE_USAGE));

        //tutorial not specified
        assertParseFailure(parser, "1 attend/12/12/2024",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByTutorialCommand.MESSAGE_USAGE));
    }
}
