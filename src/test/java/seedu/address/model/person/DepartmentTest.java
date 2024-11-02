package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DepartmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Department(null));
    }

    @Test
    public void constructor_invalidDepartment_throwsIllegalArgumentException() {
        String invalidDepartment = "";
        assertThrows(IllegalArgumentException.class, () -> new Department(invalidDepartment));
    }

    @Test
    public void isValidDepartment() {
        // null department
        assertThrows(NullPointerException.class, () -> Department.isValidDepartment(null));

        // invalid department
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only
        assertFalse(Role.isValidRole("   ")); // even more spaces

        // valid department
        assertTrue(Department.isValidDepartment("it")); // alphabets only
        assertTrue(Department.isValidDepartment("c++ developer")); // other chars
        assertTrue(Department.isValidDepartment("12345")); // numbers only
        assertTrue(Department.isValidDepartment("2nd it")); // alphanumeric characters
        assertTrue(Department.isValidDepartment("IT")); // with capital letters
        assertTrue(Department.isValidDepartment("The best department in the office")); // long departments
    }

    @Test
    public void equals() {
        Department department = new Department("Valid Department");

        // same values -> returns true
        assertTrue(department.equals(new Department("Valid Department")));

        // same object -> returns true
        assertTrue(department.equals(department));

        // null -> returns false
        assertFalse(department.equals(null));

        // different types -> returns false
        assertFalse(department.equals(5.0f));

        // different values -> returns false
        assertFalse(department.equals(new Department("Other Valid Department")));
    }
}
