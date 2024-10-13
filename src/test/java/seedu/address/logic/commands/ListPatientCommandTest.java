package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_PATIENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.JOHN;
import static seedu.address.testutil.TypicalPersons.JANE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithPatients;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PatientPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ListPatientCommand}.
 */
public class ListPatientCommandTest {
    // Model with no patients inside (to be removed in the future)
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // Model with patients inside
    private final Model modelPatients = new ModelManager(getTypicalAddressBookWithPatients(), new UserPrefs());
    private final Model expectedModelPatients = new ModelManager(getTypicalAddressBookWithPatients(), new UserPrefs());

    /**
     * Tests whether the {@code ListPatientCommand} equals method works as expected.
     * Two commands should be equal if they are the same object or if they are both instances of
     * {@code ListPatientCommand}.
     */
    @Test
    public void equals() {
        ListPatientCommand listPatientCommand = new ListPatientCommand();
        ListPatientCommand listPatientCommandCopy = new ListPatientCommand();

        // same values -> returns true
        assertEquals(listPatientCommand, listPatientCommandCopy);
        assertNotEquals(listPatientCommand, null); // Null check -> returns false
    }

    @Test
    public void execute_zeroPatientsFound() {
        String expectedMessage = String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, 0);
        PatientPredicate predicate = new PatientPredicate();
        ListPatientCommand command = new ListPatientCommand();
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multiplePatientsFound() {
        String expectedMessage = String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, 2);
        PatientPredicate predicate = new PatientPredicate();
        ListPatientCommand command = new ListPatientCommand();
        expectedModelPatients.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, modelPatients, expectedMessage, expectedModelPatients);
        assertEquals(Arrays.asList(JOHN, JANE), modelPatients.getFilteredPersonList());
    }

    /**
     * Tests if {@code ListPatientCommand} toString method returns the expected string.
     */
    @Test
    public void toString_validCommand_returnsExpectedString() {
        ListPatientCommand command = new ListPatientCommand();
        String expectedString = new ToStringBuilder(command)
                .add("commandWord", ListPatientCommand.COMMAND_WORD)
                .toString();
        assertEquals(expectedString, command.toString()); // Check if the string matches
    }
}
