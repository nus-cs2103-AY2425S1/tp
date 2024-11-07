package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MEETING_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MEETING_TWO;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.testutil.EditMeetingDescriptorBuilder;

public class EditMeetingDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditMeetingDescriptor descriptorWithSameValues = new EditMeetingDescriptor(DESC_MEETING_ONE);
        assertTrue(DESC_MEETING_ONE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MEETING_ONE.equals(DESC_MEETING_ONE));

        // null -> returns false
        assertFalse(DESC_MEETING_ONE.equals(null));

        // different types -> returns false
        assertFalse(DESC_MEETING_ONE.equals(5));

        // different values -> returns false
        assertFalse(DESC_MEETING_ONE.equals(DESC_MEETING_TWO));

        // different name -> returns false
        EditMeetingDescriptor editedMeeting = new EditMeetingDescriptorBuilder(DESC_MEETING_ONE)
                .withName("Benson Meier").build();
        assertFalse(DESC_MEETING_ONE.equals(editedMeeting));

        // different start time -> returns false
        editedMeeting = new EditMeetingDescriptorBuilder(DESC_MEETING_ONE)
                .withStartTime("01-01-2025 10:00").build();
        assertFalse(DESC_MEETING_ONE.equals(editedMeeting));

        // different end time -> returns false
        editedMeeting = new EditMeetingDescriptorBuilder(DESC_MEETING_ONE)
                .withEndTime("01-01-2025 12:00").build();
        assertFalse(DESC_MEETING_ONE.equals(editedMeeting));

        // different location -> returns false
        editedMeeting = new EditMeetingDescriptorBuilder(DESC_MEETING_ONE)
                .withLocation("New Location").build();
        assertFalse(DESC_MEETING_ONE.equals(editedMeeting));
    }

    @Test
    public void toStringMethod() {
        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingDescriptor();
        String expected = EditMeetingDescriptor.class.getCanonicalName() + "{name="
                + editMeetingDescriptor.getName().orElse(null) + ", startTime="
                + editMeetingDescriptor.getStartTime().orElse(null) + ", endTime="
                + editMeetingDescriptor.getEndTime().orElse(null) + ", location="
                + editMeetingDescriptor.getLocation().orElse(null) + "}";
        assertEquals(expected, editMeetingDescriptor.toString());
    }
}
