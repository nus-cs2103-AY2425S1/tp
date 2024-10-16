package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkVipCommand;

public class MarkVipCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkVipCommand.MESSAGE_USAGE);

    private MarkVipCommandParser parser = new MarkVipCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, CliSyntax.PREFIX_MARKVIP + "true", MESSAGE_INVALID_FORMAT);

        // new state not specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no new state specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }
}
