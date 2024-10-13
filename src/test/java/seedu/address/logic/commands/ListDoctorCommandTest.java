package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_DOCTORS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.KENNEDY;
import static seedu.address.testutil.TypicalPersons.KAREN;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithDoctors;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.doctor.DoctorPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ListDoctorCommand}.
 */
public class ListDoctorCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // Model with doctors inside
    private final Model modelDoctors = new ModelManager(getTypicalAddressBookWithDoctors(), new UserPrefs());
    private final Model expectedModelDoctors = new ModelManager(getTypicalAddressBookWithDoctors(), new UserPrefs());

    /**
     * Tests whether the {@code ListDoctorCommand} equals method works as expected.
     * Two commands should be equal if they are the same object or if they are both instances of
     * {@code ListDoctorCommand}.
     */
    @Test
    public void equals() {
        ListDoctorCommand listDoctorCommand = new ListDoctorCommand();
        ListDoctorCommand listDoctorCommandCopy = new ListDoctorCommand();
        // same values -> returns true
        assertEquals(listDoctorCommand, listDoctorCommandCopy);
        assertNotEquals(listDoctorCommand, null); // Null check -> returns false

    }

    @Test
    public void execute_zeroDoctorsFound() {
        String expectedMessage = String.format(MESSAGE_DOCTORS_LISTED_OVERVIEW, 0);
        DoctorPredicate predicate = new DoctorPredicate();
        ListDoctorCommand command = new ListDoctorCommand();
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleDoctorsFound() {
        String expectedMessage = String.format(MESSAGE_DOCTORS_LISTED_OVERVIEW, 2);
        DoctorPredicate predicate = new DoctorPredicate();
        ListDoctorCommand command = new ListDoctorCommand();
        expectedModelDoctors.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, modelDoctors, expectedMessage, expectedModelDoctors);
        assertEquals(Arrays.asList(KENNEDY, KAREN), modelDoctors.getFilteredPersonList());
    }

    /**
     * Tests if {@code ListDoctorCommand} toString method returns the expected string.
     */
    @Test
    public void toString_validCommand_returnsExpectedString() {
        ListDoctorCommand command = new ListDoctorCommand();
        String expectedString = new ToStringBuilder(command)
                .add("commandWord", ListDoctorCommand.COMMAND_WORD)
                .toString();
        assertEquals(expectedString, command.toString()); // Check if the string matches
    }
}
