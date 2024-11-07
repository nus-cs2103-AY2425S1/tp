package seedu.address.model.employee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmployeeIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EmployeeId(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidId = "";
        assertThrows(IllegalArgumentException.class, () -> new EmployeeId(invalidId));
    }

    @Test
    public void isValidId() {
        // EP: null id
        assertThrows(AssertionError.class, () -> EmployeeId.isValidId(null));

        // EP: invalid id
        assertFalse(EmployeeId.isValidId("")); // empty string
        assertFalse(EmployeeId.isValidId(" ")); // spaces only
        assertFalse(EmployeeId.isValidId("^")); // only non-alphanumeric characters
        assertFalse(EmployeeId.isValidId("5252*")); // contains non-alphanumeric characters
        assertFalse(EmployeeId.isValidId("abac gjku")); // alphabets only
        assertFalse(EmployeeId.isValidId("kk12658j")); // alphanumeric characters
        assertFalse(EmployeeId.isValidId("A0276123J20")); // with capital letters
        assertFalse(EmployeeId.isValidId("A0276123J20 A2552 6456 R20")); // long names

        // EP: valid id
        assertTrue(EmployeeId.isValidId("12345")); // numbers only
    }

    @Test
    public void equals() {
        EmployeeId employeeId = new EmployeeId("123");

        // same values -> returns true
        assertTrue(employeeId.equals(new EmployeeId("123")));

        // same object -> returns true
        assertTrue(employeeId.equals(employeeId));

        // null -> returns false
        assertFalse(employeeId.equals(null));

        // different types -> returns false
        assertFalse(employeeId.equals(5.0f));

        // different values -> returns false
        assertFalse(employeeId.equals(new EmployeeId("456")));
    }
}
