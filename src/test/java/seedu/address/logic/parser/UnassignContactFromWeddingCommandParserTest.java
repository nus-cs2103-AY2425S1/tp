package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnassignContactFromWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UnassignContactFromWeddingCommandParserTest {

    private final UnassignContactFromWeddingCommandParser parser = new UnassignContactFromWeddingCommandParser();

    @Test
    public void parse_validArgs_success() throws ParseException {
        String userInput = "1 c/1 2";
        UnassignContactFromWeddingCommand actualCommand = parser.parse(userInput);
        assertNotNull(actualCommand); // Check that the command object is not null
    }

    @Test
    public void parse_invalidWeddingIndex_failure() {
        String userInput = "abc c/1 2";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnassignContactFromWeddingCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_noContactPrefix_failure() {
        String userInput = "1 ";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnassignContactFromWeddingCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_duplicateContactPrefix_failure() {
        String userInput = "1 c/1 c/2";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                "Please only include one prefix c/ !");
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
