package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddPublicAddressCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPublicAddressCommand;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class AddPublicAddressCommandParserTest {
    private final AddPublicAddressCommandParser parser = new AddPublicAddressCommandParser();

    @Test
    public void parse_validArgs_returnsAddAddressCommand() {
        assertParseSuccess(
            parser,
            "1 c/BTC l/wallet1 pa/" + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            new AddPublicAddressCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder()
                    .withPublicAddress("BTC", "wallet1", VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING)
                    .build()
            )
        );

        // multiple whitespaces between preamble and prefix
        assertParseSuccess(
            parser,
            "  1    c/BTC    l/wallet1   pa/" + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            new AddPublicAddressCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder()
                    .withPublicAddress("BTC", "wallet1", VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING)
                    .build()
            )
        );
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        // negative index
        assertParseFailure(parser, "-5 pa/BTC", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, "0 pa/BTC", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 random", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ BTC", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNetwork_throwsParseException() {
        assertParseFailure(parser, "1 pa/EE", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, "pa/BTC", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        // no network specified
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        // no index and no network specified
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
