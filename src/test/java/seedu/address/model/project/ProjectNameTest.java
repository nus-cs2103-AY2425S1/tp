package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProjectNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProjectName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new ProjectName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> ProjectName.isValidName(null));

        // invalid name
        assertFalse(ProjectName.isValidName("")); // empty string
        assertFalse(ProjectName.isValidName(" ")); // spaces only
        assertFalse(ProjectName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(ProjectName.isValidName("project*")); // contains non-alphanumeric chars

        // valid name
        assertTrue(ProjectName.isValidName("project alpha")); // alphabets only
        assertTrue(ProjectName.isValidName("12345")); // numbers only
        assertTrue(ProjectName.isValidName("project alpha 2")); // alphanumeric characters
        assertTrue(ProjectName.isValidName("Capital Project A")); // with capital letters
        assertTrue(ProjectName.isValidName(
                "Capital Project A Version 5 Revision 2")); // long names
    }

    @Test
    public void equals() {
        ProjectName projectName = new ProjectName("Valid Name");

        // same values -> returns true
        assertTrue(projectName.equals(new ProjectName("Valid Name")));

        // same object -> returns true
        assertTrue(projectName.equals(projectName));

        // null -> returns false
        assertFalse(projectName.equals(null));

        // different types -> returns false
        assertFalse(projectName.equals(5.0f));

        // different values -> returns false
        assertFalse(projectName.equals(new ProjectName("Other Valid Name")));
    }
}
