package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkVipCommand;

public class MarkVipCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkVipCommand.MESSAGE_USAGE);

    private MarkVipCommandParser parser = new MarkVipCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, "true", MESSAGE_INVALID_FORMAT);

        // new state not specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no new state specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_failure() {
        // negative index
        assertParseFailure(parser, "-5 false", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 true", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // prefix detected in args
        assertParseFailure(parser, "1 i/false", MESSAGE_INVALID_FORMAT);

        // too many indices
        assertParseFailure(parser, "2 4 true", MESSAGE_INVALID_FORMAT);

        // too many booleans
        assertParseFailure(parser, "2 false true", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_success() {
        assertParseSuccess(parser, "3 true", new MarkVipCommand(Index.fromOneBased(3), true));
        assertParseSuccess(parser, "1 false", new MarkVipCommand(Index.fromOneBased(1), false));
    }
}
