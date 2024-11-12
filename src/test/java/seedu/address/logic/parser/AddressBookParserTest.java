package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AbstractEditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPublicAddressCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditPublicAddressCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SearchPublicAddressCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.addresses.BtcAddress;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddressesComposition;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private static final String VALID_PUBLIC_ADDRESS = VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addPublicAddress() throws Exception {
        final String publicAddress = VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;
        AddPublicAddressCommand command = (AddPublicAddressCommand) parser.parseCommand(
            AddPublicAddressCommand.COMMAND_WORD + " "
                + "1 "
                + PREFIX_PUBLIC_ADDRESS + publicAddress + " "
                + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
                + PREFIX_PUBLIC_ADDRESS_LABEL + "wallet1");

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        editPersonDescriptor.setPublicAddresses(new PublicAddressesComposition(Map.of(Network.BTC,
            Set.of(new BtcAddress(publicAddress, "wallet1")))));

        assertEquals(new AddPublicAddressCommand(INDEX_FIRST_PERSON, editPersonDescriptor), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
            DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
            + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_searchPublicAddress() throws Exception {
        final String publicAddress = "23423sa";
        SearchPublicAddressCommand command = (SearchPublicAddressCommand) parser.parseCommand(
            SearchPublicAddressCommand.COMMAND_WORD + " "
                + PREFIX_PUBLIC_ADDRESS + publicAddress);
        assertEquals(new SearchPublicAddressCommand(publicAddress), command);
    }

    @Test
    public void parseCommand_editPublicAddress() throws Exception {
        EditPublicAddressCommand command = (EditPublicAddressCommand) parser.parseCommand(
            EditPublicAddressCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC " + PREFIX_PUBLIC_ADDRESS_LABEL + "label "
                + PREFIX_PUBLIC_ADDRESS + VALID_PUBLIC_ADDRESS);
        assertEquals(new EditPublicAddressCommand(INDEX_FIRST_PERSON, new BtcAddress(VALID_PUBLIC_ADDRESS, "label")),
            command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
