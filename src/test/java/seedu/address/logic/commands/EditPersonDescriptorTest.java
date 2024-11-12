package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REGISTER_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_CLASS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_AMY);
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
        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different register number -> return false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withRegisterNumber(VALID_REGISTER_NUMBER_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different sex -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withSex(VALID_SEX_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different student class -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withStudentClass(VALID_STUDENT_CLASS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different ecName -> return false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEcName(VALID_ECNAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different ecNumber -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEcNumber(VALID_ECNUMBER_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        String expected = EditPersonDescriptor.class.getCanonicalName() + "{name="
                + editPersonDescriptor.getName().orElse(null) + ", phone="
                + editPersonDescriptor.getPhone().orElse(null) + ", email="
                + editPersonDescriptor.getEmail().orElse(null) + ", address="
                + editPersonDescriptor.getAddress().orElse(null) + ", register number="
                + editPersonDescriptor.getRegisterNumber().orElse(null) + ", sex="
                + editPersonDescriptor.getSex().orElse(null) + ", class="
                + editPersonDescriptor.getStudentClass().orElse(null) + ", emergency contact name="
                + editPersonDescriptor.getEcName().orElse(null) + ", emergency contact number="
                + editPersonDescriptor.getEcNumber().orElse(null) + ", tags="
                + editPersonDescriptor.getTags().orElse(null) + ", attendances="
                + editPersonDescriptor.getAttendances().orElse(null) + "}";

        assertEquals(expected, editPersonDescriptor.toString());
    }
}
