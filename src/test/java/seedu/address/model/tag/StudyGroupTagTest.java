package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudyGroupTagTest {

    @Test
    public void equals() {
        StudyGroupTag studyGroupTag = new StudyGroupTag("case");

        // same values -> returns true
        assertTrue(studyGroupTag.equals(new StudyGroupTag("case")));

        // different case -> returns true
        assertTrue(studyGroupTag.equals(new StudyGroupTag("CaSe")));

        // same object -> returns true
        assertTrue(studyGroupTag.equals(studyGroupTag));

        // null -> returns false
        assertFalse(studyGroupTag.equals(null));

        // different types -> returns false
        assertFalse(studyGroupTag.equals(5.0f));

        // different values -> returns false
        assertFalse(studyGroupTag.equals(new StudyGroupTag("Other-Study-Group-Name")));
    }

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

        // valid tag name
        assertTrue(StudyGroupTag.isValidStudyGroupName("studyGroup"));
        assertTrue(StudyGroupTag.isValidStudyGroupName("study-Group"));
        assertTrue(StudyGroupTag.isValidStudyGroupName("12345"));
        assertTrue(StudyGroupTag.isValidStudyGroupName("studyGroup12345"));

        // invalid tag name
        assertFalse(StudyGroupTag.isValidStudyGroupName("study Group")); // space within tag name
        assertFalse(StudyGroupTag.isValidStudyGroupName("study_Group")); // underscore within tag name
    }

}
