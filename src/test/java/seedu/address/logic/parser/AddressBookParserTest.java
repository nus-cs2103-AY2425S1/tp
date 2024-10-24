package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CreateEventCommand;
import seedu.address.logic.commands.CreateVendorCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteVendorCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditVendorDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UnassignCommand;
import seedu.address.logic.commands.ViewEventCommand;
import seedu.address.logic.commands.ViewVendorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.NameContainsKeywordsPredicate;
import seedu.address.model.vendor.Vendor;
import seedu.address.testutil.EditVendorDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventUtil;
import seedu.address.testutil.VendorBuilder;
import seedu.address.testutil.VendorUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_assign() throws Exception {
        AssignCommand command = new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        assertEquals(parser.parseCommand(AssignCommand.COMMAND_WORD + " v/1 e/1"), command);
    }

    @Test
    public void parseCommand_unassign() throws Exception {
        UnassignCommand command = new UnassignCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        assertEquals(parser.parseCommand(UnassignCommand.COMMAND_WORD + " v/1 e/1"), command);
    }

    @Test
    public void parseCommand_createEvent() throws Exception {
        Event event = new EventBuilder().build();
        CreateEventCommand command = (CreateEventCommand) parser.parseCommand(EventUtil.getCreateEventCommand(event));
        assertEquals(new CreateEventCommand(event), command);
    }

    @Test
    public void parseCommand_createVendor() throws Exception {
        Vendor vendor = new VendorBuilder().build();
        CreateVendorCommand command = (CreateVendorCommand) parser
                .parseCommand(VendorUtil.getCreateVendorCommand(vendor));
        assertEquals(new CreateVendorCommand(vendor), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deleteVendor() throws Exception {
        DeleteVendorCommand command = (DeleteVendorCommand) parser.parseCommand(
                DeleteVendorCommand.COMMAND_WORD + " " + PREFIX_VENDOR + INDEX_FIRST_VENDOR.getOneBased());
        assertEquals(new DeleteVendorCommand(INDEX_FIRST_VENDOR), command);
    }

    @Test
    public void parseCommand_deleteEvent() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser
                .parseCommand(DeleteEventCommand.COMMAND_WORD + " " + PREFIX_EVENT + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new DeleteEventCommand(INDEX_FIRST_EVENT), command);
    }

    @Test
    public void parseCommand_viewEvent() throws Exception {
        ViewEventCommand command = (ViewEventCommand) parser
                .parseCommand(ViewEventCommand.COMMAND_WORD + " " + PREFIX_EVENT + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new ViewEventCommand(INDEX_FIRST_EVENT), command);
    }

    @Test
    public void parseCommand_viewVendor() throws Exception {
        ViewVendorCommand command = (ViewVendorCommand) parser
                .parseCommand(ViewVendorCommand.COMMAND_WORD + " " + PREFIX_VENDOR + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new ViewVendorCommand(INDEX_FIRST_EVENT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Vendor vendor = new VendorBuilder().build();
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(vendor).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_VENDOR.getOneBased() + " " + VendorUtil.getEditVendorDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_VENDOR, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser
                .parseCommand(FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
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
