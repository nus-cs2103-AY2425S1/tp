package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;

public class UserPrefsTest {

    private final UserPrefs userPrefs = new UserPrefs();

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userPrefs.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void setAssignmentFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userPrefs.setAssignmentFilePath(null));
    }

    @Test
    public void setTutorialFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userPrefs.setTutorialFilePath(null));
    }

    @Test
    public void setAssignmentFilePath_validPath_success() {
        Path path = Paths.get("data", "assignments.json");
        userPrefs.setAssignmentFilePath(path);
        assertEquals(path, userPrefs.getAssignmentFilePath());
    }

    @Test
    public void setTutorialFilePath_validPath_success() {
        Path path = Paths.get("data", "tutorials.json");
        userPrefs.setTutorialFilePath(path);
        assertEquals(path, userPrefs.getTutorialFilePath());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertEquals(userPrefs, userPrefs);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertNotEquals(userPrefs, new Object());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        UserPrefs copy = new UserPrefs(userPrefs);
        assertEquals(userPrefs, copy);
    }

    @Test
    public void equals_differentGuiSettings_returnsFalse() {
        UserPrefs modifiedPrefs = new UserPrefs();
        modifiedPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        assertNotEquals(userPrefs, modifiedPrefs);
    }

    @Test
    public void equals_differentAddressBookFilePath_returnsFalse() {
        UserPrefs modifiedPrefs = new UserPrefs();
        modifiedPrefs.setAddressBookFilePath(Paths.get("different.json"));
        assertNotEquals(userPrefs, modifiedPrefs);
    }
    @Test
    public void hashCode_sameValues_returnsSameHashCode() {
        UserPrefs copy = new UserPrefs(userPrefs);
        assertEquals(userPrefs.hashCode(), copy.hashCode());
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHashCode() {
        UserPrefs modifiedPrefs = new UserPrefs();
        modifiedPrefs.setAddressBookFilePath(Paths.get("different.json"));
        assertNotEquals(userPrefs.hashCode(), modifiedPrefs.hashCode());
    }

    @Test
    public void toString_containsRelevantInfo() {
        String expectedString = "Gui Settings : " + userPrefs.getGuiSettings()
                + "\nLocal data file location : " + userPrefs.getAddressBookFilePath();
        assertEquals(expectedString, userPrefs.toString());
    }
}
