package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewStudentCommand;

public class ViewStudentCommandParserTest {

    private final ViewStudentCommandParser parser = new ViewStudentCommandParser();

    @Test
    public void parse_validArgs_returnsViewStudentCommand() {
        String userInput = String.valueOf(INDEX_FIRST_STUDENT.getOneBased());
        assertParseSuccess(parser, userInput, new ViewStudentCommand(INDEX_FIRST_STUDENT));
    }

    @Test
    public void parse_missingArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewStudentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // EP: Input is a non-numeric string
        assertParseFailure(parser, "a", ParserUtil.MESSAGE_INVALID_INDEX);
        // EP: Input contains special characters
        assertParseFailure(parser, "(!12", ParserUtil.MESSAGE_INVALID_INDEX);
        // EP: Input is a floating-point number
        assertParseFailure(parser, "0.5", ParserUtil.MESSAGE_INVALID_INDEX);
        // EP: Input is a negative number
        assertParseFailure(parser, "-1", ParserUtil.MESSAGE_INVALID_INDEX);
        // EP: Input is zero, which is not a valid index
        assertParseFailure(parser, "0", ParserUtil.MESSAGE_INVALID_INDEX);
    }
}
