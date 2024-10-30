package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.eventcommands.AddEventCommand;
import seedu.address.logic.commands.eventcommands.ClearEventCommand;
import seedu.address.logic.commands.eventcommands.DeleteEventCommand;
import seedu.address.logic.commands.eventcommands.EditEventCommand;
import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.commands.eventcommands.ScheduleCommand;
import seedu.address.logic.commands.eventcommands.SearchEventCommand;
import seedu.address.logic.commands.personcommands.AddPersonCommand;
import seedu.address.logic.commands.personcommands.ClearPersonCommand;
import seedu.address.logic.commands.personcommands.DeletePersonCommand;
import seedu.address.logic.commands.personcommands.EditPersonCommand;
import seedu.address.logic.commands.personcommands.EditPersonCommand.EditPersonDescriptor;
import seedu.address.logic.commands.personcommands.FindPersonCommand;
import seedu.address.logic.commands.personcommands.LinkPersonCommand;
import seedu.address.logic.commands.personcommands.SearchPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.types.common.NameContainsKeywordsPredicate;
import seedu.address.model.types.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddPersonCommand command = (AddPersonCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddPersonCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_WORD + " p " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeletePersonCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditPersonCommand command = (EditPersonCommand) parser.parseCommand(EditPersonCommand.COMMAND_WORD + " p "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditPersonCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindPersonCommand command = (FindPersonCommand) parser.parseCommand(
                FindPersonCommand.COMMAND_WORD + " p " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPersonCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_searchPerson() throws Exception {
        // TODO
    }

    @Test
    public void parseCommand_schedule() throws Exception {
        // TODO
    }

    @Test
    public void parseCommand_linkPerson() throws Exception {
        // TODO
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
    public void getHint_addCommandHint() {
        assertEquals(AddPersonCommand.MESSAGE_USAGE, parser.getHint("add p"));
        assertEquals(AddEventCommand.MESSAGE_USAGE, parser.getHint("add e"));
        assertEquals(AddCommand.MESSAGE_USAGE, parser.getHint("a"));
    }

    @Test
    public void getHint_deleteCommandHint() {
        assertEquals(DeletePersonCommand.MESSAGE_USAGE, parser.getHint("delete p"));
        assertEquals(DeleteEventCommand.MESSAGE_USAGE, parser.getHint("delete e"));
        assertEquals(DeleteCommand.MESSAGE_USAGE, parser.getHint("d"));
    }

    @Test
    public void getHint_editCommandHint() {
        assertEquals(EditPersonCommand.MESSAGE_USAGE, parser.getHint("edit p"));
        assertEquals(EditEventCommand.MESSAGE_USAGE, parser.getHint("edit e"));
        assertEquals(EditCommand.MESSAGE_USAGE, parser.getHint("e"));
    }

    @Test
    public void getHint_findCommandHint() {
        assertEquals(FindPersonCommand.MESSAGE_USAGE, parser.getHint("find p"));
        assertEquals(FindEventCommand.MESSAGE_USAGE, parser.getHint("find e"));
        assertEquals(FindCommand.MESSAGE_USAGE, parser.getHint("f"));
    }

    @Test
    public void getHint_searchCommandHint() {
        assertEquals(SearchPersonCommand.MESSAGE_USAGE, parser.getHint("search p"));
        assertEquals(SearchEventCommand.MESSAGE_USAGE, parser.getHint("search e"));
        assertEquals(SearchCommand.MESSAGE_USAGE, parser.getHint("se"));
    }

    @Test
    public void getHint_scheduleCommandHint() {
        assertEquals(ScheduleCommand.MESSAGE_USAGE, parser.getHint("sc"));
    }

    @Test
    public void getHint_listAndLinkCommandHint() {
        assertEquals(ListCommand.MESSAGE_USAGE, parser.getHint("lis"));
        assertEquals(LinkPersonCommand.MESSAGE_USAGE, parser.getHint("lin"));
        assertEquals(ListCommand.MESSAGE_USAGE + "\n" + LinkPersonCommand.MESSAGE_USAGE,
                parser.getHint("l"));
    }

    @Test
    public void getHint_clearCommandHint() {
        assertEquals(ClearPersonCommand.MESSAGE_USAGE, parser.getHint("clear p"));
        assertEquals(ClearEventCommand.MESSAGE_USAGE, parser.getHint("clear e"));
        assertEquals(ClearCommand.MESSAGE_USAGE, parser.getHint("c"));
    }

    @Test
    public void getHint_helpCommandHint() {
        assertEquals(HelpCommand.MESSAGE_USAGE, parser.getHint("h"));
    }

    @Test
    public void getHint_unrecognizedCommandHint() {
        assertEquals("", parser.getHint("unknown"));
    }
}
