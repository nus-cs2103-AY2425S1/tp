package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_SCORE_PHYSICS;

import org.junit.jupiter.api.Test;

import seedu.address.model.assignment.AssignmentName;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;

public class EditAssignmentDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditAssignmentCommand.EditAssignmentDescriptor descriptorWithSameValues =
                new EditAssignmentCommand.EditAssignmentDescriptor(DESC_MATH);
        assertTrue(DESC_MATH.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MATH.equals(DESC_MATH));

        // null -> returns false
        assertFalse(DESC_MATH.equals(null));

        // different types -> returns false
        assertFalse(DESC_MATH.equals(5));

        // different values -> returns false
        assertFalse(DESC_MATH.equals(DESC_PHYSICS));

        // different name -> returns false
        EditAssignmentCommand.EditAssignmentDescriptor editedMathAssignment =
                new EditAssignmentDescriptorBuilder(DESC_MATH)
                        .withAssignmentName(new AssignmentName(VALID_ASSIGNMENT_NAME_PHYSICS))
                        .build();
        assertFalse(DESC_MATH.equals(editedMathAssignment));

        // different phone -> returns false
        editedMathAssignment = new EditAssignmentDescriptorBuilder(DESC_MATH)
                .withMaxScore(VALID_MAX_SCORE_PHYSICS).build();
        assertFalse(DESC_MATH.equals(editedMathAssignment));
    }

    @Test
    public void toStringMethod() {
        EditAssignmentCommand.EditAssignmentDescriptor editAssignmentDescriptor =
                new EditAssignmentCommand.EditAssignmentDescriptor();
        String expected = EditAssignmentCommand.EditAssignmentDescriptor.class.getCanonicalName() + "{assignmentName="
                + editAssignmentDescriptor.getName().orElse(null) + ", maxScore="
                + editAssignmentDescriptor.getMaxScore().orElse(null) + "}";
        assertEquals(expected, editAssignmentDescriptor.toString());
    }
}
