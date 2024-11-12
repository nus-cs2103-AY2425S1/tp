package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AssignEventByPersonIndexEventIndexCommand;
import seedu.address.logic.commands.AssignEventByPersonIndexEventNameCommand;
import seedu.address.logic.commands.AssignEventByPersonNameEventIndexCommand;
import seedu.address.logic.commands.AssignEventByPersonNameEventNameCommand;
import seedu.address.logic.commands.AssignEventCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteByIndexCommand;
import seedu.address.logic.commands.DeleteByNameCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventByIndexCommand;
import seedu.address.logic.commands.DeleteEventByNameCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListEventsCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.UnassignEventByPersonIndexEventIndexCommand;
import seedu.address.logic.commands.UnassignEventByPersonIndexEventNameCommand;
import seedu.address.logic.commands.UnassignEventByPersonNameEventIndexCommand;
import seedu.address.logic.commands.UnassignEventByPersonNameEventNameCommand;
import seedu.address.logic.commands.UnassignEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;


public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deleteByIndex() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteByIndexCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteByName() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + ALICE.getName().toString());
        assertEquals(new DeleteByNameCommand(ALICE.getName()), command);
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
    public void parseCommand_search() throws Exception {
        List<String> keywords;
        SearchCommand command;
        //Search command with address prefix
        keywords = Arrays.asList("street", "road", "lane");
        command = (SearchCommand) parser.parseCommand(
                SearchCommand.COMMAND_WORD + " a/"
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new AddressContainsKeywordsPredicate(keywords)), command);
        //Search command with email prefix
        keywords = Arrays.asList("example", "gmail", "yahoo");
        command = (SearchCommand) parser.parseCommand(
                SearchCommand.COMMAND_WORD + " e/"
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new EmailContainsKeywordsPredicate(keywords)), command);
        //Search command with name prefix
        keywords = Arrays.asList("foo", "bar", "baz");
        command = (SearchCommand) parser.parseCommand(
                SearchCommand.COMMAND_WORD + " n/"
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new NameContainsKeywordsPredicate(keywords)), command);
        //Search command with phone prefix
        keywords = Arrays.asList("12345678", "98765432", "13572468");
        command = (SearchCommand) parser.parseCommand(SearchCommand.COMMAND_WORD + " p/"
                + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new PhoneContainsKeywordsPredicate(keywords)), command);
        //Search command with tags prefix
        keywords = Arrays.asList("12345678", "98765432", "13572468");
        command = (SearchCommand) parser.parseCommand(SearchCommand.COMMAND_WORD + " t/"
                + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new TagContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_add_event() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(EventUtil.getAddEventCommand(event));
        assertEquals(new AddEventCommand(event), command);
    }

    @Test
    public void parseCommand_list_events() throws Exception {
        assertTrue(parser.parseCommand(ListEventsCommand.COMMAND_WORD) instanceof ListEventsCommand);
        assertTrue(parser.parseCommand(ListEventsCommand.COMMAND_WORD + " 3") instanceof ListEventsCommand);
    }

    @Test
    public void parseCommand_unassign_events() throws Exception {
        UnassignEventCommand unassignEventByPersonIndexEventIndexCommand = (UnassignEventCommand) parser.parseCommand(
                EventUtil.getUnassignEventDetails("1", "1"));
        assertEquals(new UnassignEventByPersonIndexEventIndexCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT),
                unassignEventByPersonIndexEventIndexCommand);
        UnassignEventCommand unassignEventByPersonIndexEventNameCommand = (UnassignEventCommand) parser.parseCommand(
                EventUtil.getUnassignEventDetails("1", MEETING.getEventName().toString()));
        assertEquals(new UnassignEventByPersonIndexEventNameCommand(INDEX_FIRST_PERSON, MEETING.getEventName()),
                unassignEventByPersonIndexEventNameCommand);
        UnassignEventCommand unassignEventByPersonNameEventIndexCommand = (UnassignEventCommand) parser.parseCommand(
                EventUtil.getUnassignEventDetails(ALICE.getName().toString(), "1"));
        assertEquals(new UnassignEventByPersonNameEventIndexCommand(ALICE.getName(), INDEX_FIRST_EVENT),
                unassignEventByPersonNameEventIndexCommand);
        UnassignEventCommand unassignEventByPersonNameEventNameCommand = (UnassignEventCommand) parser.parseCommand(
                EventUtil.getUnassignEventDetails(ALICE.getName().toString(), MEETING.getEventName().toString()));
        assertEquals(new UnassignEventByPersonNameEventNameCommand(ALICE.getName(), MEETING.getEventName()),
                unassignEventByPersonNameEventNameCommand);
    }

    @Test
    public void parseCommand_assign_events() throws Exception {
        AssignEventCommand assignEventByPersonIndexEventIndexCommand = (AssignEventCommand) parser.parseCommand(
                EventUtil.getAssignEventDetails("1", "1"));
        assertEquals(new AssignEventByPersonIndexEventIndexCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT),
                assignEventByPersonIndexEventIndexCommand);
        AssignEventCommand assignEventByPersonIndexEventNameCommand = (AssignEventCommand) parser.parseCommand(
                EventUtil.getAssignEventDetails("1", MEETING.getEventName().toString()));
        assertEquals(new AssignEventByPersonIndexEventNameCommand(INDEX_FIRST_PERSON, MEETING.getEventName()),
                assignEventByPersonIndexEventNameCommand);
        AssignEventCommand assignEventByPersonNameEventIndexCommand = (AssignEventCommand) parser.parseCommand(
                EventUtil.getAssignEventDetails(ALICE.getName().toString(), "1"));
        assertEquals(new AssignEventByPersonNameEventIndexCommand(ALICE.getName(), INDEX_FIRST_EVENT),
                assignEventByPersonNameEventIndexCommand);
        AssignEventCommand assignEventByPersonNameEventNameCommand = (AssignEventCommand) parser.parseCommand(
                EventUtil.getAssignEventDetails(ALICE.getName().toString(), MEETING.getEventName().toString()));
        assertEquals(new AssignEventByPersonNameEventNameCommand(ALICE.getName(), MEETING.getEventName()),
                assignEventByPersonNameEventNameCommand);
    }

    @Test
    public void parseCommand_deleteEventByIndex() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new DeleteEventByIndexCommand(INDEX_FIRST_EVENT), command);
    }

    @Test
    public void parseCommand_deleteEventByName() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + MEETING.getName().toString());
        assertEquals(new DeleteEventByNameCommand(MEETING.getName()), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_export() throws Exception {
        assertTrue(parser.parseCommand(ExportCommand.COMMAND_WORD) instanceof ExportCommand);
    }

    @Test
    public void parseCommand_import() throws Exception {
        ImportCommand command = (ImportCommand) parser.parseCommand(
                ImportCommand.COMMAND_WORD + " someFile.csv");
        assertEquals(new ImportCommand("someFile.csv"), command);
    }

    @Test
    public void parseCommand_editEvent() throws Exception {
        Index targetIndex = Index.fromOneBased(1);
        Event event = new EventBuilder().build();

        EditEventCommand.EditEventDescriptor descriptor = new EditEventCommand.EditEventDescriptor();
        descriptor.setName(event.getEventName());
        descriptor.setDescription(event.getEventDescription());
        descriptor.setDuration(event.getEventDuration());

        String commandString = EditEventCommand.COMMAND_WORD + " " + targetIndex.getOneBased() + " "
                + EventUtil.getEditEventDetails(descriptor);

        EditEventCommand command = (EditEventCommand) parser.parseCommand(commandString);

        assertEquals(new EditEventCommand(targetIndex, descriptor), command);
    }
}
