package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GLASTONBURY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_GLASTONBURY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_GLASTONBURY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ADELE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditConcertCommand.EditConcertDescriptor;
import seedu.address.testutil.EditConcertDescriptorBuilder;

public class EditConcertDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditConcertDescriptor descriptorWithSameValues = new EditConcertDescriptor(DESC_COACHELLA);
        assertTrue(DESC_COACHELLA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_COACHELLA.equals(DESC_COACHELLA));

        // null -> returns false
        assertFalse(DESC_COACHELLA.equals(null));

        // different types -> returns false
        assertFalse(DESC_COACHELLA.equals(5));

        // different values -> returns false
        assertFalse(DESC_COACHELLA.equals(DESC_GLASTONBURY));

        // different name -> returns false
        EditConcertDescriptor editedCoachella = new EditConcertDescriptorBuilder(DESC_COACHELLA)
                .withName(VALID_NAME_ADELE).build();
        assertFalse(DESC_COACHELLA.equals(editedCoachella));

        // different address -> returns false
        editedCoachella = new EditConcertDescriptorBuilder(DESC_COACHELLA)
                .withAddress(VALID_ADDRESS_GLASTONBURY).build();
        assertFalse(DESC_COACHELLA.equals(editedCoachella));

        // different dates -> returns false
        editedCoachella = new EditConcertDescriptorBuilder(DESC_COACHELLA)
                .withDate(VALID_DATE_GLASTONBURY).build();
        assertFalse(DESC_COACHELLA.equals(editedCoachella));
    }

    @Test
    public void toStringMethod() {
        EditConcertDescriptor editConcertDescriptor = new EditConcertDescriptor();
        String expected = EditConcertDescriptor.class.getCanonicalName() + "{name="
                + editConcertDescriptor.getName().orElse(null) + ", address="
                + editConcertDescriptor.getAddress().orElse(null) + ", date="
                + editConcertDescriptor.getDate().orElse(null) + "}";
        assertEquals(expected, editConcertDescriptor.toString());
    }
}
