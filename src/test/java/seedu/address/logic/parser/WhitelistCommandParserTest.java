package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_STATUS_ACTIVE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.WhitelistCommand;
import seedu.address.logic.commands.WhitelistListCommand;
import seedu.address.model.person.ClientStatus;

public class WhitelistCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhitelistCommand.MESSAGE_USAGE);
    private WhitelistCommandParser parser = new WhitelistCommandParser();

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "2 ps/old", MESSAGE_INVALID_FORMAT);

        // double indexes
        assertParseFailure(parser, "1 2", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validIndexAndValidClientStatus_success() {
        // test each of the valid client statuses
        assertParseSuccess(parser, "1 cs/active",
                new WhitelistCommand(INDEX_FIRST_PERSON, new ClientStatus(VALID_CLIENT_STATUS_ACTIVE)));

        assertParseSuccess(parser, "1 cs/old",
                new WhitelistCommand(INDEX_FIRST_PERSON, new ClientStatus(VALID_CLIENT_STATUS_ACTIVE)));

        assertParseSuccess(parser, "1 cs/unresponsive",
                new WhitelistCommand(INDEX_FIRST_PERSON, new ClientStatus(VALID_CLIENT_STATUS_ACTIVE)));

        assertParseSuccess(parser, "1 cs/potential",
                new WhitelistCommand(INDEX_FIRST_PERSON, new ClientStatus(VALID_CLIENT_STATUS_ACTIVE)));
    }

    @Test
    public void parse_noParams_success() {
        assertParseSuccess(parser, "", new WhitelistListCommand());
    }

    @Test
    public void parse_validIndexAndInvalidClientStatus_failure() {
        // wrong client status
        assertParseFailure(parser, "1 cs/hello world", ClientStatus.MESSAGE_CONSTRAINTS);

        // should not be able to set client as blacklisted using whitelist command!
        assertParseFailure(parser, "1 cs/blacklisted", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_noIndexWithValidClientStatus_failure() {
        // test each valid client status but no index
        assertParseFailure(parser, "cs/old", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "cs/active", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "cs/unresponsive", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "cs/potential", MESSAGE_INVALID_FORMAT);
    }
}
