package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnstarCommand;
import seedu.address.model.person.Name;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class UnstarCommandParserTest {
    private UnstarCommandParser parser = new UnstarCommandParser();

    @Test
    public void parse_validName_returnsUnstarCommand() {
        String userInput = "Alex Yeoh";
        UnstarCommand expectedCommand = new UnstarCommand(new Name(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validIndex_returnsUnstarCommand() {
        String userInput = "1"; // Assuming 1 is a valid index
        UnstarCommand expectedCommand = new UnstarCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnstarCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidName_throwsParseException() {
        // Assuming names cannot contain special characters, like '@'
        assertParseFailure(parser, "John @ Doe",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnstarCommand.MESSAGE_USAGE));
    }
}

