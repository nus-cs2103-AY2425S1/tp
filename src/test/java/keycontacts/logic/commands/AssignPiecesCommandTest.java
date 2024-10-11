package keycontacts.logic.commands;

import static keycontacts.testutil.Assert.assertThrows;
import static keycontacts.testutil.TypicalStudents.ALICE;
import static keycontacts.testutil.TypicalStudents.BENSON;
import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.Messages;
import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.UserPrefs;
import keycontacts.model.pianopiece.PianoPiece;
import keycontacts.model.util.SampleDataUtil;

public class AssignPiecesCommandTest {
    private static final Set<PianoPiece> VALID_PIANO_PIECES = SampleDataUtil.getPianoPieceSet(
            "Testing Value 1", "Testing Value 2", "Testing Value 3");
    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private Model model;
    @BeforeEach
    public void initialise() {
        model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignPiecesCommand(null, VALID_PIANO_PIECES));
    }
    @Test
    public void constructor_nullPianoPieces_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignPiecesCommand(VALID_INDEX, null));
    }

    @Test
    public void execute_validIndexAndPianoPiece_success() throws Exception {
        AssignPiecesCommand command = new AssignPiecesCommand(VALID_INDEX, VALID_PIANO_PIECES);
        CommandResult commandResult = command.execute(model);

        assertEquals(String.format(AssignPiecesCommand.MESSAGE_SUCCESS,
                Messages.format(VALID_PIANO_PIECES),
                Messages.format(model.getFilteredStudentList().get(VALID_INDEX.getZeroBased()))),
                commandResult.getFeedbackToUser());
    }
    @Test
    public void execute_duplicatePianoPiece_throwsCommandException() throws Exception {
        AssignPiecesCommand setupCommand = new AssignPiecesCommand(VALID_INDEX, VALID_PIANO_PIECES);
        setupCommand.execute(model);

        Set<PianoPiece> firstPianoPiece = VALID_PIANO_PIECES.stream().limit(1).collect(Collectors.toSet());
        AssignPiecesCommand command = new AssignPiecesCommand(VALID_INDEX, firstPianoPiece);

        assertThrows(CommandException.class,
                AssignPiecesCommand.MESSAGE_DUPLICATE_PIANO_PIECE, () -> command.execute(model));
    }

    @Test
    public void equals() {
        Set<PianoPiece> pianoPieces1 = BENSON.getPianoPieces();
        Set<PianoPiece> pianoPieces2 = ALICE.getPianoPieces();

        Index index1 = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);

        AssignPiecesCommand baseCommand = new AssignPiecesCommand(index1, pianoPieces1);

        // same object -> returns true
        assertTrue(baseCommand.equals(baseCommand));

        // same values -> returns true
        AssignPiecesCommand identicalCommand = new AssignPiecesCommand(index1, pianoPieces1);
        assertTrue(baseCommand.equals(identicalCommand));
        assertTrue(identicalCommand.equals(baseCommand));

        // different values -> returns false
        AssignPiecesCommand differentIndexCommand = new AssignPiecesCommand(index2, pianoPieces1);
        assertFalse(baseCommand.equals(differentIndexCommand));

        AssignPiecesCommand differentPianoPiecesCommand = new AssignPiecesCommand(index1, pianoPieces2);
        assertFalse(baseCommand.equals(differentPianoPiecesCommand));

        // null -> returns false
        assertFalse(baseCommand.equals(null));


    }
    @Test
    public void toStringMethod() {
        AssignPiecesCommand assignPiecesCommand = new AssignPiecesCommand(VALID_INDEX, VALID_PIANO_PIECES);
        String expected = AssignPiecesCommand.class.getCanonicalName() + "{index=" + VALID_INDEX + ", "
                + "pianoPieces=" + VALID_PIANO_PIECES + "}";
        assertEquals(expected, assignPiecesCommand.toString());
    }
}
