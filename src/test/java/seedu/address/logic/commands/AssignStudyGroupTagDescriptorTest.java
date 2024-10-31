package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_3A;
import static seedu.address.logic.commands.CommandTestUtil.DESC_3B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDY_GROUP_TAG_2B;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignCommand.AssignStudyGroupTagDescriptor;
import seedu.address.testutil.AssignStudyGroupTagDescriptorBuilder;

public class AssignStudyGroupTagDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        AssignStudyGroupTagDescriptor descriptorWithSameValues = new AssignStudyGroupTagDescriptor(DESC_3A);
        assertTrue(DESC_3A.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_3A.equals(DESC_3A));

        // null -> returns false
        assertFalse(DESC_3A.equals(null));

        // different types -> returns false
        assertFalse(DESC_3A.equals(5));

        // different values -> returns false
        assertFalse(DESC_3A.equals(DESC_3B));

        // different name -> returns false
        AssignStudyGroupTagDescriptor edited1A = new AssignStudyGroupTagDescriptorBuilder(DESC_3A)
                .withStudyGroupTag(VALID_STUDY_GROUP_TAG_2B).build();
        assertFalse(DESC_3A.equals(edited1A));
    }

    @Test
    public void toStringMethod() {
        AssignStudyGroupTagDescriptor assignStudyGroupTagDescriptor = new AssignStudyGroupTagDescriptor();
        String expected = AssignStudyGroupTagDescriptor.class.getCanonicalName() + "{study groups to assign="
                + assignStudyGroupTagDescriptor.getStudyGroupTag() + "}";
        assertEquals(expected, assignStudyGroupTagDescriptor.toString());
    }
}
