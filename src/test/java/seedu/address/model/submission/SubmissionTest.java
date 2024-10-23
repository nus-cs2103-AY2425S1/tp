package seedu.address.model.submission;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_ASSIGNMENT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_TUTORIAL_1;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SubmissionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Submission(null));
    }

    @Test
    public void constructor_invalidSubmission_throwsIllegalArgumentException() {
        String invalidSubmission = "";
        assertThrows(IllegalArgumentException.class, () -> new Submission(invalidSubmission));
    }

    @Test
    public void isValidSubmissionName() {
        // null submission name
        assertThrows(NullPointerException.class, () -> Submission.isValidSubmissionName(null));

        // invalid submission name
        assertFalse(Submission.isValidSubmissionName("")); // empty string
        assertFalse(Submission.isValidSubmissionName(" ")); // spaces only
        assertFalse(Submission.isValidSubmissionName("Assignment%")); // non-alphanumeric characters

        // valid submission name
        assertTrue(Submission.isValidSubmissionName("Assignment")); // only alphabets
        assertTrue(Submission.isValidSubmissionName("Assignment 1")); // alphabets with space
        assertTrue(Submission.isValidSubmissionName("Assignment1")); // alphabets and numbers
    }

    @Test
    public void isValidSubmissionStatus() {
        // null submission status
        assertThrows(NullPointerException.class, () -> Submission.isValidSubmissionStatus(null));

        // invalid submission status
        assertFalse(Submission.isValidSubmissionStatus("")); // empty string
        assertFalse(Submission.isValidSubmissionStatus(" ")); // spaces only
        assertFalse(Submission.isValidSubmissionStatus("A")); // invalid alphabets
        assertFalse(Submission.isValidSubmissionStatus("1")); // numbers
        assertFalse(Submission.isValidSubmissionStatus("y")); // lowercase "Y"

        // valid submission status
        assertTrue(Submission.isValidSubmissionStatus("Y")); // "Y"
        assertTrue(Submission.isValidSubmissionStatus("N")); // "N"
    }

    @Test
    public void equals() {
        Submission submission = new Submission(VALID_SUBMISSION_ASSIGNMENT_1);

        // same values -> returns true
        assertTrue(submission.equals(new Submission(VALID_SUBMISSION_ASSIGNMENT_1)));

        // same object -> returns true
        assertTrue(submission.equals(submission));

        // null -> returns false
        assertFalse(submission.equals(null));

        // different types -> returns false
        assertFalse(submission.equals(5.0f));

        // different values -> returns false
        assertFalse(submission.equals(new Submission(VALID_SUBMISSION_TUTORIAL_1)));
    }
}
