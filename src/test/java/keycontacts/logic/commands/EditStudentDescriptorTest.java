package keycontacts.logic.commands;

import static keycontacts.logic.commands.CommandTestUtil.DESC_AMY;
import static keycontacts.logic.commands.CommandTestUtil.DESC_BOB;
import static keycontacts.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static keycontacts.logic.commands.CommandTestUtil.VALID_GRADE_LEVEL_BOB;
import static keycontacts.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static keycontacts.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static keycontacts.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import keycontacts.logic.commands.EditCommand.EditStudentDescriptor;
import keycontacts.testutil.EditStudentDescriptorBuilder;

public class EditStudentDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditStudentDescriptor descriptorWithSameValues = new EditStudentDescriptor(DESC_AMY);
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
        EditStudentDescriptor editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different grade level -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withGradeLevel(VALID_GRADE_LEVEL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different group -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withGroup(VALID_GROUP_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

    }

    @Test
    public void toStringMethod() {
        EditStudentDescriptor editStudentDescriptor = new EditStudentDescriptor();
        String expected = EditStudentDescriptor.class.getCanonicalName() + "{name="
                + editStudentDescriptor.getName().orElse(null) + ", phone="
                + editStudentDescriptor.getPhone().orElse(null) + ", address="
                + editStudentDescriptor.getAddress().orElse(null) + ", gradeLevel="
                + editStudentDescriptor.getGradeLevel().orElse(null) + ", group="
                + editStudentDescriptor.getGroup().orElse(null) + "}";
        assertEquals(expected, editStudentDescriptor.toString());
    }
}
