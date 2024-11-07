package seedu.eventfulnus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventfulnus.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventfulnus.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.eventfulnus.testutil.Assert.assertThrows;
import static seedu.eventfulnus.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.eventfulnus.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.logic.commands.AddCommand;
import seedu.eventfulnus.logic.commands.AddEventCommand;
import seedu.eventfulnus.logic.commands.ClearCommand;
import seedu.eventfulnus.logic.commands.DeleteCommand;
import seedu.eventfulnus.logic.commands.EditCommand;
import seedu.eventfulnus.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.eventfulnus.logic.commands.EditEventCommand;
import seedu.eventfulnus.logic.commands.ExitCommand;
import seedu.eventfulnus.logic.commands.FindCommand;
import seedu.eventfulnus.logic.commands.FindEventCommand;
import seedu.eventfulnus.logic.commands.HelpCommand;
import seedu.eventfulnus.logic.commands.ListCommand;
import seedu.eventfulnus.logic.commands.ListEventCommand;
import seedu.eventfulnus.logic.parser.exceptions.ParseException;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.event.EventContainsKeywordsPredicate;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.PersonContainsKeywordsPredicate;
import seedu.eventfulnus.testutil.EditEventDescriptorBuilder;
import seedu.eventfulnus.testutil.EditPersonDescriptorBuilder;
import seedu.eventfulnus.testutil.EventBuilder;
import seedu.eventfulnus.testutil.EventUtil;
import seedu.eventfulnus.testutil.PersonBuilder;
import seedu.eventfulnus.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addEvent() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(EventUtil.getAddEventCommand(event));
        assertEquals(new AddEventCommand(event), command);
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
    public void parseCommand_editEvent() throws Exception {
        Event event = new EventBuilder().build();
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(event).build();
        EditEventCommand command = (EditEventCommand) parser.parseCommand(EditEventCommand.COMMAND_WORD + " "
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
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new FindCommand(new PersonContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findEvent() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindEventCommand command = (FindEventCommand) parser.parseCommand(
                FindEventCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new FindEventCommand(new EventContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_listEvent() throws Exception {
        assertTrue(parser.parseCommand(ListEventCommand.COMMAND_WORD) instanceof ListEventCommand);
        assertTrue(parser.parseCommand(ListEventCommand.COMMAND_WORD + " 3") instanceof ListEventCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_UNKNOWN_COMMAND + "unknownCommand", () ->
                        parser.parseCommand("unknownCommand"));
    }
}
