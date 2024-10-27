package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditPublicAddressCommand;
import seedu.address.model.addresses.Network;

public class EditPublicAddressCommandParserTest {

    private static final String VALID_PUBLIC_ADDRESS = "14qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd";

    private final EditPublicAddressCommandParser parser = new EditPublicAddressCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_PUBLIC_ADDRESS_LABEL + "MyWallet " + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingNetwork_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_PUBLIC_ADDRESS_LABEL + "MyWallet "
                        + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingLabel_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAddress_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_PUBLIC_ADDRESS_LABEL + "MyWallet",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPublicAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNetwork_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "INVALID_NETWORK "
                        + PREFIX_PUBLIC_ADDRESS_LABEL + "MyWallet " + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS,
                Network.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validArgs_returnsEditPublicAddressCommand() {
        Index expectedIndex = Index.fromOneBased(1);
        Network expectedNetwork = Network.BTC;
        String expectedLabel = "MyWallet";

        EditPublicAddressCommand expectedCommand = new EditPublicAddressCommand(
                expectedIndex, expectedNetwork, VALID_PUBLIC_ADDRESS, expectedLabel);

        assertParseSuccess(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_PUBLIC_ADDRESS_LABEL + "MyWallet "
                        + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS,
                expectedCommand);
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                        + PREFIX_PUBLIC_ADDRESS_LABEL + "MyWallet "
                        + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS + " "
                        + PREFIX_PUBLIC_ADDRESS_NETWORK + "ETH",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PUBLIC_ADDRESS_NETWORK));
    }

}
