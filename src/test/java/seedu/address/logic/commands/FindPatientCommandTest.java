package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PATIENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.JANE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithPatients;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.FindPatientPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPatientCommand}. (in the future)
 */
public class FindPatientCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithPatients(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBookWithPatients(), new UserPrefs());

    @Test
    public void equals() {
        FindPatientPredicate firstPredicate =
                new FindPatientPredicate("first");
        FindPatientPredicate secondPredicate =
                new FindPatientPredicate("second");

        FindPatientCommand findFirstCommand = new FindPatientCommand(firstPredicate);
        FindPatientCommand findSecondCommand = new FindPatientCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPatientCommand findFirstCommandCopy = new FindPatientCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeyword_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, 0);
        FindPatientPredicate predicate = preparePredicate("jane john");
        FindPatientCommand command = new FindPatientCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeyword_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, 1);
        FindPatientPredicate predicate = preparePredicate("jane");
        FindPatientCommand command = new FindPatientCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(JANE), model.getFilteredPersonList());
    }

    private FindPatientPredicate preparePredicate(String userInput) {
        return new FindPatientPredicate(userInput);
    }
}
