package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.StarCommand;
import seedu.address.model.person.Name;

public class StarCommandParserTest {
    private StarCommandParser parser = new StarCommandParser();

    @Test
    public void parse_validName_returnsStarCommand() {
        String userInput = "Alex Yeoh";
        StarCommand expectedCommand = new StarCommand(new Name(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validIndex_returnsStarCommand() {
        String userInput = "1"; // Assuming 1 is a valid index
        StarCommand expectedCommand = new StarCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StarCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidName_throwsParseException() {
        // Assuming names cannot contain special characters, like '@'
        assertParseFailure(parser, "John @ Doe",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StarCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Test with a negative index
        assertParseFailure(parser, "-1",
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // Test with zero index
        assertParseFailure(parser, "0",
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }
}
