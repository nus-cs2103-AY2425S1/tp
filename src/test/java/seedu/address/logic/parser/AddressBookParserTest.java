package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddParentCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListParentsCommand;
import seedu.address.logic.commands.ListStudentsCommand;
import seedu.address.logic.commands.PinCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UnlinkCommand;
import seedu.address.logic.commands.UnpinCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Grade;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.ParentBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.StudentBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_adds() throws Exception {
        Student student = new StudentBuilder().build();
        AddStudentCommand command = (AddStudentCommand) parser.parseCommand(PersonUtil.getAddCommand(student));
        assertEquals(new AddStudentCommand(student), command);
    }

    @Test
    public void parseCommand_addp() throws Exception {
        Parent parent = new ParentBuilder().build();
        AddParentCommand command = (AddParentCommand) parser.parseCommand(PersonUtil.getAddCommand(parent));
        assertEquals(new AddParentCommand(parent), command);
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
        assertEquals(new DeleteCommand(List.of(INDEX_FIRST_PERSON)), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new StudentBuilder().build();
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
    public void parseCommand_findtag() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindTagCommand command = (FindTagCommand) parser.parseCommand(
                FindTagCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindTagCommand(new TagContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_grade() throws Exception {
        final Grade grade = new Grade("1");
        GradeCommand command = (GradeCommand) parser.parseCommand(GradeCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_GRADE + grade.gradeIndex);
        assertEquals(new GradeCommand(INDEX_FIRST_PERSON, grade), command);
    }

    @Test
    public void parseCommand_pin() throws Exception {
        PinCommand command = (PinCommand) parser.parseCommand(
                PinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new PinCommand(List.of(INDEX_FIRST_PERSON)), command);
    }

    @Test
    public void parseCommand_unpin() throws Exception {
        UnpinCommand command = (UnpinCommand) parser.parseCommand(
                UnpinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new UnpinCommand(List.of(INDEX_FIRST_PERSON)), command);
    }

    @Test
    public void parseCommand_listStudents() throws Exception {
        assertTrue(parser.parseCommand(ListStudentsCommand.COMMAND_WORD) instanceof ListStudentsCommand);
        assertTrue(parser.parseCommand(
                ListStudentsCommand.COMMAND_WORD + " 3") instanceof ListStudentsCommand);
    }

    @Test
    public void parseCommand_listParents() throws Exception {
        assertTrue(parser.parseCommand(ListParentsCommand.COMMAND_WORD) instanceof ListParentsCommand);
        assertTrue(parser.parseCommand(
                ListParentsCommand.COMMAND_WORD + " 3") instanceof ListParentsCommand);
    }

    @Test
    public void parseCommand_unlink() throws Exception {
        Name name = new Name("John Doe");
        UnlinkCommand command = (UnlinkCommand) parser.parseCommand(
                UnlinkCommand.COMMAND_WORD + " " + PREFIX_CHILD + "John Doe");
        assertEquals(new UnlinkCommand(name), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " 3") instanceof SortCommand);
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
