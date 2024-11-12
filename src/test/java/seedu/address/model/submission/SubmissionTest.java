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

        // invalid submission names
        // EP: empty strings
        assertFalse(Submission.isValidSubmissionName("")); // empty string
        assertFalse(Submission.isValidSubmissionName(" ")); // spaces only

        // EP: non-alphanumeric characters
        assertFalse(Submission.isValidSubmissionName("Assignment%"));

        // valid submission names
        // EP: only contain alphanumeric characters and spaces
        assertTrue(Submission.isValidSubmissionName("Assignment")); // only alphabets
        assertTrue(Submission.isValidSubmissionName("Assignment 1")); // alphabets with space
        assertTrue(Submission.isValidSubmissionName("Assignment1")); // alphabets and numbers
    }

    @Test
    public void isValidSubmissionStatus() {
        // null submission status
        assertThrows(NullPointerException.class, () -> Submission.isValidSubmissionStatus(null));

        // invalid submission status
        // EP: empty strings
        assertFalse(Submission.isValidSubmissionStatus("")); // empty string
        assertFalse(Submission.isValidSubmissionStatus(" ")); // spaces only

        // EP: invalid alphabet
        assertFalse(Submission.isValidSubmissionStatus("A"));

        // EP: only numbers
        assertFalse(Submission.isValidSubmissionStatus("1"));

        // EP: lowercase values
        assertFalse(Submission.isValidSubmissionStatus("y")); // lowercase "Y"

        // EP: too long input
        assertFalse(Submission.isValidSubmissionStatus("NILL"));

        // valid submission status
        // EP: "Y", "N" or "NIL"
        assertTrue(Submission.isValidSubmissionStatus("Y")); // "Y"
        assertTrue(Submission.isValidSubmissionStatus("N")); // "N"
        assertTrue(Submission.isValidSubmissionStatus("NIL")); // "NIL"
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
