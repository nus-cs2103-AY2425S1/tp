package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindTaskCommand;

public class FindTaskCommandParserTest {

    private FindTaskCommandParser parser = new FindTaskCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // Test with empty argument
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindTaskCommand() {
        // Test with valid index argument
        FindTaskCommand expectedFindTaskCommand = new FindTaskCommand(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", expectedFindTaskCommand);

        // Test with leading/trailing spaces
        assertParseSuccess(parser, " 1 ", expectedFindTaskCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test with non-numeric argument
        assertParseFailure(parser, "abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));

        // Test with negative number
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
    }
}
