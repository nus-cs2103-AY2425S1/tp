package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_SUB_STRING;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditPublicAddressCommand;
import seedu.address.model.addresses.BtcAddress;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;

public class EditPublicAddressCommandParserTest {

    private final EditPublicAddressCommandParser parser = new EditPublicAddressCommandParser();

    // Valid
    @Test
    public void parse_validArgs_success() {
        Index index = Index.fromOneBased(1);
        PublicAddress publicAddress = new BtcAddress(
            VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING, "mylabel");

        assertParseSuccess(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            new EditPublicAddressCommand(index, publicAddress));
    }

    @Test
    public void parse_validArgsReversedOrder_success() {
        Index index = Index.fromOneBased(1);
        PublicAddress publicAddress = new BtcAddress(
            VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING, "mylabel");

        assertParseSuccess(parser, "1 " + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING + " "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC",
            new EditPublicAddressCommand(index, publicAddress));
    }

    @Test
    public void parse_validArgsWithSpaces_success() {
        Index index = Index.fromOneBased(1);
        PublicAddress publicAddress = new BtcAddress(
            VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING, "mylabel");

        assertParseSuccess(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + " BTC  "
                + PREFIX_PUBLIC_ADDRESS_LABEL + " mylabel  "
                + PREFIX_PUBLIC_ADDRESS + " " + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING + " ",
            new EditPublicAddressCommand(index, publicAddress));
    }

    // Missing arguments
    @Test
    public void parse_noArgs_failure() {
        assertParseFailure(parser, " ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingNetwork_failure() {
        assertParseFailure(parser, "1 "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditPublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingLabel_failure() {
        assertParseFailure(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditPublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPublicAddress_failure() {
        assertParseFailure(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditPublicAddressCommand.MESSAGE_USAGE));
    }

    // Duplicate arguments
    @Test
    public void parse_duplicateNetwork_failure() {
        assertParseFailure(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                + PREFIX_PUBLIC_ADDRESS_NETWORK + "ETH "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            Messages.getErrorMessageForDuplicatePrefixes(
                PREFIX_PUBLIC_ADDRESS_NETWORK));
    }

    @Test
    public void parse_duplicateLabel_failure() {
        assertParseFailure(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "otherlabel "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PUBLIC_ADDRESS_LABEL));
    }

    @Test
    public void parse_duplicatePublicAddress_failure() {
        assertParseFailure(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                + PREFIX_PUBLIC_ADDRESS_NETWORK + "ETH "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_SUB_STRING,
            Messages.getErrorMessageForDuplicatePrefixes(
                PREFIX_PUBLIC_ADDRESS_NETWORK));
    }

    // Invalid arguments
    @Test
    public void parse_invalidIndexNonNumeric_failure() {
        assertParseFailure(parser, "a " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndexMultiple_failure() {
        assertParseFailure(parser, "1 1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndexNegative_failure() {
        assertParseFailure(parser, "-1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNetworkUnsupported_failure() {
        assertParseFailure(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "DOGE "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            Network.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidNetworkWrongCase_success() {
        assertParseSuccess(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "btc "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            new EditPublicAddressCommand(Index.fromOneBased(1),
                new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING, "mylabel")));
    }

    @Test
    public void parse_invalidNetworkEmpty_failure() {
        assertParseFailure(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + " "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "mylabel "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING,
            Network.MESSAGE_CONSTRAINTS);
    }

    // TODO: Invalid public address
}
