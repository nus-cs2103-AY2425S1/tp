package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddGuestCommand;
import seedu.address.logic.commands.AddVendorCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteGuestCommand;
import seedu.address.logic.commands.DeleteVendorCommand;
import seedu.address.logic.commands.EditGuestCommand;
import seedu.address.logic.commands.EditVendorCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.util.EditGuestDescriptor;
import seedu.address.logic.commands.util.EditVendorDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Vendor;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditGuestDescriptorBuilder;
import seedu.address.testutil.EditVendorDescriptorBuilder;
import seedu.address.testutil.GuestBuilder;
import seedu.address.testutil.GuestUtil;
import seedu.address.testutil.VendorBuilder;
import seedu.address.testutil.VendorUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add_guest() throws Exception {
        Guest guest = new GuestBuilder().build();
        AddGuestCommand command = (AddGuestCommand) parser.parseCommand(GuestUtil.getAddGuestCommand(guest));
        assertEquals(new AddGuestCommand(guest), command);
    }

    @Test
    public void parseCommand_add_vendor() throws Exception {
        Vendor vendor = new VendorBuilder().build();
        AddVendorCommand command = (AddVendorCommand) parser.parseCommand(VendorUtil.getAddVendorCommand(vendor));
        assertEquals(new AddVendorCommand(vendor), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deleteGuest() throws Exception {
        DeleteGuestCommand command = (DeleteGuestCommand) parser.parseCommand(
                DeleteGuestCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteGuestCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteVendor() throws Exception {
        DeleteVendorCommand command = (DeleteVendorCommand) parser.parseCommand(
                DeleteVendorCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteVendorCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_editGuest() throws Exception {
        Guest guest = new GuestBuilder().build();
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder(guest).build();
        EditGuestCommand command = (EditGuestCommand) parser.parseCommand(EditGuestCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + GuestUtil.getEditGuestDescriptorDetails(descriptor));
        assertEquals(new EditGuestCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editVendor() throws Exception {
        Vendor vendor = new VendorBuilder().build();
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(vendor).build();
        EditVendorCommand command = (EditVendorCommand) parser.parseCommand(
                EditVendorCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + VendorUtil.getEditVendorDescriptorDetails(descriptor));
        assertEquals(new EditVendorCommand(INDEX_FIRST_PERSON, descriptor), command);
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
                FindCommand.COMMAND_WORD + " " + PREFIX_NAME + keywords.stream().collect(Collectors.joining(" ")));
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
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
