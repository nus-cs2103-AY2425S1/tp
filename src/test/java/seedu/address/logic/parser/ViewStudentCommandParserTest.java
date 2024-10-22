package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DIDDY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStudents.DIDDY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewStudentCommand;

public class ViewStudentCommandParserTest {

    private ViewStudentCommandParser parser = new ViewStudentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStudentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewStudentCommand() {
        // no leading and trailing whitespaces
        ViewStudentCommand expectedViewStudentCommand = new ViewStudentCommand(DIDDY.getName());
        assertParseSuccess(parser, VALID_NAME_DIDDY, expectedViewStudentCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Puff Daddy  \t", expectedViewStudentCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "$",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStudentCommand.MESSAGE_USAGE));
    }
}
