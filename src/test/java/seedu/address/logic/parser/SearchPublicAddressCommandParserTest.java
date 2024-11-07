package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_ETH_MAIN_STRING;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchPublicAddressCommand;

public class SearchPublicAddressCommandParserTest {
    private final SearchPublicAddressCommandParser parser = new SearchPublicAddressCommandParser();

    @Test
    public void parse_publicAddressSpecified_success() {
        // have public address
        String userInput = SearchPublicAddressCommand.COMMAND_WORD + " "
            + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_ETH_MAIN_STRING;
        SearchPublicAddressCommand expectedCommand = new SearchPublicAddressCommand(
            VALID_PUBLIC_ADDRESS_ETH_MAIN_STRING);
        assertParseSuccess(parser, userInput, expectedCommand);


    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SearchPublicAddressCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, SearchPublicAddressCommand.COMMAND_WORD, expectedMessage);
        // no parameters + space (white space is stripped in command's parser, this a extra precaution)
        assertParseFailure(parser, SearchPublicAddressCommand.COMMAND_WORD + " ", expectedMessage);
    }

    @Test
    public void parse_excessFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SearchPublicAddressCommand.MESSAGE_USAGE);
        String userInput = SearchPublicAddressCommand.COMMAND_WORD + " "
            + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_ETH_MAIN_STRING + " "
            + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;
        // no duplicate parameters
        assertParseFailure(parser, userInput, expectedMessage);

    }
}
