package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.list.GroupList;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Year;
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

        // different studentId -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withStudentId(VALID_STUDENTID_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different major -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withAddress(VALID_MAJOR_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different groups -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withGroups(VALID_GROUP_ONE).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        String expected = EditPersonDescriptor.class.getCanonicalName() + "{name="
                + editPersonDescriptor.getName().orElse(null) + ", studentId="
                + editPersonDescriptor.getStudentId().orElse(null) + ", email="
                + editPersonDescriptor.getEmail().orElse(null) + ", major="
                + editPersonDescriptor.getMajor().orElse(null) + ", year="
                + editPersonDescriptor.getYear().orElse(null) + ", groups="
                + editPersonDescriptor.getGroups().orElse(null) + "}";
        assertEquals(expected, editPersonDescriptor.toString());
    }

    @Test
    public void isAnyFieldEdited_nameFieldSet_returnsTrue() {
        // Test when only the name field is set
        EditCommand.EditPersonDescriptor descriptor = new EditCommand.EditPersonDescriptor();
        descriptor.setName(new Name("Dylan Goh"));

        // The name field is set, so it should return true
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    public void isAnyFieldEdited_someFieldsSet_returnsTrue() {
        // Test when some fields are set
        EditCommand.EditPersonDescriptor descriptor = new EditCommand.EditPersonDescriptor();
        descriptor.setName(new Name("Dylan Goh"));
        descriptor.setMajor(Major.makeMajor("Arts"));
        descriptor.setYear(Year.makeYear("3"));

        // This should return true
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    public void isAnyFieldEdited_allFieldsSet_returnsTrue() {
        // Test when all fields are set
        EditCommand.EditPersonDescriptor descriptor = new EditCommand.EditPersonDescriptor();
        descriptor.setName(new Name("Dylan Goh"));
        descriptor.setStudentId(new StudentId("A1234567B"));
        descriptor.setEmail(Email.makeEmail(""));
        descriptor.setMajor(Major.makeMajor("Arts"));
        descriptor.setYear(Year.makeYear("3"));
        descriptor.setGroups(new GroupList());

        // This should return true
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    public void isAnyFieldEdited_noFieldsSet_returnsFalse() {
        // Test when no fields are set
        EditCommand.EditPersonDescriptor descriptor = new EditCommand.EditPersonDescriptor();

        // No fields are edited, should return false
        assertFalse(descriptor.isAnyFieldEdited());
    }
}
