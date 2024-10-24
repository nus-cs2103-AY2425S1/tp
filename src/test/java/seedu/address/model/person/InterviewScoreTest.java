package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class InterviewScoreTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InterviewScore(null));
    }

    @Test
    public void constructor_invalidInterviewScore_throwsIllegalArgumentException() {
        String invalidInterviewScore = "";
        assertThrows(IllegalArgumentException.class, () -> new InterviewScore(invalidInterviewScore));
    }

    @Test
    public void isValidInterviewScore() {
        // null interview score
        assertThrows(NullPointerException.class, () -> InterviewScore.isValidInterviewScore(null));

        // invalid interview scores
        assertFalse(InterviewScore.isValidInterviewScore("")); // empty string
        assertFalse(InterviewScore.isValidInterviewScore(" ")); // spaces only
        assertFalse(InterviewScore.isValidInterviewScore("11")); // more than 10
        assertFalse(InterviewScore.isValidInterviewScore("1.23")); // more than 1 decimal place
        assertFalse(InterviewScore.isValidInterviewScore("pass")); // non-numeric
        assertFalse(InterviewScore.isValidInterviewScore("5.f")); // alphabets within digits
        assertFalse(InterviewScore.isValidInterviewScore("1 0")); // spaces within digits

        // valid interview scores
        assertTrue(InterviewScore.isValidInterviewScore("0.0")); // minimum score
        assertTrue(InterviewScore.isValidInterviewScore("10.0")); //maximum score
        assertTrue(InterviewScore.isValidInterviewScore("6")); // decimal place can be omitted
    }

    @Test
    public void isSameInterviewScore() {
        InterviewScore interviewScore = new InterviewScore("5.0");

        // same values -> returns true
        assertTrue(interviewScore.isSameInterviewScore(new InterviewScore("5.0")));

        // same object -> returns true
        assertTrue(interviewScore.isSameInterviewScore(interviewScore));

        // null -> returns false
        assertFalse(interviewScore.isSameInterviewScore(null));

        // different values -> returns false
        assertFalse(interviewScore.isSameInterviewScore(new InterviewScore("4.5")));
    }


    @Test
    public void equals() {
        InterviewScore interviewScore = new InterviewScore("5.5");

        // same values -> returns true
        assertTrue(interviewScore.equals(new InterviewScore("5.5")));

        // same object -> returns true
        assertTrue(interviewScore.equals(interviewScore));

        // null -> returns false
        assertFalse(interviewScore.equals(null));

        // different types -> returns false
        assertFalse(interviewScore.equals(5.5f));

        // different values -> returns false
        assertFalse(interviewScore.equals(new InterviewScore("5.0")));
    }
}
