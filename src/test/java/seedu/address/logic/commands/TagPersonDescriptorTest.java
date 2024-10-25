package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DOE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagCommand.TagPersonDescriptor;
import seedu.address.testutil.TagPersonDescriptorBuilder;

public class TagPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        TagPersonDescriptor descriptorWithSameValues = new TagPersonDescriptor(DESC_DOE);
        assertTrue(DESC_DOE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_DOE.equals(DESC_DOE));

        // different values -> returns false
        assertFalse(DESC_DOE.equals(DESC_BOB));

        // different tag -> returns false
        TagPersonDescriptor editedDoe = new TagPersonDescriptorBuilder(DESC_DOE).withTag("colleague").build();
        assertFalse(DESC_DOE.equals(editedDoe));

        // different tag (multiple tags) -> returns false
        editedDoe = new TagPersonDescriptorBuilder(DESC_DOE).withTag("friend").build();
        assertFalse(DESC_DOE.equals(editedDoe));
    }


    @Test
    public void toStringMethod() {
        TagPersonDescriptor tagPersonDescriptor = new TagPersonDescriptor();
        String expected = TagPersonDescriptor.class.getCanonicalName()
                + "{name=null, phone=null, email=null, address=null, tags=null}";
        assertEquals(expected, tagPersonDescriptor.toString());
    }

}
