package seedu.academyassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.academyassist.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.academyassist.testutil.TypicalIcs.IC_FIRST_PERSON;
import static seedu.academyassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;

import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.Messages;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;
import seedu.academyassist.model.UserPrefs;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.IcMatchesPredicate;
import seedu.academyassist.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());

    @Test
    public void execute_validIcUnfilteredList_success() {
        model.updateFilteredPersonList(new IcMatchesPredicate(IC_FIRST_PERSON));
        Person personToDelete = model.getFilteredPersonList().get(0);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        DeleteCommand deleteCommand = new DeleteCommand(IC_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getName(), personToDelete.getIc());

        ModelManager expectedModel = new ModelManager(model.getAcademyAssist(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistenceIcUnfilteredList_throwsCommandException() {
        Ic nonExistenceIc = new Ic("S0000000G");
        DeleteCommand deleteCommand = new DeleteCommand(nonExistenceIc);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NRIC);
    }

    @Test
    public void execute_validIcFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Ic firstPersonIc = new Ic("F2234567X");
        model.updateFilteredPersonList(new IcMatchesPredicate(firstPersonIc));
        Person personToDelete = model.getFilteredPersonList().get(0);

        DeleteCommand deleteCommand = new DeleteCommand(firstPersonIc);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getName(), personToDelete.getIc());

        Model expectedModel = new ModelManager(model.getAcademyAssist(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIcFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Ic nonExistenceIc = new Ic("S0000000G");

        DeleteCommand deleteCommand = new DeleteCommand(nonExistenceIc);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NRIC);
    }

    @Test
    public void equals() {
        Ic firstPersonIc = new Ic("S0000001S");
        Ic secondPersonIc = new Ic("S0000002S");


        DeleteCommand deleteFirstCommand = new DeleteCommand(firstPersonIc);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondPersonIc);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstPersonIc);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Ic targetIc = new Ic("F2234567X");;
        DeleteCommand deleteCommand = new DeleteCommand(targetIc);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIc=" + targetIc + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
