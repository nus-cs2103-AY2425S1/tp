package seedu.address.logic.parser;

import static seedu.address.logic.commands.FilterByNetworkCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.FilterByNetworkCommandParser.MESSAGE_EMPTY_NETWORK;
import static seedu.address.logic.parser.FilterByNetworkCommandParser.MESSAGE_EMPTY_OR_WRONG_PREFIX;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterByNetworkCommand;
import seedu.address.model.addresses.Network;


public class FilterByNetworkCommandParserTest {

    private FilterByNetworkCommandParser parser = new FilterByNetworkCommandParser();

    @Test
    public void parse_validNetwork_returnsFilterByNetworkCommand() {
        assertParseSuccess(
                parser,
                " c/BTC",
                new FilterByNetworkCommand(Network.BTC));

        assertParseSuccess(
                parser,
                "    c/    BTC   ",
                new FilterByNetworkCommand(Network.BTC));

        assertParseSuccess(
                parser,
                " c/ETH",
                new FilterByNetworkCommand(Network.ETH));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
                parser,
                "     ",
                String.format(MESSAGE_EMPTY_NETWORK, MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongPrefix_throwsParseException() {
        assertParseFailure(
                parser,
                " x/BTC",
                String.format(MESSAGE_EMPTY_OR_WRONG_PREFIX, PREFIX_PUBLIC_ADDRESS_NETWORK, MESSAGE_USAGE));
    }

    @Test
    public void parse_missingNetworkPrefix_throwsParseException() {
        assertParseFailure(
                parser,
                "BTC",
                String.format(MESSAGE_EMPTY_OR_WRONG_PREFIX, PREFIX_PUBLIC_ADDRESS_NETWORK, MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleNetworkPrefixes_throwsParseException() {
        assertParseFailure(
                parser,
                " c/BTC c/ETH",
                String.format(FilterByNetworkCommandParser.MESSAGE_TOO_MANY_NETWORKS, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNetwork_throwsParseException() {
        assertParseFailure(
                parser,
                " c/RANDOM",
                FilterByNetworkCommandParser.MESSAGE_INVALID_NETWORKS);
    }

}
