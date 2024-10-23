package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
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
import seedu.address.model.person.PersonInWeddingPredicate;
import seedu.address.model.wedding.Wedding;

public class ViewWeddingCommandTest {
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
        ViewWeddingCommand viewWeddingCommand = new ViewWeddingCommand(INDEX_FIRST_PERSON);
        Wedding targetWedding = model.getFilteredWeddingList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Set up expected model and message
        String expectedMessage = String.format(ViewWeddingCommand.MESSAGE_SUCCESS,
                targetWedding.getWeddingName());
        PersonInWeddingPredicate predicate = new PersonInWeddingPredicate(targetWedding);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(viewWeddingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWeddingList().size() + 1);
        ViewWeddingCommand viewWeddingCommand = new ViewWeddingCommand(outOfBoundIndex);

        assertCommandFailure(viewWeddingCommand, model, Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewWeddingCommand viewWeddingOneCommand = new ViewWeddingCommand(INDEX_FIRST_PERSON);
        ViewWeddingCommand viewWeddingTwoCommand = new ViewWeddingCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(viewWeddingOneCommand.equals(viewWeddingOneCommand));

        // same values -> returns true
        ViewWeddingCommand deleteFirstCommandCopy = new ViewWeddingCommand(INDEX_FIRST_PERSON);
        assertTrue(viewWeddingOneCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewWeddingOneCommand.equals(1));

        // null -> returns false
        assertFalse(viewWeddingOneCommand.equals(null));

        // different person -> returns false
        assertFalse(viewWeddingOneCommand.equals(viewWeddingTwoCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewWeddingCommand viewWeddingCommand = new ViewWeddingCommand(targetIndex);
        String expected = ViewWeddingCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, viewWeddingCommand.toString());
    }


}
