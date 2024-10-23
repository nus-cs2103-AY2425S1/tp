package seedu.address.model.exam;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_FINAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_MIDTERM;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ExamTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Exam(null));
    }

    @Test
    public void constructor_invalidExam_throwsIllegalArgumentException() {
        String invalidExam = "";
        assertThrows(IllegalArgumentException.class, () -> new Exam(invalidExam));
    }

    @Test
    public void isValidExamName() {
        // null exam name
        assertThrows(NullPointerException.class, () -> Exam.isValidExamName(null));

        // invalid exam name
        assertFalse(Exam.isValidExamName("")); // empty string
        assertFalse(Exam.isValidExamName(" ")); // spaces only
        assertFalse(Exam.isValidExamName("Midterm%")); // non-alphanumeric characters

        // valid exam name
        assertTrue(Exam.isValidExamName("Midterm")); // only alphabets
        assertTrue(Exam.isValidExamName("Midterm Exam")); // alphabets with space
        assertTrue(Exam.isValidExamName("Midterm 2")); // alphabets and numbers
    }

    @Test
    public void isValidExamScore() {
        // null exam score
        assertThrows(NullPointerException.class, () -> Exam.isValidExamScore(null));

        // invalid exam score
        assertFalse(Exam.isValidExamScore("")); // empty string
        assertFalse(Exam.isValidExamScore(" ")); // spaces only
        assertFalse(Exam.isValidExamScore("hundred")); // non-numbers
        assertFalse(Exam.isValidExamScore("100a")); // numbers mixed with letters
        assertFalse(Exam.isValidExamScore("101")); // out of range

        // valid exam score
        assertTrue(Exam.isValidExamScore("0")); // min
        assertTrue(Exam.isValidExamScore("100")); // max
        assertTrue(Exam.isValidExamScore("5")); // single digit
        assertTrue(Exam.isValidExamScore("50")); // double digit
    }

    @Test
    public void equals() {
        Exam exam = new Exam(VALID_EXAM_MIDTERM);

        // same values -> returns true
        assertTrue(exam.equals(new Exam(VALID_EXAM_MIDTERM)));

        // same object -> returns true
        assertTrue(exam.equals(exam));

        // null -> returns false
        assertFalse(exam.equals(null));

        // different types -> returns false
        assertFalse(exam.equals(5.0f));

        // different values -> returns false
        assertFalse(exam.equals(new Exam(VALID_EXAM_FINAL)));
    }
}
