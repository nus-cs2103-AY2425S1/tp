package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_APPOINTMENT_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;

public class EditAppointmentDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditAppointmentDescriptor descriptorWithSameValues = new EditAppointmentDescriptor(DESC_APPOINTMENT_AMY);
        assertTrue(DESC_APPOINTMENT_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_APPOINTMENT_AMY.equals(DESC_APPOINTMENT_AMY));

        // null -> returns false
        assertFalse(DESC_APPOINTMENT_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_APPOINTMENT_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_APPOINTMENT_AMY.equals(DESC_APPOINTMENT_BOB));

        // different name -> returns false
        EditAppointmentDescriptor editedAmy = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_APPOINTMENT_AMY.equals(editedAmy));

        // different nric -> returns false
        editedAmy = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_AMY).withNric(VALID_NRIC_BOB).build();
        assertFalse(DESC_APPOINTMENT_AMY.equals(editedAmy));

        // different start time -> returns false
        editedAmy = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_AMY)
                .withDate(VALID_DATE_APPOINTMENT_BOB)
                .withStartTime(VALID_START_TIME_APPOINTMENT_BOB).build();
        assertFalse(DESC_APPOINTMENT_AMY.equals(editedAmy));

        // different end time -> returns false
        editedAmy = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_AMY)
                .withDate(VALID_DATE_APPOINTMENT_BOB)
                .withEndTime(VALID_END_TIME_APPOINTMENT_BOB).build();
        assertFalse(DESC_APPOINTMENT_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();
        String expected = EditAppointmentDescriptor.class.getCanonicalName() + "{name="
                + editAppointmentDescriptor.getName().orElse(null) + ", nric="
                + editAppointmentDescriptor.getNric().orElse(null) + ", startTime="
                + editAppointmentDescriptor.getStartTime().orElse(null) + ", endTime="
                + editAppointmentDescriptor.getEndTime().orElse(null) + "}";
        assertEquals(expected, editAppointmentDescriptor.toString());
    }
}
