package seedu.address.logic.commands.eventcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOOK_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOOK_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOOK_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_BOOK_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LITERATURE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.eventcommands.EditEventCommand.EditEventDescriptor;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventDescriptor descriptorWithSameValues = new EditEventDescriptor(DESC_ART_EXHIBIT);
        assertTrue(DESC_ART_EXHIBIT.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ART_EXHIBIT.equals(DESC_ART_EXHIBIT));

        // null -> returns false
        assertFalse(DESC_ART_EXHIBIT.equals(null));

        // different types -> returns false
        assertFalse(DESC_ART_EXHIBIT.equals(5));

        // different values -> returns false
        assertFalse(DESC_ART_EXHIBIT.equals(DESC_BOOK_FAIR));

        // different name -> returns false
        EditEventDescriptor editedArtExhibit = new EditEventDescriptorBuilder(DESC_ART_EXHIBIT)
                .withName(VALID_NAME_BOOK_FAIR).build();
        assertFalse(DESC_ART_EXHIBIT.equals(editedArtExhibit));

        // different address -> returns false
        editedArtExhibit = new EditEventDescriptorBuilder(DESC_ART_EXHIBIT)
                .withAddress(VALID_ADDRESS_BOOK_FAIR).build();
        assertFalse(DESC_ART_EXHIBIT.equals(editedArtExhibit));

        // different start time -> returns false
        editedArtExhibit = new EditEventDescriptorBuilder(DESC_ART_EXHIBIT)
                .withStartTime(VALID_START_TIME_BOOK_FAIR).build();
        assertFalse(DESC_ART_EXHIBIT.equals(editedArtExhibit));

        // different tags -> returns false
        editedArtExhibit = new EditEventDescriptorBuilder(DESC_ART_EXHIBIT).withTags(VALID_TAG_LITERATURE).build();
        assertFalse(DESC_AMY.equals(editedArtExhibit));
    }

    @Test
    public void toStringMethod() {
        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        String expected = EditEventDescriptor.class.getCanonicalName() + "{name="
                + editEventDescriptor.getName().orElse(null) + ", address="
                + editEventDescriptor.getAddress().orElse(null) + ", start time="
                + editEventDescriptor.getStartTime().orElse(null) + ", tags="
                + editEventDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editEventDescriptor.toString());
    }
}
