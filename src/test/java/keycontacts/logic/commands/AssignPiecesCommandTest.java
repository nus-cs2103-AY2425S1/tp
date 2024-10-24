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

public class AssignPiecesCommandTest {

    private final Set<PianoPiece> validPianoPieces = PianoPiece.getPianoPieceSet(VALID_PIANO_PIECE_BEETHOVEN,
            VALID_PIANO_PIECE_PACHELBEL);

    private final Model model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignPiecesCommand(null, validPianoPieces));
    }
    @Test
    public void constructor_nullPianoPieces_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignPiecesCommand(INDEX_FIRST_STUDENT, null));
    }

    @Test
    public void execute_validIndexAndPianoPiece_success() {
        AssignPiecesCommand command = new AssignPiecesCommand(INDEX_FIRST_STUDENT, validPianoPieces);
        Student student = model.getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student updatedStudent = student.withAddedPianoPieces(validPianoPieces);

        Model expectedModel = new ModelManager(new StudentDirectory(model.getStudentDirectory()), new UserPrefs());
        expectedModel.setStudent(model.getStudentList().get(0), updatedStudent);

        CommandResult commandResult = new CommandResult(String.format(AssignPiecesCommand.MESSAGE_SUCCESS,
                Messages.format(validPianoPieces),
                Messages.format(student)));

        assertCommandSuccess(command, model, commandResult, expectedModel);
    }
    @Test
    public void execute_indexOutOfBounds_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getStudentList().size() + 1);
        AssignPiecesCommand command = new AssignPiecesCommand(outOfBoundsIndex, validPianoPieces);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }
    @Test
    public void execute_duplicatePianoPiece_throwsCommandException() throws Exception {
        AssignPiecesCommand setupCommand = new AssignPiecesCommand(INDEX_FIRST_STUDENT, validPianoPieces);
        setupCommand.execute(model);

        Set<PianoPiece> firstPianoPiece = validPianoPieces.stream().limit(1).collect(Collectors.toSet());
        AssignPiecesCommand command = new AssignPiecesCommand(INDEX_FIRST_STUDENT, firstPianoPiece);

        assertCommandFailure(command, model, String.format(AssignPiecesCommand.MESSAGE_DUPLICATE_PIANO_PIECE,
                Messages.format(firstPianoPiece)));
    }

    @Test
    public void equals() {
        Set<PianoPiece> bensonPianoPieces = BENSON.getPianoPieces();
        Set<PianoPiece> alicePianoPieces = ALICE.getPianoPieces();

        AssignPiecesCommand baseCommand = new AssignPiecesCommand(INDEX_FIRST_STUDENT, bensonPianoPieces);

        // same object -> returns true
        assertTrue(baseCommand.equals(baseCommand));

        // same values -> returns true
        AssignPiecesCommand identicalCommand = new AssignPiecesCommand(INDEX_FIRST_STUDENT, bensonPianoPieces);
        assertTrue(baseCommand.equals(identicalCommand));
        assertTrue(identicalCommand.equals(baseCommand));

        // different values -> returns false
        AssignPiecesCommand differentIndexCommand = new AssignPiecesCommand(INDEX_SECOND_STUDENT, bensonPianoPieces);
        assertFalse(baseCommand.equals(differentIndexCommand));

        AssignPiecesCommand differentPianoPiecesCommand = new AssignPiecesCommand(INDEX_FIRST_STUDENT,
                alicePianoPieces);
        assertFalse(baseCommand.equals(differentPianoPiecesCommand));

        // null -> returns false
        assertFalse(baseCommand.equals(null));

    }

    @Test
    public void toStringMethod() {
        AssignPiecesCommand assignPiecesCommand = new AssignPiecesCommand(INDEX_FIRST_STUDENT, validPianoPieces);
        String expected = AssignPiecesCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_STUDENT + ", "
                + "pianoPieces=" + validPianoPieces + "}";
        assertEquals(expected, assignPiecesCommand.toString());
    }
}
