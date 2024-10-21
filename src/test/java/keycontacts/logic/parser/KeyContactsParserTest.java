package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PIECE_NAME;
import static keycontacts.testutil.Assert.assertThrows;
import static keycontacts.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static keycontacts.testutil.TypicalStudents.ALICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.commands.AddCommand;
import keycontacts.logic.commands.AssignPiecesCommand;
import keycontacts.logic.commands.ClearCommand;
import keycontacts.logic.commands.DeleteCommand;
import keycontacts.logic.commands.EditCommand;
import keycontacts.logic.commands.EditCommand.EditStudentDescriptor;
import keycontacts.logic.commands.ExitCommand;
import keycontacts.logic.commands.FindCommand;
import keycontacts.logic.commands.FindCommand.FindStudentDescriptor;
import keycontacts.logic.commands.HelpCommand;
import keycontacts.logic.commands.ListCommand;
import keycontacts.logic.commands.ScheduleCommand;
import keycontacts.logic.commands.UnassignPiecesCommand;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.pianopiece.PianoPiece;
import keycontacts.model.student.Student;
import keycontacts.model.student.StudentDescriptorMatchesPredicate;
import keycontacts.testutil.EditStudentDescriptorBuilder;
import keycontacts.testutil.FindStudentDescriptorBuilder;
import keycontacts.testutil.StudentBuilder;
import keycontacts.testutil.StudentUtil;

public class KeyContactsParserTest {

    private final KeyContactsParser parser = new KeyContactsParser();

    @Test
    public void parseCommand_add() throws Exception {
        Student student = new StudentBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(StudentUtil.getAddCommand(student));
        assertEquals(new AddCommand(student), command);
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
        List<String> keywords = Arrays.asList("n/foo", "a/bar", "g/baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("foo")
                .withAddress("bar").withGradeLevel("baz").build();
        assertEquals(new FindCommand(new StudentDescriptorMatchesPredicate(descriptor)), command);
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
    public void parseCommand_assign() throws Exception {
        String[] pianoPieces = new String[] {"Moonlight Sonata", "Clair de Lune", "Nocturne Op. 9 No. 2"};
        String pianoPieceArguments = Arrays.stream(pianoPieces)
                .map(piece -> PREFIX_PIECE_NAME + piece)
                .collect(Collectors.joining(" "));
        Index index = Index.fromOneBased(3);
        AssignPiecesCommand command = (AssignPiecesCommand) parser.parseCommand(
                AssignPiecesCommand.COMMAND_WORD + " " + index.getOneBased() + " " + pianoPieceArguments);

        AssignPiecesCommand expectedCommand = new AssignPiecesCommand(
                index,
                PianoPiece.getPianoPieceSet(pianoPieces));
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_unassign() throws Exception {
        String[] pianoPieces = new String[] {"Moonlight Sonata", "Clair de Lune", "Nocturne Op. 9 No. 2"};
        String pianoPieceArguments = Arrays.stream(pianoPieces)
                .map(piece -> PREFIX_PIECE_NAME + piece)
                .collect(Collectors.joining(" "));
        Index index = Index.fromOneBased(4);
        UnassignPiecesCommand command = (UnassignPiecesCommand) parser.parseCommand(
                UnassignPiecesCommand.COMMAND_WORD + " " + index.getOneBased() + " " + pianoPieceArguments);

        UnassignPiecesCommand expectedCommand = new UnassignPiecesCommand(
                index,
                PianoPiece.getPianoPieceSet(pianoPieces));
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_schedule() throws Exception {
        RegularLesson regularLesson = ALICE.getRegularLesson();
        ScheduleCommand command = (ScheduleCommand) parser.parseCommand(
                ScheduleCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDENT.getOneBased() + " "
                        + StudentUtil.getRegularLessonDetails(regularLesson));
        assertEquals(new ScheduleCommand(INDEX_FIRST_STUDENT, regularLesson), command);
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
