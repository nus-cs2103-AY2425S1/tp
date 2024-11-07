package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOUR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOUR_DESC_MIXED_CASED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.OweCommand;

public class OweCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, OweCommand.MESSAGE_USAGE);

    private OweCommandParser parser = new OweCommandParser();

    @Test
    public void parse_validInput_success() {
        Index targetIndex = INDEX_FIRST_STUDENT;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_HOUR + 1;
        OweCommand expectedCommand = new OweCommand(targetIndex, Double.parseDouble("1"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // EP: missing parts
        // no index specified
        assertParseFailure(parser, VALID_HOUR_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no value specified
        String userInput = "1" + PREFIX_HOUR;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // EP: invalid input
        // negative index
        assertParseFailure(parser, "-5" + VALID_HOUR_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_HOUR_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 n/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidHour_failure() {
        assertParseFailure(parser, "1" + INVALID_HOUR_DESC, ParserUtil.MESSAGE_INVALID_HOUR);
    }

    @Test
    public void parse_validHourMixedCase_success() {
        Index targetIndex = INDEX_FIRST_STUDENT;
        OweCommand expectedCommand = new OweCommand(targetIndex, Double.parseDouble(VALID_HOUR_AMY));
        assertParseSuccess(parser, "1" + VALID_HOUR_DESC_MIXED_CASED, expectedCommand);
    }
}
