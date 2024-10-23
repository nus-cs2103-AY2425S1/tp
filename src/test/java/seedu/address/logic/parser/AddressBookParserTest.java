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

import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.ClearAllCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearEventCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.EditPersonCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditPersonCommandTest;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListPersonCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewEventCommand;
import seedu.address.logic.commands.ViewPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
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
        String clearAllCommand = ClearCommand.COMMAND_WORD + " " + ClearAllCommand.COMMAND_FIELD;
        assertTrue(parser.parseCommand(clearAllCommand) instanceof ClearCommand);

        String clearEventsCommand = ClearCommand.COMMAND_WORD + " " + ClearEventCommand.COMMAND_FIELD;
        assertTrue(parser.parseCommand(clearEventsCommand) instanceof ClearCommand);

        try {
            parser.parseCommand(ClearCommand.COMMAND_WORD + " 3");
        } catch (Exception e) {
            assertTrue(e instanceof ParseException);
        }
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_WORD + " " + DeletePersonCommand.COMMAND_FIELD
                        + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeletePersonCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditPersonCommand command = (EditPersonCommand) parser.parseCommand(EditPersonCommand.COMMAND_WORD + " "
                + EditPersonCommand.COMMAND_FIELD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditPersonCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findPerson() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindPersonCommand command = (FindPersonCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + FindPersonCommand.COMMAND_FIELD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPersonCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_listPerson() throws Exception {
        String listPersonCommand = ListCommand.COMMAND_WORD + " " + ListPersonCommand.COMMAND_FIELD;
        assertTrue(parser.parseCommand(listPersonCommand) instanceof ListCommand);
        assertTrue(parser.parseCommand(listPersonCommand + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_viewPerson() throws Exception {
        String viewPersonCommand = ViewCommand.COMMAND_WORD + " " + ViewPersonCommand.COMMAND_FIELD;
        assertTrue(parser.parseCommand(viewPersonCommand + " Bob") instanceof ViewCommand);
        try {
            parser.parseCommand(viewPersonCommand + " ");
        } catch (Exception e) {
            assertTrue(e instanceof ParseException);
        }
    }

    @Test
    public void parseCommand_viewEvent() throws Exception {
        String viewEventCommand = ViewCommand.COMMAND_WORD + " " + ViewEventCommand.COMMAND_FIELD;
        assertTrue(parser.parseCommand(viewEventCommand + " Bob") instanceof ViewCommand);
        try {
            parser.parseCommand(viewEventCommand + " ");
        } catch (Exception e) {
            assertTrue(e instanceof ParseException);
        }
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
