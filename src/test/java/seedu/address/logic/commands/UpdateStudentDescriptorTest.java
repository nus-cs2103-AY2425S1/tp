package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateCommand.UpdateStudentDescriptor;
import seedu.address.testutil.UpdateStudentDescriptorBuilder;

public class UpdateStudentDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        UpdateStudentDescriptor descriptorWithSameValues =
                new UpdateStudentDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        UpdateStudentDescriptor updatedAmy =
                new UpdateStudentDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(updatedAmy));

        // different phone -> returns false
        updatedAmy = new UpdateStudentDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(updatedAmy));

        // different address -> returns false
        updatedAmy = new UpdateStudentDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(updatedAmy));

        // different subjects -> returns false
        updatedAmy = new UpdateStudentDescriptorBuilder(DESC_AMY).withSubjects(VALID_SUBJECT_MATH).build();
        assertFalse(DESC_AMY.equals(updatedAmy));

        updatedAmy = new UpdateStudentDescriptorBuilder(DESC_AMY).withLessonTimes(VALID_LESSON_TIME).build();
        assertFalse(DESC_AMY.equals(updatedAmy));
    }

    @Test
    public void toStringMethod() {
        UpdateStudentDescriptor updateStudentDescriptor = new UpdateStudentDescriptor();
        String expected = UpdateStudentDescriptor.class.getCanonicalName() + "{name="
                + updateStudentDescriptor.getName().orElse(null) + ", phone="
                + updateStudentDescriptor.getPhone().orElse(null) + ", emergencyContact="
                + updateStudentDescriptor.getEmergencyContact().orElse(null) + ", address="
                + updateStudentDescriptor.getAddress().orElse(null) + ", note="
                + updateStudentDescriptor.getNote().orElse(null) + ", level="
                + updateStudentDescriptor.getLevel().orElse(null) + ", subjects="
                + updateStudentDescriptor.getSubjects().orElse(null) + ", task list="
                + updateStudentDescriptor.getTaskList().orElse(null) + ", lesson times="
                + updateStudentDescriptor.getLessonTimes().orElse(null) + "}";
        assertEquals(expected, updateStudentDescriptor.toString());
    }
}
