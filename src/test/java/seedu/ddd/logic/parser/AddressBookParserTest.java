package seedu.ddd.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ddd.logic.Messages.getErrorMessageForExclusiveFlags;
import static seedu.ddd.logic.commands.CommandTestUtil.CLIENT_FLAG;
import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_VENDOR;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_SINGLE;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_DATE;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_DESCRIPTION;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_NAME;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_SINGLE;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.commands.ClearCommand;
import seedu.ddd.logic.commands.DeleteCommand;
import seedu.ddd.logic.commands.EditCommand;
import seedu.ddd.logic.commands.EditContactCommand;
import seedu.ddd.logic.commands.EditContactCommand.EditContactDescriptor;
import seedu.ddd.logic.commands.ExitCommand;
import seedu.ddd.logic.commands.HelpCommand;
import seedu.ddd.logic.commands.add.AddContactCommand;
import seedu.ddd.logic.commands.add.AddEventCommand;
import seedu.ddd.logic.commands.list.ListCommand;
import seedu.ddd.logic.commands.list.ListContactCommand;
import seedu.ddd.logic.commands.list.ListEventCommand;
import seedu.ddd.logic.parser.add.AddressBookParser;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.testutil.EditContactDescriptorBuilder;
import seedu.ddd.testutil.contact.ClientBuilder;
import seedu.ddd.testutil.contact.ClientUtil;
import seedu.ddd.testutil.contact.ContactUtil;
import seedu.ddd.testutil.contact.VendorBuilder;
import seedu.ddd.testutil.contact.VendorUtil;
import seedu.ddd.testutil.event.EventBuilder;
import seedu.ddd.testutil.event.EventUtil;

public class AddressBookParserTest {
    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_addClient() throws Exception {
        Client client = new ClientBuilder().build();
        AddContactCommand command = (AddContactCommand) parser.parseCommand(ClientUtil.getAddContactCommand(client));
        assertEquals(new AddContactCommand(client), command);
    }

    @Test
    public void parseCommand_addVendor() throws Exception {
        Vendor vendor = new VendorBuilder().build();
        AddContactCommand command = (AddContactCommand) parser.parseCommand(VendorUtil.getAddContactCommand(vendor));
        assertEquals(new AddContactCommand(vendor), command);
    }

    @Test
    public void parseCommand_addEvent() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(EventUtil.getAddEventCommand(event));
        assertEquals(new AddEventCommand(DEFAULT_EVENT_NAME, DEFAULT_EVENT_DESCRIPTION,
                DEFAULT_EVENT_DATE, DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_SINGLE,
                DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_SINGLE, DEFAULT_EVENT_ID), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_CONTACT.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_CONTACT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Contact contact = new ClientBuilder().build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(contact).build();
        // EditCommand does not take an id parameter, so id is null in EditCommandDescriptor
        descriptor.setId(null);
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_CONTACT.getOneBased() + " " + ContactUtil.getEditContactDescriptorDetails(descriptor));
        assertEquals(new EditContactCommand(INDEX_FIRST_CONTACT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }
    /*
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }
    */

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " -c") instanceof ListContactCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " -v") instanceof ListContactCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " -e") instanceof ListEventCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
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

    @Test
    public void parseCommand_multipleFlagsAddCommand_throwsParseException() {
        Vendor vendor = new VendorBuilder().build();
        String command = VendorUtil.getAddContactCommand(vendor) + " " + CLIENT_FLAG;
        assertThrows(ParseException.class,
                getErrorMessageForExclusiveFlags(FLAG_CLIENT, FLAG_VENDOR), () -> parser.parseCommand(command));
    }
}
