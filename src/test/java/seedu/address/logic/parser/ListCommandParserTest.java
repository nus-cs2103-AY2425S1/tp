package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_KEY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_KEY_BUYERS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_KEY_CLIENTS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_KEY_MEETINGS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_KEY_PROPERTIES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_KEY_SELLERS_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListBuyersCommand;
import seedu.address.logic.commands.ListClientsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListMeetingsCommand;
import seedu.address.logic.commands.ListPropertiesCommand;
import seedu.address.logic.commands.ListSellersCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validKey_success() {
        // normal input with valid keys
        assertParseSuccess(parser, VALID_KEY_CLIENTS_DESC, new ListClientsCommand());
        assertParseSuccess(parser, VALID_KEY_BUYERS_DESC, new ListBuyersCommand());
        assertParseSuccess(parser, VALID_KEY_SELLERS_DESC, new ListSellersCommand());
        assertParseSuccess(parser, VALID_KEY_PROPERTIES_DESC, new ListPropertiesCommand());
        assertParseSuccess(parser, VALID_KEY_MEETINGS_DESC, new ListMeetingsCommand());
    }


    @Test
    public void parse_missingKey_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE);

        // missing key prefix
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidKey_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE);

        // invalid key
        assertParseFailure(parser, INVALID_KEY_DESC, expectedMessage);
    }
}
