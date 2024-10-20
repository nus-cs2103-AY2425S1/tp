package seedu.academyassist.model.person;

import org.junit.jupiter.api.Test;

import static seedu.academyassist.testutil.Assert.assertThrows;

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
    public void isValidSubjectName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));
    }
}
