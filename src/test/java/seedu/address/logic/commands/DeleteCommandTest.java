package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredListEmployee_success() {
        Person personToDelete = model.getFilteredPersonList().get(Index.fromOneBased(10).getZeroBased());
        DeleteCommand deleteCommand = new DeleteEmployeeCommand(Index.fromOneBased(10));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, "Employee",
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredListPotentialHire_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeletePotentialCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, "Potential Hire",
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredListEmployee_throwsTypeException() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteEmployeeCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteCommand.MESSAGE_DELETE_PERSON_WRONG_TYPE, "employee"));
    }

    @Test
    public void execute_validIndexUnfilteredListPotentialHire_throwsTypeException() {
        Person personToDelete = model.getFilteredPersonList().get(Index.fromOneBased(10).getZeroBased());
        DeleteCommand deleteCommand = new DeletePotentialCommand(Index.fromOneBased(10));

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteCommand.MESSAGE_DELETE_PERSON_WRONG_TYPE, "potential hire"));
    }

    @Test
    public void execute_invalidIndexUnfilteredEmployeeList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteEmployeeCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredPotentialHireList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeletePotentialCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredListEmployee_success() {
        showPersonAtIndex(model, Index.fromOneBased(10));

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteEmployeeCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, "Employee",
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredListPotentialHire_success() {
        showPersonAtIndex(model, Index.fromOneBased(3));

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeletePotentialCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, "Potential Hire",
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidOverIndexFilteredListEmployee_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteEmployeeCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidOverIndexFilteredListPotentialHire_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeletePotentialCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidUnderIndexFilteredListEmployee_throwsCommandException() {
        try {
            showPersonAtIndex(model, INDEX_FIRST_PERSON);

            Index outOfBoundIndex = Index.fromOneBased(0);
            DeleteCommand deleteCommand = new DeleteEmployeeCommand(outOfBoundIndex);
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("execute_invalidUnderIndexFilteredListEmployee_throwsCommandException - test failed");
        }
    }

    @Test
    public void execute_invalidUnderIndexFilteredListPotentialHire_throwsCommandException() {
        try {
            showPersonAtIndex(model, INDEX_FIRST_PERSON);

            Index outOfBoundIndex = Index.fromOneBased(0);
            DeleteCommand deleteCommand = new DeletePotentialCommand(outOfBoundIndex);
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("execute_invalidUnderIndexFilteredListEmployee_throwsCommandException - test failed");
        }
    }

    @Test
    public void constructor_nullPersonEmployee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteEmployeeCommand(null));
    }

    @Test
    public void constructor_nullPersonPotentialHire_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeletePotentialCommand(null));
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstPotentialCommand = new DeletePotentialCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondPotentialCommand = new DeletePotentialCommand(INDEX_SECOND_PERSON);
        DeleteCommand deleteFirstEmployeeCommand = new DeleteEmployeeCommand(Index.fromOneBased(10));
        DeleteCommand deleteSecondEmployeeCommand = new DeleteEmployeeCommand(Index.fromOneBased(11));

        // same object -> returns true
        assertTrue(deleteFirstPotentialCommand.equals(deleteFirstPotentialCommand));
        assertTrue(deleteFirstEmployeeCommand.equals(deleteFirstEmployeeCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandPotentialCopy = new DeletePotentialCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteFirstCommandEmployeeCopy = new DeleteEmployeeCommand(Index.fromOneBased(10));
        assertTrue(deleteFirstPotentialCommand.equals(deleteFirstCommandPotentialCopy));
        assertTrue(deleteFirstEmployeeCommand.equals(deleteFirstCommandEmployeeCopy));

        // different types -> returns false
        assertFalse(deleteFirstPotentialCommand.equals(1));
        assertFalse(deleteFirstEmployeeCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstPotentialCommand.equals(null));
        assertFalse(deleteFirstEmployeeCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstPotentialCommand.equals(deleteSecondPotentialCommand));
        assertFalse(deleteFirstEmployeeCommand.equals(deleteSecondEmployeeCommand));;
        assertFalse(deleteFirstPotentialCommand.equals(deleteFirstEmployeeCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndexPotential = Index.fromOneBased(1);
        DeleteCommand deletePotentialCommand = new DeletePotentialCommand(targetIndexPotential);
        String expectedPotential = DeletePotentialCommand.class.getCanonicalName()
                + "{targetType=ph, targetIndex=" + targetIndexPotential + "}";
        assertEquals(expectedPotential, deletePotentialCommand.toString());

        Index targetIndexEmployee = Index.fromOneBased(11);
        DeleteCommand deleteEmployeeCommand = new DeleteEmployeeCommand(targetIndexEmployee);
        String expectedEmployee = DeleteEmployeeCommand.class.getCanonicalName()
                + "{targetType=e, targetIndex=" + targetIndexEmployee + "}";
        assertEquals(expectedEmployee, deleteEmployeeCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
