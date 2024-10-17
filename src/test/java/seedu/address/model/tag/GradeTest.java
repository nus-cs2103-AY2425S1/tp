package seedu.address.model.tag;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GradeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Grade(null));
    }

    @Test
    public void constructor_invalidGradeIndex_throwsIllegalArgumentException() {
        String invalidGradeIndex = "";
        assertThrows(IllegalArgumentException.class, () -> new Grade(invalidGradeIndex));
    }

    @Test
    public void isValidGradeName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Grade.isValidGradeName(null));
    }
}
