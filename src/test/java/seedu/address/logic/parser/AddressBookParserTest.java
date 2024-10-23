package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DIDDY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalStudents.DIDDY;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditStudentCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GetAttendanceByTgCommand;
import seedu.address.logic.commands.GetAttendanceCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.commands.ViewStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
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
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_viewStudent() throws Exception {
        Name name = DIDDY.getName();
        ViewStudentCommand command = (ViewStudentCommand) parser.parseCommand(ViewStudentCommand.COMMAND_WORD
                + " " + VALID_NAME_DIDDY);
        assertEquals(new ViewStudentCommand(name), command);
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
    public void parseCommand_deletea() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(DeleteAssignmentCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(
                DeleteAssignmentCommand.COMMAND_WORD + " a/1 n/1") instanceof DeleteAssignmentCommand);
    }

    @Test
    public void parseCommand_adds() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(AddStudentCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(AddStudentCommand.COMMAND_WORD
                + " n/John Doe p/98765432 tg/G16 sn/A1234567X") instanceof AddStudentCommand);
    }

    @Test
    public void parseCommand_edits() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStudentCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(EditStudentCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(
                EditStudentCommand.COMMAND_WORD + " 1 n/John Doe") instanceof EditStudentCommand);
    }

    @Test
    public void parseCommand_markAttendance() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(MarkAttendanceCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(
                MarkAttendanceCommand.COMMAND_WORD + " n/John Doe d/2024-10-01 pr/p")
                instanceof MarkAttendanceCommand);
    }

    @Test
    public void parseCommand_getAttendance() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetAttendanceCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(GetAttendanceCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(
                GetAttendanceCommand.COMMAND_WORD + " n/John Doe d/2019-01-01")
                instanceof GetAttendanceCommand);
    }

    @Test
    public void parseCommand_getAttendanceByTg() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetAttendanceByTgCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(GetAttendanceByTgCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(
                GetAttendanceByTgCommand.COMMAND_WORD + " tg/G16") instanceof GetAttendanceByTgCommand);
    }

    @Test
    public void parseCommand_addAssignment() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(AddAssignmentCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(
                AddAssignmentCommand.COMMAND_WORD + " n/John a/Homework 1 d/2024-12-12")
                instanceof AddAssignmentCommand);
    }

    @Test
    public void parseCommand_deleteAssignment() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(DeleteAssignmentCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(
                DeleteAssignmentCommand.COMMAND_WORD + " n/John a/Homework 1") instanceof DeleteAssignmentCommand);
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
