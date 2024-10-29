package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;



public class SubjectTest {

    private static final Set<Subject> SUBJECT_ARRAY = Set.of(
            new Subject(VALID_SUBJECT_ENGLISH));

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidSubjectName_throwsIllegalArgumentException() {
        String invalidSubjectName = "";
        assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubjectName));
    }

    @Test
    public void isValidSubjectName_nullSubjectName_failure() {
        // null subject name
        assertThrows(NullPointerException.class, () -> Subject.isValidSubjectName(null));
    }

    @Test
    public void isValidSubjectName_validSubjectName_success() {
        assertTrue(Subject.isValidSubjectName("MATH"));
        assertTrue(Subject.isValidSubjectName("mATh"));
        assertTrue(Subject.isValidSubjectName("math"));
    }

    @Test
    public void isValidSubjectNameByLevel_invalidLevel_failure() {
        assertFalse(Subject.isValidSubjectNameByLevel(
                new Level("NONE NONE"), VALID_SUBJECT_ENGLISH));
    }

    @Test
    public void isValidSubjectNameByLevel_validLevel_success() {
        assertTrue(Subject.isValidSubjectNameByLevel(
                new Level("S1 EXPRESS"), VALID_SUBJECT_ENGLISH));
    }

    @Test
    public void isValidSubjectNameByLevel_invalidSubject_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                Subject.isValidSubjectNameByLevel(new Level("S1 EXPRESS"), INVALID_SUBJECT));
    }

    @Test
    public void isValidSubjectNameByLevel_validSubject_success() {
        assertTrue(Subject.isValidSubjectNameByLevel(new Level("S1 EXPRESS"), VALID_SUBJECT_MATH));
    }
    @Test
    public void isValidSubjectsByLevel_invalidLevel_failure() {
        assertFalse(Subject.isValidSubjectsByLevel(new Level("NONE NONE"), SUBJECT_ARRAY));
    }

    @Test
    public void subject_case_insensitive() {
        assertTrue(new Subject("MATH").equals(new Subject("math")));
    }

    @Test
    public void toStringTest() {
        Subject s = new Subject("MATH");
        Subject s2 = new Subject("math");
        Subject s3 = new Subject("mAtH");
        String expected = "[MATH]";
        assertEquals(s.toString(), expected);
        assertEquals(s2.toString(), expected);
        assertEquals(s3.toString(), expected);
    }
}
