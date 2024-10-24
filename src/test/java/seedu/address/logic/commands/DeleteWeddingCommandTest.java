package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WEDDING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_WEDDING;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_TWO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.wedding.Wedding;

public class DeleteWeddingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    void setUp() {
        model.addWedding(WEDDING_ONE);
        model.addWedding(WEDDING_TWO);
        expectedModel.addWedding(WEDDING_ONE);
        expectedModel.addWedding(WEDDING_TWO);
    }

    @Test
    public void execute_validIndex_success() {
        Wedding weddingToDelete = model.getFilteredWeddingList().get(INDEX_FIRST_WEDDING.getZeroBased());
        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(INDEX_FIRST_WEDDING);

        String expectedMessage = String.format(DeleteWeddingCommand.MESSAGE_DELETE_WEDDING_SUCCESS,
                Messages.formatWedding(weddingToDelete));

        expectedModel.removeWedding(weddingToDelete);

        assertCommandSuccess(deleteWeddingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWeddingList().size() + 1);
        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(outOfBoundIndex);

        assertCommandFailure(deleteWeddingCommand, model, Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
    }


    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(targetIndex);
        String expected = DeleteWeddingCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteWeddingCommand.toString());
    }

    @Test
    public void equals() {
        DeleteWeddingCommand deleteWeddingFirstCommand = new DeleteWeddingCommand(INDEX_FIRST_WEDDING);
        DeleteWeddingCommand deleteWeddingSecondCommand = new DeleteWeddingCommand(INDEX_SECOND_WEDDING);

        // same object -> returns true
        assertTrue(deleteWeddingFirstCommand.equals(deleteWeddingFirstCommand));

        // same values -> returns true
        DeleteWeddingCommand deleteFirstCommandCopy = new DeleteWeddingCommand(INDEX_FIRST_WEDDING);
        assertTrue(deleteWeddingFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteWeddingFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteWeddingFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteWeddingFirstCommand.equals(deleteWeddingSecondCommand));
    }

}
