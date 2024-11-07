package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.contact.commands.AddCommand;
import seedu.address.logic.commands.contact.commands.ClearCommand;
import seedu.address.logic.commands.contact.commands.DeleteCommand;
import seedu.address.logic.commands.contact.commands.EditCommand;
import seedu.address.logic.commands.contact.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.contact.commands.FindNameCommand;
import seedu.address.logic.commands.contact.commands.FindRoleCommand;
import seedu.address.logic.commands.contact.commands.ListCommand;
import seedu.address.logic.commands.event.commands.AddEventCommand;
import seedu.address.logic.commands.event.commands.AddPersonToEventCommand;
import seedu.address.logic.commands.event.commands.DeleteEventCommand;
import seedu.address.logic.commands.event.commands.EventAddAllCommand;
import seedu.address.logic.commands.event.commands.RemovePersonFromEventCommand;
import seedu.address.logic.commands.searchmode.CheckExcludedCommand;
import seedu.address.logic.commands.searchmode.ClearExcludedCommand;
import seedu.address.logic.commands.searchmode.ExitSearchModeCommand;
import seedu.address.logic.commands.searchmode.SearchModeSearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PersonIsRolePredicate;
import seedu.address.model.role.Role;
import seedu.address.model.role.Sponsor;
import seedu.address.model.role.Volunteer;
import seedu.address.testutil.EditPersonDescriptorBuilder;
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
        FindNameCommand command = (FindNameCommand) parser.parseCommand(
                FindNameCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindNameCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_search() throws Exception {
        List<Role> roles = Arrays.asList(new Sponsor(), new Volunteer());
        FindRoleCommand command = (FindRoleCommand) parser.parseCommand(
                FindRoleCommand.COMMAND_WORD + " "
                        + roles.stream().map(Role::getRoleName).collect(Collectors.joining(" ")));
        assertEquals(new FindRoleCommand(new PersonIsRolePredicate(roles)), command);
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
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_testCommand() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("editer /p 12345678"));
    }

    @Test
    public void parseCommand_addEvent() throws ParseException {
        Command expected = new AddEventCommand(new Event("sumo bot festival"));
        assertEquals(expected, new AddressBookParser()
                .parseCommand(AddEventCommand.COMMAND_WORD + " sumo bot festival"));
    }

    @Test
    public void parseCommand_removePersonFromEvent() throws ParseException {
        Command expected = new RemovePersonFromEventCommand(Index.fromOneBased(1),
                Index.fromOneBased(1));
        assertEquals(expected, new AddressBookParser()
                .parseCommand(RemovePersonFromEventCommand.COMMAND_WORD + " ei/1 pi/1"));
    }

    @Test
    public void parseSearchModeCommand_searchModeSearchCommand() throws ParseException {
        Command expected = new SearchModeSearchCommand(new NameContainsKeywordsPredicate(Arrays.asList("Amy")));
        assertEquals(expected, new AddressBookParser()
                .parseSearchModeCommand(SearchModeSearchCommand.COMMAND_WORD + " n/Amy"));
    }

    @Test
    public void parseSearchModeCommand_exitSearchModeCommand() throws ParseException {
        Command expected = new ExitSearchModeCommand();
        assertEquals(expected, new AddressBookParser()
                .parseSearchModeCommand(ExitSearchModeCommand.COMMAND_WORD));
    }
    @Test
    public void parseSearchModeCommand_exitCommand() throws ParseException {
        Command expected = new ExitCommand();
        assertEquals(expected, new AddressBookParser()
                .parseSearchModeCommand(ExitCommand.COMMAND_WORD));
    }

    @Test
    public void parseSearchModeCommand_eventAddAllCommand() throws ParseException {
        Command expected = new EventAddAllCommand(INDEX_FIRST_EVENT);
        assertEquals(expected, new AddressBookParser()
                .parseSearchModeCommand(EventAddAllCommand.COMMAND_WORD + " 1"));
    }

    @Test
    public void parseSearchModeCommand_emptyInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseSearchModeCommand(""));
    }

    @Test
    public void parseSearchModeCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND
                + Messages.SEARCHMODE_UNKNOWN_COMMAND, () ->
                parser.parseSearchModeCommand("random"));
    }

    @Test
    public void parseCommand_addPersonToEvent() throws ParseException {
        HashSet<Index> attendees = new HashSet<>();
        attendees.add(INDEX_FIRST_PERSON);
        Command expected = new AddPersonToEventCommand(INDEX_FIRST_EVENT, attendees, new HashSet<>(),
                new HashSet<>(), new HashSet<>());
        assertEquals(expected, new AddressBookParser()
                .parseCommand(AddPersonToEventCommand.COMMAND_WORD + " ei/1 a/1"));
    }

    @Test
    public void parseCommand_deleteEvent() throws ParseException {
        Command expected = new DeleteEventCommand(INDEX_FIRST_EVENT);
        assertEquals(expected, new AddressBookParser()
                .parseCommand(DeleteEventCommand.COMMAND_WORD + " 1"));
    }

    @Test
    public void parseSearchModeCommand_helpCommand() throws ParseException {
        Command expected = new HelpCommand();
        assertEquals(expected, new AddressBookParser()
                .parseSearchModeCommand(HelpCommand.COMMAND_WORD));
    }

    @Test
    public void parseSearchModeCommand_excludePersonCommand() throws ParseException {
        Model model = new ModelManager();
        model.addPerson(ALICE);
        model.addPerson(BOB);
        model.addPerson(CARL);
        Command expected = new ExcludePersonCommandParser().parse(" pi/1, 2, 3");
        assertEquals(expected, new AddressBookParser()
                .parseSearchModeCommand("exclude pi/1, 2, 3"));
    }

    @Test
    public void parseSearchModeCommand_checkExcludedCommand() throws ParseException {
        Command expected = new CheckExcludedCommand();
        assertEquals(expected, new AddressBookParser()
                .parseSearchModeCommand(CheckExcludedCommand.COMMAND_WORD));
    }

    @Test
    public void parseSearchModeCommand_clearExcludedCommand() throws ParseException {
        Command expected = new ClearExcludedCommand();
        assertEquals(expected, new AddressBookParser()
                .parseSearchModeCommand(ClearExcludedCommand.COMMAND_WORD));
    }

}
