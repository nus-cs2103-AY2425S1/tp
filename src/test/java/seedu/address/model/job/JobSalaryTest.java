package seedu.address.model.job;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class JobSalaryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JobSalary(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidSalary = "";
        assertThrows(IllegalArgumentException.class, () -> new JobSalary(invalidSalary));
    }

    @Test
    public void isValidSalary() {
        // null salary
        assertThrows(NullPointerException.class, () -> new JobSalary(null));

        // invalid salary
        assertFalse(JobSalary.isValidSalary("")); // empty string
        assertFalse(JobSalary.isValidSalary(" ")); // spaces only
        assertFalse(JobSalary.isValidSalary("two-grand")); // non-numeric
        assertFalse(JobSalary.isValidSalary("25K")); // alphabets with digits
        assertFalse(JobSalary.isValidSalary("25.00")); // symbol within digits
        assertFalse(JobSalary.isValidSalary("25 00")); // spaces within digits

        // valid salary
        assertTrue(JobSalary.isValidSalary("2500"));
    }

    @Test
    public void equals() {
        JobSalary salary = new JobSalary("2500");

        // same values -> returns true
        assertTrue(salary.equals(new JobSalary("2500")));

        // same object -> returns true
        assertTrue(salary.equals(salary));

        // null -> returns false
        assertFalse(salary.equals(null));

        // different types -> returns false
        assertFalse(salary.equals(5.0f));

        // different values -> returns false
        assertFalse(salary.equals(new JobSalary("100")));
    }
}
