package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class JobCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JobCode(null));
    }

    @Test
    public void constructor_invalidJobCode_throwsIllegalArgumentException() {
        String invalidJobCode = "";
        assertThrows(IllegalArgumentException.class, () -> new JobCode(invalidJobCode));
    }

    @Test
    public void isValidJobCode() {
        // null JobCode
        assertThrows(NullPointerException.class, () -> JobCode.isValidJobCode(null));

        // invalid Job Codes
        assertFalse(JobCode.isValidJobCode("")); // empty string
        assertFalse(JobCode.isValidJobCode(" ")); // spaces only

        // valid Job Codes
        assertTrue(JobCode.isValidJobCode("SWE1234"));
        assertTrue(JobCode.isValidJobCode("321")); // three characters
        assertTrue(JobCode.isValidJobCode("QAT012389472")); // long Job Code
    }

    @Test
    public void equals() {
        JobCode jobCode = new JobCode("Valid Job Code");

        // same values -> returns true
        assertTrue(jobCode.equals(new JobCode("Valid Job Code")));

        // same object -> returns true
        assertTrue(jobCode.equals(jobCode));

        // null -> returns false
        assertFalse(jobCode.equals(null));

        // different types -> returns false
        assertFalse(jobCode.equals(5.0f));

        // different values -> returns false
        assertFalse(jobCode.equals(new JobCode("Other Valid Job Code")));
    }
}
