package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.RetrievePublicAddressCommand;
import seedu.address.model.addresses.Network;

public class RetrievePublicAddressCommandParserTest {

    private RetrievePublicAddressCommandParser parser = new RetrievePublicAddressCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RetrievePublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefixPublicAddress_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RetrievePublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RetrievePublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNetworkWithoutTag_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "INVALID_NETWORK",
                Network.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidNetworkWithTag_throwsParseException() {
        assertParseFailure(parser,
                "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "INVALID_NETWORK" + PREFIX_PUBLIC_ADDRESS_LABEL + "MyWallet",
                Network.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validArgsWithoutTag_returnsRetrievePublicAddressCommand() {
        Index expectedIndex = Index.fromOneBased(1);
        Network expectedNetwork = Network.BTC;
        RetrievePublicAddressCommand expectedCommand = new RetrievePublicAddressCommand(expectedIndex, expectedNetwork);

        assertParseSuccess(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC", expectedCommand);
    }

    @Test
    public void parse_validArgsWithTag_returnsRetrievePublicAddressCommand() {
        Index expectedIndex = Index.fromOneBased(1);
        Network expectedNetwork = Network.BTC;
        String expectedLabel = "MyWallet";
        RetrievePublicAddressCommand expectedCommand =
                new RetrievePublicAddressCommand(expectedIndex, expectedNetwork, expectedLabel);

        assertParseSuccess(parser,
                "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC " + PREFIX_PUBLIC_ADDRESS_LABEL + "MyWallet",
                expectedCommand);
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser,
                "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC " + PREFIX_PUBLIC_ADDRESS_NETWORK + "ETH",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PUBLIC_ADDRESS_NETWORK));
    }

}
