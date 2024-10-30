package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_DIDDY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.DIDDY;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.DeleteAllStudentsCommand;
import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.logic.commands.EditStudentCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GetAttendanceByTgCommand;
import seedu.address.logic.commands.GetAttendanceCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.commands.MarkPresentAllCommand;
import seedu.address.logic.commands.UnmarkPresentAllCommand;
import seedu.address.logic.commands.ViewStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.student.TutorialGroup;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
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
    public void parseCommand_deleteall() throws Exception {
        assertTrue(parser.parseCommand("deleteall") instanceof DeleteAllStudentsCommand);
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
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand("undo") instanceof seedu.address.logic.commands.UndoCommand);
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
    public void parseCommand_markPresentAll() throws Exception {
        TutorialGroup tutorialGroup = DIDDY.getTutorialGroup();
        LocalDate date = LocalDate.of(2024, 10, 23);
        MarkPresentAllCommand command = (MarkPresentAllCommand) parser.parseCommand(MarkPresentAllCommand.COMMAND_WORD
                + " " + PREFIX_TUTORIAL_GROUP + VALID_TUTORIAL_GROUP_DIDDY + " " + PREFIX_DATE + date);
        assertEquals(new MarkPresentAllCommand(tutorialGroup, date), command);
    }

    @Test
    public void parseCommand_unmarkPresentAll() throws Exception {
        TutorialGroup tutorialGroup = DIDDY.getTutorialGroup();
        LocalDate date = LocalDate.of(2024, 10, 23);
        UnmarkPresentAllCommand command = (UnmarkPresentAllCommand) parser.parseCommand(
                UnmarkPresentAllCommand.COMMAND_WORD + " " + PREFIX_TUTORIAL_GROUP + VALID_TUTORIAL_GROUP_DIDDY + " "
                        + PREFIX_DATE + date);
        assertEquals(new UnmarkPresentAllCommand(tutorialGroup, date), command);
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
