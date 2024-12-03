package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Name;

public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validName_returnsDeleteCommand() {
        String userInput = "Alex Yeoh";
        DeleteCommand expectedCommand = new DeleteCommand(new Name(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validIndex_returnsDeleteCommand() {
        String userInput = "1";
        DeleteCommand expectedCommand = new DeleteCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidName_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "John @ Doe", expectedMessage);
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

    @Test
    public void parse_edgeCaseName_returnsDeleteCommand() {
        String userInput = "John Doe"; // Valid name case
        DeleteCommand expectedCommand = new DeleteCommand(new Name(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
