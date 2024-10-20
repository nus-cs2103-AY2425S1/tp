package seedu.address.model.tag;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudyGroupTagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudyGroupTag(null));
    }

    @Test
    public void constructor_invalidStudyGroupName_throwsIllegalArgumentException() {
        String invalidStudyGroupName = "";
        assertThrows(IllegalArgumentException.class, () -> new StudyGroupTag(invalidStudyGroupName));
    }

    @Test
    public void isValidStudyGroupName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> StudyGroupTag.isValidStudyGroupName(null));
    }

}
