package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProjectIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProjectId(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidId = "";
        assertThrows(IllegalArgumentException.class, () -> new ProjectId(invalidId));
    }

    @Test
    public void isValidId() {
        // null name
        assertThrows(NullPointerException.class, () -> ProjectId.isValidId(null));

        // invalid name
        assertFalse(ProjectId.isValidId("")); // empty string
        assertFalse(ProjectId.isValidId(" ")); // spaces only
        assertFalse(ProjectId.isValidId("^")); // only non-alphanumeric characters
        assertFalse(ProjectId.isValidId("5252*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(ProjectId.isValidId("abac gjku")); // alphabets only
        assertTrue(ProjectId.isValidId("12345")); // numbers only
        assertTrue(ProjectId.isValidId("kk12658j")); // alphanumeric characters
        assertTrue(ProjectId.isValidId("A0276123J20")); // with capital letters
        assertTrue(ProjectId.isValidId("A0276123J20 A2552 6456 R20")); // long names
    }

    @Test
    public void equals() {
        ProjectId projectId = new ProjectId("Valid Id");

        // same values -> returns true
        assertTrue(projectId.equals(new ProjectId("Valid Id")));

        // same object -> returns true
        assertTrue(projectId.equals(projectId));

        // null -> returns false
        assertFalse(projectId.equals(null));

        // different types -> returns false
        assertFalse(projectId.equals(5.0f));

        // different values -> returns false
        assertFalse(projectId.equals(new ProjectId("Other Valid Name")));
    }
}
