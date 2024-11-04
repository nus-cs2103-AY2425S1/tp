package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BlacklistCommand;
import seedu.address.logic.commands.BlacklistListCommand;

public class BlacklistCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlacklistCommand.MESSAGE_USAGE);

    private BlacklistCommandParser parser = new BlacklistCommandParser();

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);

        // prefix is valid, but not needed for the command
        assertParseFailure(parser, "2 cs/old", MESSAGE_INVALID_FORMAT);

        // double indexes
        assertParseFailure(parser, "1 2", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validIndex_success() {
        assertParseSuccess(parser, "1", new BlacklistCommand(INDEX_FIRST_PERSON));

        assertParseSuccess(parser, "2", new BlacklistCommand(INDEX_SECOND_PERSON));

        assertParseSuccess(parser, "10", new BlacklistCommand(Index.fromOneBased(10)));
    }

    @Test
    public void parse_noParams_success() {
        assertParseSuccess(parser, "", new BlacklistListCommand());
    }
}
