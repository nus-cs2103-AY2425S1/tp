package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignContactToWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AssignContactToWeddingCommandParserTest {

    private final AssignContactToWeddingCommandParser parser = new AssignContactToWeddingCommandParser();

    @Test
    public void parse_validArgs_success() throws ParseException {
        String userInput = "1 c/1 2";
        AssignContactToWeddingCommand actualCommand = parser.parse(userInput);
        assertNotNull(actualCommand);
    }
    @Test
    public void parse_invalidWeddingIndex_failure() {
        String userInput = "abc c/1 2";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignContactToWeddingCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_noContactPrefix_failure() {
        String userInput = "1 ";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignContactToWeddingCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_duplicateContactPrefix_failure() {
        String userInput = "1 c/1 c/2";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Please only include one prefix c/ !");
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
