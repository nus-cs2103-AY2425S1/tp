package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCEDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALID;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUTORIAL_ID;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AttendCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
// import seedu.address.logic.commands.EditCommand;
// import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListTutorialCommand;
import seedu.address.logic.commands.UnattendCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;
// import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.StudentUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Student student = new StudentBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(StudentUtil.getAddCommand(student));
        assertEquals(new AddCommand(student, TUTORIAL_ID), command);
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
        assertEquals(new DeleteCommand(INDEX_FIRST_STUDENT), command);
    }

    @Test
    public void parseCommand_attend() throws Exception {
        String userInput = AttendCommand.COMMAND_WORD + " "
                + PREFIX_STUDENTID + "A1001000U "
                + PREFIX_TUTORIALID + "T1001 "
                + PREFIX_ATTENDANCEDATE + "2024/02/21";

        Command command = parser.parseCommand(userInput);

        StudentId expectedStudentId = new StudentId("A1001000U");
        TutorialId expectedTutorialId = TutorialId.of("T1001");
        Date expectedDate = new SimpleDateFormat("yyyy/MM/dd").parse("2024/02/21");

        AttendCommand expectedCommand = new AttendCommand(expectedStudentId, expectedTutorialId, expectedDate);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_absent() throws Exception {
        String userInput = UnattendCommand.COMMAND_WORD + " "
                + PREFIX_STUDENTID + "A1001000U "
                + PREFIX_TUTORIALID + "T1001 "
                + PREFIX_ATTENDANCEDATE + "2024/02/21";

        Command command = parser.parseCommand(userInput);

        StudentId expectedStudentId = new StudentId("A1001000U");
        TutorialId expectedTutorialId = TutorialId.of("T1001");
        Date expectedDate = new SimpleDateFormat("yyyy/MM/dd").parse("2024/02/21");

        UnattendCommand expectedCommand = new UnattendCommand(expectedStudentId, expectedTutorialId, expectedDate);

        assertEquals(expectedCommand, command);
    }
    /*
    @Test
    public void parseCommand_edit() throws Exception {
        Student student = new StudentBuilder().build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(student).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_STUDENT.getOneBased() + " " + StudentUtil.getEditStudentDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_STUDENT, descriptor), command);
    }
    */


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
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
    @Test
    public void parseCommand_listTutorial() throws Exception {
        // Test parsing the "listTut" command without arguments
        assertTrue(parser.parseCommand(ListTutorialCommand.COMMAND_WORD) instanceof ListTutorialCommand);

        // Test parsing the "listTut" command with extra arguments (it should still parse as ListTutorialCommand)
        assertTrue(parser.parseCommand(ListTutorialCommand.COMMAND_WORD + " 3") instanceof ListTutorialCommand);
    }
}
