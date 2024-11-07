package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.RetrievePublicAddressCommand;
import seedu.address.model.addresses.Network;

public class RetrievePublicAddressCommandParserTest {

    private final RetrievePublicAddressCommandParser parser = new RetrievePublicAddressCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, " " + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_NAME + "John Doe",
                new RetrievePublicAddressCommand("mylabel", "John Doe", Network.BTC));
    }

    @Test
    public void parse_validArgsReversedOrder_success() {
        assertParseSuccess(parser, " " + PREFIX_NAME + "John Doe "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel",
                new RetrievePublicAddressCommand("mylabel", "John Doe", Network.BTC));
    }

    @Test
    public void parse_validArgsEmptyName_success() {
        assertParseSuccess(parser, " " + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_NAME,
                new RetrievePublicAddressCommand("mylabel", "", Network.BTC));
    }

    @Test
    public void parse_validArgsNoNetwork_success() {
        assertParseSuccess(parser, " " + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                        + PREFIX_NAME + "John Doe",
                new RetrievePublicAddressCommand("mylabel", "John Doe"));
    }

    @Test
    public void parse_validArgsNoName_success() {
        assertParseSuccess(parser, " " + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC ",
                new RetrievePublicAddressCommand("mylabel", "", Network.BTC));
    }

    @Test
    public void parse_validArgsLabelOnly_success() {
        assertParseSuccess(parser, " " + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel ",
                new RetrievePublicAddressCommand("mylabel", "", null));
    }

    @Test
    public void parse_validArgsWithSpaces_success() {
        assertParseSuccess(parser, " " + PREFIX_PUBLIC_ADDRESS_LABEL + "  my label  "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + " BTC  "
                        + PREFIX_NAME + " John Doe ",
                new RetrievePublicAddressCommand("my label", "John Doe", Network.BTC));
    }

    @Test
    public void parse_invalidArgsMissingLabel_failure() {
        assertParseFailure(parser, " " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_NAME + "John Doe",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RetrievePublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsEmptyLabel_failure() {
        assertParseFailure(parser, " " + PREFIX_PUBLIC_ADDRESS_LABEL + "    "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_NAME + "John Doe",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RetrievePublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsUnsupportedNetwork_failure() {
        assertParseFailure(parser, " " + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + "DOGE "
                        + PREFIX_NAME + "John Doe",
                Network.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidArgsWrongCasedNetwork_failure() {
        assertParseFailure(parser, " " + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + "btc "
                        + PREFIX_NAME + "John Doe",
                Network.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidArgsExtraPreamble_failure() {
        assertParseFailure(parser, " 1 " + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_NAME + "John Doe",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RetrievePublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsDuplicateLabel_failure() {
        assertParseFailure(parser, " " + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                        + PREFIX_PUBLIC_ADDRESS_LABEL + "otherlabel "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_NAME + "John Doe",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PUBLIC_ADDRESS_LABEL));
    }

    @Test
    public void parse_invalidArgsDuplicateNetwork_failure() {
        assertParseFailure(parser, " " + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + "ETH "
                        + PREFIX_NAME + "John Doe",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PUBLIC_ADDRESS_NETWORK));
    }

    @Test
    public void parse_invalidArgsDuplicateName_failure() {
        assertParseFailure(parser, " " + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_NAME + "John Doe "
                        + PREFIX_NAME + "John Cena",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }

}
