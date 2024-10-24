package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_UNIQUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_TIME_APPOINTMENT_AMY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nric;

public class EditAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditAppointmentCommand(null, null, null));
    }

    @Test
    public void equals() {

        final EditAppointmentCommand standardCommand = new EditAppointmentCommand(new Nric(VALID_NRIC_AMY),
                VALID_START_DATE_TIME_APPOINTMENT_AMY,
                DESC_APPOINTMENT_AMY);

        // same values -> returns true
        EditAppointmentDescriptor copyDescriptor = new EditAppointmentDescriptor(DESC_APPOINTMENT_AMY);
        EditAppointmentCommand commandWithSameValues = new EditAppointmentCommand(new Nric(VALID_NRIC_AMY),
                VALID_START_DATE_TIME_APPOINTMENT_AMY,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different nric -> returns false
        assertFalse(standardCommand.equals(new EditAppointmentCommand(new Nric(VALID_NRIC_BOB),
                VALID_START_DATE_TIME_APPOINTMENT_AMY, DESC_APPOINTMENT_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditAppointmentCommand(new Nric(VALID_NRIC_AMY),
                VALID_START_DATE_TIME_APPOINTMENT_AMY, DESC_APPOINTMENT_BOB)));
    }

    @Test
    public void toStringMethod() {
        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(new Nric(VALID_NRIC_UNIQUE),
                VALID_START_DATE_TIME_APPOINTMENT_AMY,
                editAppointmentDescriptor);
        String expected = EditAppointmentCommand.class.getCanonicalName() + "{nric=" + VALID_NRIC_UNIQUE
                + ", editAppointmentDescriptor="
                + editAppointmentDescriptor + "}";
        assertEquals(expected, editAppointmentCommand.toString());
    }
}
