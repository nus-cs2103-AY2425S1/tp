package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOUR_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUR;
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
        // EP: missing parts
        // No index specified
        assertParseFailure(parser, VALID_HOUR_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));

        // No hours specified
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));

        // No index and no hour specified
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // EP: invalid input
        // Negative index
        assertParseFailure(parser, "-5" + VALID_HOUR_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));

        // Zero index
        assertParseFailure(parser, "0" + VALID_HOUR_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));

        // Invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));

        // Invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // EP: invalid value
        // Invalid hour (not a number)
        assertParseFailure(parser, "1" + INVALID_HOUR_DESC, MESSAGE_INVALID_HOUR);

        // Invalid hour (negative number)
        assertParseFailure(parser, "1 " + PREFIX_HOUR + "-3", MESSAGE_INVALID_HOUR);

        // Invalid hour (zero)
        assertParseFailure(parser, "1 " + PREFIX_HOUR + "0", MESSAGE_INVALID_HOUR);
    }

    @Test
    public void parse_validInput_success() {
        Index targetIndex = INDEX_FIRST_STUDENT;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_HOUR + 1;
        PayCommand expectedCommand = new PayCommand(targetIndex, Double.parseDouble("1"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleValidFields_failure() {
        Index targetIndex = INDEX_FIRST_STUDENT;
        String userInput = targetIndex.getOneBased() + VALID_HOUR_DESC + VALID_HOUR_DESC;
        String expectedCommand = Messages.MESSAGE_DUPLICATE_FIELDS + PREFIX_HOUR;

        assertParseFailure(parser, userInput, expectedCommand);
    }
}


