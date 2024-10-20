package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_HOUR;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.PayCommand;

public class PayCommandParserTest {

    private PayCommandParser parser = new PayCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // No index specified
        assertParseFailure(parser, "h/1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));

        // No hours specified
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));

        // No index and no hour specified
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Negative index
        assertParseFailure(parser, "-5 h/5", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));

        // Zero index
        assertParseFailure(parser, "0 h/5", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));

        // Invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));

        // Invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid hour (not a number)
        assertParseFailure(parser, "1 h/abc", MESSAGE_INVALID_HOUR);

        // Invalid hour (negative number)
        assertParseFailure(parser, "1 h/-3", MESSAGE_INVALID_HOUR);

        // Invalid hour (zero)
        assertParseFailure(parser, "1 h/0", MESSAGE_INVALID_HOUR);
    }

    @Test
    public void parse_validInput_success() {
        Index targetIndex = INDEX_FIRST_STUDENT;
        String userInput = targetIndex.getOneBased() + " h/" + 1;
        PayCommand expectedCommand = new PayCommand(targetIndex, Double.parseDouble("1"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleValidFields_failure() {
        // Multiple valid hours, only the first one should be considered
        Index targetIndex = INDEX_FIRST_STUDENT;
        String userInput = targetIndex.getOneBased() + " h/5 h/10";
        String expectedCommand = Messages.MESSAGE_DUPLICATE_FIELDS + "h/";

        assertParseFailure(parser, userInput, expectedCommand);
    }
}


