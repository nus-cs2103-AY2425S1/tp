package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_LOG_MESSAGE_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOG_DATE;
import static seedu.address.logic.commands.CommandTestUtil.LOG_MESSAGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LogCommand;

/**
 * Test cases for LogCommandParser.
 */
public class LogCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            LogCommand.MESSAGE_USAGE);

    private LogCommandParser parser = new LogCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_LOG_MESSAGE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + LOG_MESSAGE_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + LOG_MESSAGE_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, "1" + INVALID_LOG_DATE + LOG_MESSAGE_DESC,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyLogMessage_failure() {
        // index specified but log message is empty
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_LOG;
        assertParseFailure(parser, userInput, MESSAGE_LOG_MESSAGE_EMPTY);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DATE
                + VALID_LOG_DATE + " " + PREFIX_LOG + VALID_LOG_MESSAGE;

        LogCommand expectedCommand = new LogCommand(targetIndex, LocalDate.parse(VALID_LOG_DATE), VALID_LOG_MESSAGE);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_onlyLogSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_LOG + VALID_LOG_MESSAGE;

        LogCommand expectedCommand = new LogCommand(targetIndex, VALID_LOG_MESSAGE);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no date specified, only log message
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_LOG + VALID_LOG_MESSAGE;

        LogCommand expectedCommand = new LogCommand(targetIndex, VALID_LOG_MESSAGE);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
