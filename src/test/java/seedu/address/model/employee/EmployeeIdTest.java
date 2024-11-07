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
        // EP: null name
        assertThrows(AssertionError.class, () -> EmployeeId.isValidEmployeeId(null));

        // EP: invalid name
        assertFalse(EmployeeId.isValidEmployeeId("")); // empty string
        assertFalse(EmployeeId.isValidEmployeeId(" ")); // spaces only
        assertFalse(EmployeeId.isValidEmployeeId("^")); // only non-alphanumeric characters
        assertFalse(EmployeeId.isValidEmployeeId("5252*")); // contains non-alphanumeric characters
        assertFalse(EmployeeId.isValidEmployeeId("abac gjku")); // alphabets only
        assertFalse(EmployeeId.isValidEmployeeId("kk12658j")); // alphanumeric characters
        assertFalse(EmployeeId.isValidEmployeeId("A0276123J20")); // with capital letters
        assertFalse(EmployeeId.isValidEmployeeId("A0276123J20 A2552 6456 R20")); // long names

        // EP: valid name
        assertTrue(EmployeeId.isValidEmployeeId("12345")); // numbers only
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
