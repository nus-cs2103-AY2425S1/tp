package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void setClientBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setClientBookFilePath(null));
    }

    @Test
    public void setPropertyBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setPropertyBookFilePath(null));
    }

    @Test
    public void setMeetingBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setMeetingBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        UserPrefs userPrefs = new UserPrefs();
        Path path = Paths.get("address/book/file/path");
        userPrefs.setAddressBookFilePath(path);
        assertEquals(path, userPrefs.getAddressBookFilePath());
    }

    @Test
    public void setClientBookFilePath_validPath_setsClientBookFilePath() {
        UserPrefs userPrefs = new UserPrefs();
        Path path = Paths.get("client/book/file/path");
        userPrefs.setClientBookFilePath(path);
        assertEquals(path, userPrefs.getClientBookFilePath());
    }

    @Test
    public void setPropertyBookFilePath_validPath_setsPropertyBookFilePath() {
        UserPrefs userPrefs = new UserPrefs();
        Path path = Paths.get("property/book/file/path");
        userPrefs.setPropertyBookFilePath(path);
        assertEquals(path, userPrefs.getPropertyBookFilePath());
    }

    @Test
    public void setMeetingBookFilePath_validPath_setsMeetingBookFilePath() {
        UserPrefs userPrefs = new UserPrefs();
        Path path = Paths.get("meeting/book/file/path");
        userPrefs.setMeetingBookFilePath(path);
        assertEquals(path, userPrefs.getMeetingBookFilePath());
    }

    @Test
    public void equals() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();

        // Same object should return true
        assertEquals(userPrefs1, userPrefs1);

        // Two different objects with default values should return true
        assertEquals(userPrefs1, userPrefs2);

        // Modify one of the userPrefs and they should not be equal
        userPrefs1.setAddressBookFilePath(Paths.get("different/address/book/file/path"));
        assertNotEquals(userPrefs1, userPrefs2);
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();

        // Objects with the same values should have the same hashcode
        assertEquals(userPrefs1.hashCode(), userPrefs2.hashCode());

        // Modifying one object should result in different hashcodes
        userPrefs1.setAddressBookFilePath(Paths.get("different/address/book/file/path"));
        assertNotEquals(userPrefs1.hashCode(), userPrefs2.hashCode());
    }
}
