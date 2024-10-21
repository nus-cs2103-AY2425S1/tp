package seedu.academyassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.academyassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SubjectTest {
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
    public void isValidSubject_null_false() {
        // null subject name
        assertFalse(() -> Subject.isValidSubject(null));
    }
}
