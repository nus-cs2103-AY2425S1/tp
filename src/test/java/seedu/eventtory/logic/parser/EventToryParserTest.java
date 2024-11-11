package seedu.eventtory.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventtory.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.eventtory.testutil.Assert.assertThrows;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.commands.AssignCommand;
import seedu.eventtory.logic.commands.ClearCommand;
import seedu.eventtory.logic.commands.CreateEventCommand;
import seedu.eventtory.logic.commands.CreateVendorCommand;
import seedu.eventtory.logic.commands.DeleteEventCommand;
import seedu.eventtory.logic.commands.DeleteVendorCommand;
import seedu.eventtory.logic.commands.EditEventCommand;
import seedu.eventtory.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.eventtory.logic.commands.EditVendorCommand;
import seedu.eventtory.logic.commands.EditVendorCommand.EditVendorDescriptor;
import seedu.eventtory.logic.commands.ExitCommand;
import seedu.eventtory.logic.commands.FindCommand;
import seedu.eventtory.logic.commands.FindEventCommand;
import seedu.eventtory.logic.commands.FindVendorCommand;
import seedu.eventtory.logic.commands.HelpCommand;
import seedu.eventtory.logic.commands.ListCommand;
import seedu.eventtory.logic.commands.UnassignCommand;
import seedu.eventtory.logic.commands.ViewEventCommand;
import seedu.eventtory.logic.commands.ViewVendorCommand;
import seedu.eventtory.logic.parser.exceptions.ParseException;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.event.EventContainsKeywordsPredicate;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.model.vendor.VendorContainsKeywordsPredicate;
import seedu.eventtory.testutil.EditEventDescriptorBuilder;
import seedu.eventtory.testutil.EditVendorDescriptorBuilder;
import seedu.eventtory.testutil.EventBuilder;
import seedu.eventtory.testutil.EventUtil;
import seedu.eventtory.testutil.VendorBuilder;
import seedu.eventtory.testutil.VendorUtil;

public class EventToryParserTest {

    private final EventToryParser parser = new EventToryParser();

    @Test
    public void parseCommand_assign() throws Exception {
        AssignCommand command = new AssignCommand(Index.fromOneBased(1));
        assertEquals(parser.parseCommand(AssignCommand.COMMAND_WORD + " 1"), command);
    }

    @Test
    public void parseCommand_unassign() throws Exception {
        UnassignCommand command = new UnassignCommand(Index.fromOneBased(1));
        assertEquals(parser.parseCommand(UnassignCommand.COMMAND_WORD + " 1"), command);
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
    public void parseCommand_editVendor() throws Exception {
        Vendor vendor = new VendorBuilder().build();
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(vendor).build();
        EditVendorCommand command = (EditVendorCommand) parser.parseCommand(EditVendorCommand.COMMAND_WORD + " v/"
                + INDEX_FIRST_VENDOR.getOneBased() + " " + VendorUtil.getEditVendorDescriptorDetails(descriptor));
        assertEquals(new EditVendorCommand(INDEX_FIRST_VENDOR, descriptor), command);
    }

    @Test
    public void parseCommand_editEvent() throws Exception {
        Event event = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(event).build();
        EditEventCommand command = (EditEventCommand) parser.parseCommand(EditEventCommand.COMMAND_WORD + " e/"
                + INDEX_FIRST_EVENT.getOneBased() + " " + EventUtil.getEditEventDescriptorDetails(descriptor));
        assertEquals(new EditEventCommand(INDEX_FIRST_EVENT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand firstCommand = (FindCommand) parser
                .parseCommand(FindCommand.COMMAND_WORD + " " + PREFIX_VENDOR
            + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindVendorCommand(new VendorContainsKeywordsPredicate(keywords)), firstCommand);

        FindCommand secondCommand = (FindCommand) parser
                .parseCommand(FindCommand.COMMAND_WORD + " " + PREFIX_EVENT
            + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindEventCommand(new EventContainsKeywordsPredicate(keywords)), secondCommand);
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
