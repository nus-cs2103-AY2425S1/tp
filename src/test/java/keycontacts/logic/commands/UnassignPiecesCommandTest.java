package keycontacts.logic.commands;

import static keycontacts.logic.commands.CommandTestUtil.VALID_PIANO_PIECE_BEETHOVEN;
import static keycontacts.logic.commands.CommandTestUtil.VALID_PIANO_PIECE_PACHELBEL;
import static keycontacts.logic.commands.CommandTestUtil.assertCommandFailure;
import static keycontacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static keycontacts.testutil.Assert.assertThrows;
import static keycontacts.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static keycontacts.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static keycontacts.testutil.TypicalStudents.ALICE;
import static keycontacts.testutil.TypicalStudents.BENSON;
import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.Messages;
import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.StudentDirectory;
import keycontacts.model.UserPrefs;
import keycontacts.model.pianopiece.PianoPiece;
import keycontacts.model.student.Student;

public class UnassignPiecesCommandTest {

    private final Set<PianoPiece> validPianoPieces = PianoPiece.getPianoPieceSet(VALID_PIANO_PIECE_BEETHOVEN,
            VALID_PIANO_PIECE_PACHELBEL);

    private final Model model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignPiecesCommand(null, validPianoPieces));
    }
    @Test
    public void constructor_nullPianoPieces_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignPiecesCommand(INDEX_FIRST_STUDENT, null));
    }

    @Test
    public void execute_validIndexAndPianoPiece_success() {
        Student student = model.getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Set<PianoPiece> studentFirstPianoPiece = student.getPianoPieces().stream()
                .limit(1).collect(Collectors.toSet());

        UnassignPiecesCommand command = new UnassignPiecesCommand(INDEX_FIRST_STUDENT, studentFirstPianoPiece);

        Student updatedStudent = student.withRemovedPianoPieces(studentFirstPianoPiece);
        Model expectedModel = new ModelManager(new StudentDirectory(model.getStudentDirectory()), new UserPrefs());
        expectedModel.setStudent(model.getStudentList().get(0), updatedStudent);

        CommandResult commandResult = new CommandResult(String.format(UnassignPiecesCommand.MESSAGE_SUCCESS,
                Messages.format(studentFirstPianoPiece),
                Messages.format(student)));

        assertCommandSuccess(command, model, commandResult, expectedModel);
    }
    @Test
    public void execute_validIndexAndNoPianoPiece_success() {
        Student student = model.getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Set<PianoPiece> studentPieces = student.getPianoPieces();

        Set<PianoPiece> noPieces = Set.of();
        UnassignPiecesCommand command = new UnassignPiecesCommand(INDEX_FIRST_STUDENT, noPieces);

        Student updatedStudent = student.withRemovedPianoPieces(studentPieces);
        Model expectedModel = new ModelManager(new StudentDirectory(model.getStudentDirectory()), new UserPrefs());
        expectedModel.setStudent(model.getStudentList().get(0), updatedStudent);

        CommandResult commandResult = new CommandResult(String.format(UnassignPiecesCommand.MESSAGE_SUCCESS,
                Messages.format(studentPieces),
                Messages.format(student)));

        assertCommandSuccess(command, model, commandResult, expectedModel);
    }
    @Test
    public void execute_indexOutOfBounds_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getStudentList().size() + 1);
        UnassignPiecesCommand command = new UnassignPiecesCommand(outOfBoundsIndex, validPianoPieces);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }
    @Test
    public void execute_studentNoPianoPiece_throwsCommandException() {
        UnassignPiecesCommand command = new UnassignPiecesCommand(INDEX_FIRST_STUDENT, validPianoPieces);
        Student student = model.getStudentList().get(0);
        model.setStudent(model.getStudentList().get(0),
                student.withRemovedPianoPieces(student.getPianoPieces()));
        assertCommandFailure(command, model, UnassignPiecesCommand.MESSAGE_NO_PIANO_PIECE_FOUND);
    }
    @Test
    public void execute_allPianoPiecesNotOnStudent_throwsCommandException() {
        model.setStudent(model.getStudentList().get(0), ALICE);
        Set<PianoPiece> piecesToRemove = BENSON.getPianoPieces();
        UnassignPiecesCommand command = new UnassignPiecesCommand(INDEX_FIRST_STUDENT, piecesToRemove);

        assertCommandFailure(command, model, String.format(UnassignPiecesCommand.MESSAGE_PIANO_PIECE_NOT_FOUND,
                Messages.format(piecesToRemove)));
    }
    @Test
    public void execute_subsetOfPianoPiecesNotOnStudent_throwsCommandException() {
        model.setStudent(model.getStudentList().get(0), ALICE);
        Set<PianoPiece> subsetNotPresent = BENSON.getPianoPieces();
        Set<PianoPiece> piecesToRemove = new HashSet<>(subsetNotPresent);
        piecesToRemove.addAll(ALICE.getPianoPieces());

        UnassignPiecesCommand command = new UnassignPiecesCommand(INDEX_FIRST_STUDENT, piecesToRemove);

        assertCommandFailure(command, model, String.format(UnassignPiecesCommand.MESSAGE_PIANO_PIECE_NOT_FOUND,
                Messages.format(subsetNotPresent)));
    }
    @Test
    public void equals() {
        Set<PianoPiece> bensonPianoPieces = BENSON.getPianoPieces();
        Set<PianoPiece> alicePianoPieces = ALICE.getPianoPieces();

        UnassignPiecesCommand baseCommand = new UnassignPiecesCommand(INDEX_FIRST_STUDENT, bensonPianoPieces);

        // same object -> returns true
        assertTrue(baseCommand.equals(baseCommand));

        // same values -> returns true
        UnassignPiecesCommand identicalCommand = new UnassignPiecesCommand(INDEX_FIRST_STUDENT, bensonPianoPieces);
        assertTrue(baseCommand.equals(identicalCommand));
        assertTrue(identicalCommand.equals(baseCommand));

        // different values -> returns false
        UnassignPiecesCommand differentIndexCommand = new UnassignPiecesCommand(INDEX_SECOND_STUDENT,
                bensonPianoPieces);
        assertFalse(baseCommand.equals(differentIndexCommand));

        UnassignPiecesCommand differentPianoPiecesCommand = new UnassignPiecesCommand(INDEX_FIRST_STUDENT,
                alicePianoPieces);
        assertFalse(baseCommand.equals(differentPianoPiecesCommand));

        // null -> returns false
        assertFalse(baseCommand.equals(null));

    }

    @Test
    public void toStringMethod() {
        UnassignPiecesCommand unassignPiecesCommand = new UnassignPiecesCommand(INDEX_FIRST_STUDENT, validPianoPieces);
        String expected = UnassignPiecesCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_STUDENT + ", "
                + "pianoPieces=" + validPianoPieces + "}";
        assertEquals(expected, unassignPiecesCommand.toString());
    }
}
