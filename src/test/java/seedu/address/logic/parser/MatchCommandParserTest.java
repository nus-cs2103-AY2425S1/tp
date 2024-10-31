package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.MatchCommand;

public class MatchCommandParserTest {
    private final MatchCommandParser parser = new MatchCommandParser();

    @Test
    public void parse_correctFormat_success() {
        String userInput = "1 1";
        Index contactIndex = Index.fromOneBased(1);
        Index jobIndex = Index.fromOneBased(1);

        assertParseSuccess(parser, userInput, new MatchCommand(contactIndex, jobIndex));
    }

    @Test
    public void parse_incorrectFormat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE);

        // Too few arguments
        assertParseFailure(parser, "1", expectedMessage);

        // Too many arguments
        assertParseFailure(parser, "1 1 1", expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = Messages.MESSAGE_INVALID_DISPLAYED_INDEX;

        // Arguments not number
        assertParseFailure(parser, "one one", expectedMessage);

        // Arguments not positive integer
        assertParseFailure(parser, "0 1", expectedMessage);
        assertParseFailure(parser, "-1 1", expectedMessage);
    }
}
