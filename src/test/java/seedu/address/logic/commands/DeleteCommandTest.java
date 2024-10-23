package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOwnerAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPetAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OWNER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PET;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OWNER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PET;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.owner.Owner;
import seedu.address.model.pet.Pet;
import seedu.address.testutil.TypicalOwners;
import seedu.address.testutil.TypicalPets;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model modelWithOwners = new ModelManager(TypicalOwners.getTypicalPawPatrol(), new UserPrefs());
    private Model modelWithPets = new ModelManager(TypicalPets.getTypicalPawPatrol(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredOwnerList_success() {
        Owner ownerToDelete = modelWithOwners.getFilteredOwnerList().get(INDEX_FIRST_OWNER.getZeroBased());
        DeleteOwnerCommand deleteOwnerCommand = new DeleteOwnerCommand(INDEX_FIRST_OWNER);

        String expectedMessage = String.format(DeleteOwnerCommand.MESSAGE_DELETE_OWNER_SUCCESS,
                Messages.format(ownerToDelete));

        ModelManager expectedModel = new ModelManager(modelWithOwners.getPawPatrol(), new UserPrefs());
        expectedModel.deleteOwner(ownerToDelete);

        assertCommandSuccess(deleteOwnerCommand, modelWithOwners, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredPetList_success() {
        Pet petToDelete = modelWithPets.getFilteredPetList().get(INDEX_FIRST_PET.getZeroBased());
        DeletePetCommand deletePetCommand = new DeletePetCommand(INDEX_FIRST_PET);

        String expectedMessage = String.format(DeletePetCommand.MESSAGE_DELETE_PET_SUCCESS,
                Messages.format(petToDelete));

        ModelManager expectedModel = new ModelManager(modelWithPets.getPawPatrol(), new UserPrefs());
        expectedModel.deletePet(petToDelete);

        assertCommandSuccess(deletePetCommand, modelWithPets, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredOwnerList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(modelWithOwners.getFilteredOwnerList().size() + 1);
        DeleteOwnerCommand deleteOwnerCommand = new DeleteOwnerCommand(outOfBoundIndex);

        assertCommandFailure(deleteOwnerCommand, modelWithOwners, Messages.MESSAGE_INVALID_OWNER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredPetList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(modelWithPets.getFilteredPetList().size() + 1);
        DeletePetCommand deletePetCommand = new DeletePetCommand(outOfBoundIndex);

        assertCommandFailure(deletePetCommand, modelWithPets, Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredOwnerList_success() {
        showOwnerAtIndex(modelWithOwners, INDEX_FIRST_OWNER);

        Owner ownerToDelete = modelWithOwners.getFilteredOwnerList().get(INDEX_FIRST_OWNER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteOwnerCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteOwnerCommand.MESSAGE_DELETE_OWNER_SUCCESS,
                Messages.format(ownerToDelete));

        Model expectedModel = new ModelManager(modelWithOwners.getPawPatrol(), new UserPrefs());
        expectedModel.deleteOwner(ownerToDelete);
        showNoOwner(expectedModel);

        assertCommandSuccess(deleteCommand, modelWithOwners, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredPetList_success() {
        showPetAtIndex(modelWithPets, INDEX_FIRST_PET);

        Pet petToDelete = modelWithPets.getFilteredPetList().get(INDEX_FIRST_PET.getZeroBased());
        DeleteCommand deleteCommand = new DeletePetCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeletePetCommand.MESSAGE_DELETE_PET_SUCCESS,
                Messages.format(petToDelete));

        Model expectedModel = new ModelManager(modelWithPets.getPawPatrol(), new UserPrefs());
        expectedModel.deletePet(petToDelete);
        showNoPet(expectedModel);

        assertCommandSuccess(deleteCommand, modelWithPets, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredOwnerList_throwsCommandException() {
        showOwnerAtIndex(modelWithOwners, INDEX_FIRST_OWNER);

        Index outOfBoundIndex = INDEX_SECOND_OWNER;
        // ensures that outOfBoundIndex is still in bounds of PawPatrol list
        assertTrue(outOfBoundIndex.getZeroBased() < modelWithOwners.getPawPatrol().getOwnerList().size());

        DeleteCommand deleteCommand = new DeleteOwnerCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, modelWithOwners, Messages.MESSAGE_INVALID_OWNER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredPetList_throwsCommandException() {
        showPetAtIndex(modelWithPets, INDEX_FIRST_PET);

        Index outOfBoundIndex = INDEX_SECOND_PET;
        // ensures that outOfBoundIndex is still in bounds of PawPatrol list
        assertTrue(outOfBoundIndex.getZeroBased() < modelWithPets.getPawPatrol().getPetList().size());

        DeleteCommand deleteCommand = new DeletePetCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, modelWithPets, Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
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
