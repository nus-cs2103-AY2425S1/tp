package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkAttendanceByStudentCommand;

public class MarkAttendanceCommandParserTest {

    private MarkAttendanceByStudentCommandParser parser = new MarkAttendanceByStudentCommandParser();

    @Test
    public void parse_validArgs_returnsMarkAttendanceCommand() {
        assertParseSuccess(parser, "1", new MarkAttendanceByStudentCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));
    }
}
