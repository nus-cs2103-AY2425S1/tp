package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.consultation.AddConsultCommand;
import seedu.address.logic.commands.consultation.AddToConsultCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.student.IsStudentOfCoursePredicate;
import seedu.address.model.student.Name;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;
import seedu.address.testutil.ConsultationBuilder;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.StudentUtil;


public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Student student = new StudentBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(StudentUtil.getAddCommand(student));
        assertEquals(new AddCommand(student), command);
    }

    @Test
    public void parseCommand_addConsult() throws Exception {
        Consultation consult = new ConsultationBuilder().build();
        AddConsultCommand command = (AddConsultCommand) parser.parseCommand(
                AddConsultCommand.COMMAND_WORD + " d/" + consult.getDate() + " t/" + consult.getTime());
        assertEquals(new AddConsultCommand(consult), command);
    }

    @Test
    public void parseCommand_addToConsult() throws Exception {
        // Construct the input arguments for the AddToConsultCommand
        Index index = Index.fromOneBased(1);
        String name1 = "John Doe";
        String name2 = "Harry Ng";

        String input = String.format("%s %d n/%s n/%s", AddToConsultCommand.COMMAND_WORD,
                index.getOneBased(), name1, name2);

        AddToConsultCommand command = (AddToConsultCommand) parser.parseCommand(input);

        // Construct the expected command
        List<Name> expectedNames = List.of(new Name(name1), new Name(name2));
        AddToConsultCommand expectedCommand = new AddToConsultCommand(index, expectedNames);

        // Assert that the parsed command is equal to the expected command
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDENT.getOneBased());
        Set<Index> firstIndexSet = new HashSet<>();
        firstIndexSet.add(INDEX_FIRST_STUDENT);
        assertEquals(new DeleteCommand(firstIndexSet), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Student student = new StudentBuilder().build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(student).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_STUDENT.getOneBased() + " " + StudentUtil.getEditStudentDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_STUDENT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " n/foo c/cs2101 n/bar c/cs2102 n/baz");
        FindCommand expectedCommand = new FindCommand(List.of(
                new NameContainsKeywordsPredicate(List.of("foo")),
                new NameContainsKeywordsPredicate(List.of("bar")),
                new NameContainsKeywordsPredicate(List.of("baz")),
                new IsStudentOfCoursePredicate(List.of("CS2101")),
                new IsStudentOfCoursePredicate(List.of("CS2102"))
        ));
        assertEquals(expectedCommand, command);
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
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_export() throws Exception {
        String filePath = "data/export.csv";
        ExportCommand command = (ExportCommand) parser.parseCommand(
                ExportCommand.COMMAND_WORD + " " + filePath);
        assertEquals(new ExportCommand(Paths.get(filePath)), command);
    }
}
