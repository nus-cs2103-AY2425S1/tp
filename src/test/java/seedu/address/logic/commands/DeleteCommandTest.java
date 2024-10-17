package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OWNER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PET;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OWNER;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.owner.Owner;
import seedu.address.model.pet.Pet;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredOwnerList_success() {
        Owner ownerToDelete = model.getFilteredOwnerList().get(INDEX_FIRST_OWNER.getZeroBased());
        DeleteOwnerCommand deleteOwnerCommand = new DeleteOwnerCommand(INDEX_FIRST_OWNER);

        String expectedMessage = String.format(DeleteOwnerCommand.MESSAGE_DELETE_OWNER_SUCCESS,
                Messages.format(ownerToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteOwner(ownerToDelete);

        assertCommandSuccess(deleteOwnerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredPetList_success() {
        Pet petToDelete = model.getFilteredPetList().get(INDEX_FIRST_PET.getZeroBased());
        DeletePetCommand deletePetCommand = new DeletePetCommand(INDEX_FIRST_PET);

        String expectedMessage = String.format(DeletePetCommand.MESSAGE_DELETE_PET_SUCCESS,
                Messages.format(petToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePet(petToDelete);

        assertCommandSuccess(deletePetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredOwnerList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOwnerList().size() + 1);
        DeleteOwnerCommand deleteOwnerCommand = new DeleteOwnerCommand(outOfBoundIndex);

        assertCommandFailure(deleteOwnerCommand, model, Messages.MESSAGE_INVALID_OWNER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredPetList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPetList().size() + 1);
        DeletePetCommand deletePetCommand = new DeletePetCommand(outOfBoundIndex);

        assertCommandFailure(deletePetCommand, model, Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredOwnerList_success() {
        showPersonAtIndex(model, INDEX_FIRST_OWNER);

        Owner ownerToDelete = model.getFilteredOwnerList().get(INDEX_FIRST_OWNER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteOwnerCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteOwnerCommand.MESSAGE_DELETE_OWNER_SUCCESS,
                Messages.format(ownerToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteOwner(ownerToDelete);
        showNoOwner(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredPetList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PET);

        Pet petToDelete = model.getFilteredPetList().get(INDEX_FIRST_PET.getZeroBased());
        DeleteCommand deleteCommand = new DeletePetCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeletePetCommand.MESSAGE_DELETE_PET_SUCCESS,
                Messages.format(petToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePet(petToDelete);
        showNoPet(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredOwnerList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_OWNER);

        Index outOfBoundIndex = INDEX_SECOND_OWNER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getOwnerList().size());

        DeleteCommand deleteCommand = new DeleteOwnerCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_OWNER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredPetList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_OWNER);

        Index outOfBoundIndex = INDEX_SECOND_OWNER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPetList().size());

        DeleteCommand deleteCommand = new DeleteOwnerCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstOwnerCommand = new DeleteOwnerCommand(INDEX_FIRST_OWNER);
        DeleteCommand deleteSecondOwnerCommand = new DeleteOwnerCommand(INDEX_SECOND_OWNER);

        // same object -> returns true
        assertTrue(deleteFirstOwnerCommand.equals(deleteFirstOwnerCommand));

        // same values -> returns true
        DeleteCommand deleteFirstOwnerCommandCopy = new DeleteOwnerCommand(INDEX_FIRST_OWNER);
        assertTrue(deleteFirstOwnerCommand.equals(deleteFirstOwnerCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstOwnerCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstOwnerCommand.equals(null));

        // different owner -> returns false
        assertFalse(deleteFirstOwnerCommand.equals(deleteSecondOwnerCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteOwnerCommand = new DeleteOwnerCommand(targetIndex);
        DeleteCommand deletePetCommand = new DeletePetCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered list to show no owner.
     */
    private void showNoOwner(Model model) {
        model.updateFilteredOwnerList(p -> false);

        assertTrue(model.getFilteredOwnerList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered list to show no owner.
     */
    private void showNoPet(Model model) {
        model.updateFilteredPetList(p -> false);

        assertTrue(model.getFilteredPetList().isEmpty());
    }
}
