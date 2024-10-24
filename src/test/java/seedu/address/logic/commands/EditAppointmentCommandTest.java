package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
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
    public void equals() {

        final EditAppointmentCommand standardCommand = new EditAppointmentCommand(new Nric(VALID_NRIC_AMY),
                DESC_APPOINTMENT_AMY);

        // same values -> returns true
        EditAppointmentDescriptor copyDescriptor = new EditAppointmentDescriptor(DESC_APPOINTMENT_AMY);
        EditAppointmentCommand commandWithSameValues = new EditAppointmentCommand(new Nric(VALID_NRIC_AMY),
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));
    }
}
