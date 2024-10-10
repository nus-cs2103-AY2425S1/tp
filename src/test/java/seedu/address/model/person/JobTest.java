package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class JobTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Job(null));
    }

    @Test
    public void constructor_invalidJob_throwsIllegalArgumentException() {
        String invalidJob = "";
        assertThrows(IllegalArgumentException.class, () -> new Job(invalidJob));
    }

    @Test
    public void isValidJob() {
        // null job
        assertThrows(NullPointerException.class, () -> Job.isValidJob(null));

        // invalid job
        assertFalse(Job.isValidJob("")); // empty string
        assertFalse(Job.isValidJob(" ")); // spaces only
        assertFalse(Job.isValidJob("^")); // only non-alphanumeric characters
        assertFalse(Job.isValidJob("engineer*")); // contains non-alphanumeric characters

        // valid job
        assertTrue(Job.isValidJob("software developer")); // alphabets only
        assertTrue(Job.isValidJob("12345")); // numbers only
        assertTrue(Job.isValidJob("swe 2")); // alphanumeric characters
        assertTrue(Job.isValidJob("DevOps Engineer")); // with capital letters
        assertTrue(Job.isValidJob("Senior Principal DevOps Automation Specialist L3")); // long jobs
    }

    @Test
    public void isSameJob() {
        Job job = new Job("Software Engineer");

        // same values -> returns true
        assertTrue(job.isSameJob(new Job("Software Engineer")));

        // same object -> returns true
        assertTrue(job.isSameJob(job));

        // same values case insensitive -> returns true
        assertTrue(job.isSameJob(new Job("SOFTWARE ENGINEER")));

        // same values case insensitive -> returns true
        assertTrue(job.isSameJob(new Job("software engineer")));

        // null -> returns false
        assertFalse(job.isSameJob(null));

        // different values -> returns false
        assertFalse(job.isSameJob(new Job("Software Scientist")));
    }

    @Test
    public void equals() {
        Job job = new Job("Valid Job");

        // same values -> returns true
        assertTrue(job.equals(new Job("Valid Job")));

        // same object -> returns true
        assertTrue(job.equals(job));

        // null -> returns false
        assertFalse(job.equals(null));

        // different types -> returns false
        assertFalse(job.equals(5.0f));

        // different values -> returns false
        assertFalse(job.equals(new Job("Other Valid Job")));
    }
}
