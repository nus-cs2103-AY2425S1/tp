package seedu.address.model.tag;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudyGroupTagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudyGroupTag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new StudyGroupTag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> StudyGroupTag.isValidStudyGroupName(null));
    }

}
