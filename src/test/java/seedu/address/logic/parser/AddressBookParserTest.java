package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CourseContainsKeywordsPredicate;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Module;
import seedu.address.model.person.ModuleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
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
        StudentId validStudentId = new StudentId("12345678");
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + validStudentId);
        assertEquals(new DeleteCommand(validStudentId), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).setEmptyModuleList().build();
        StudentId studentId = person.getStudentId();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(studentId, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_filterByName() throws Exception {
        String nameKeywords = "Alice Bob";
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + nameKeywords);
        assertEquals(new FilterCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"))), command);
    }

    @Test
    public void parseCommand_filterByModule() throws Exception {
        String moduleKeyword = "CS2103T";
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " " + PREFIX_MODULE + moduleKeyword);
        assertEquals(new FilterCommand(new ModuleContainsKeywordsPredicate(moduleKeyword)), command);
    }

    @Test
    public void parseCommand_filterByCourse() throws Exception {
        String courseKeywords = "Computer Science";
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " " + PREFIX_COURSE + courseKeywords);
        assertEquals(new FilterCommand(
                new CourseContainsKeywordsPredicate(Arrays.asList("Computer", "Science"))), command);
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
    public void parseCommand_module() throws Exception {
        StudentId validStudentId = new StudentId(CommandTestUtil.VALID_STUDENTID_BOB);
        Module validModule = new Module(CommandTestUtil.VALID_MODULE_BOB);
        ModuleCommand command = (ModuleCommand) parser.parseCommand(
                ModuleCommand.COMMAND_WORD + " " + validStudentId + " "
                        + PREFIX_MODULE + validModule.value);
        assertEquals(new ModuleCommand(validStudentId, validModule), command);
    }

    @Test
    public void parseCommand_grade() throws Exception {
        StudentId validStudentId = new StudentId(CommandTestUtil.VALID_STUDENTID_BOB);
        Module validModule = new Module(CommandTestUtil.VALID_MODULE_BOB);
        Grade validGrade = new Grade(CommandTestUtil.VALID_GRADE_BOB);
        System.out.println("this");
        GradeCommand command = (GradeCommand) parser.parseCommand(
                GradeCommand.COMMAND_WORD + " " + validStudentId + " "
                        + PREFIX_MODULE + validModule.value + " " + PREFIX_GRADE + validGrade);
        assertEquals(new GradeCommand(validStudentId, validModule, validGrade), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        StudentId validStudentId = new StudentId(CommandTestUtil.VALID_STUDENTID_BOB);
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + validStudentId);
        assertEquals(new FindCommand(validStudentId), command);
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
