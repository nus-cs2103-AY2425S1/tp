package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DeletePublicAddressCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeletePublicAddressCommand;
import seedu.address.model.addresses.Network;

public class DeletePublicAddressCommandParserTest {
    private DeletePublicAddressCommandParser parser = new DeletePublicAddressCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteAddressCommand() {
        assertParseSuccess(
                parser,
                "1 pa/BTC",
                new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.BTC)
        );

        // multiple whitespaces between preamble and prefix
        assertParseSuccess(
                parser,
                "  1   pa/BTC",
                new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.BTC)
        );

        // with label
        assertParseSuccess(
                parser,
                "1 pa/BTC l/default",
                new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.BTC, "default")
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
