package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void equals_false() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        UserPrefs otherUserPrefs = new UserPrefs();
        otherUserPrefs.setGuiSettings(new GuiSettings(5, 6, 7, 8));
        // different types -> returns false
        assertFalse(userPrefs.equals(1));
        // null -> returns false
        assertFalse(userPrefs.equals(null));
        // different gui settings -> returns false
        assertFalse(userPrefs.equals(otherUserPrefs));
    }

    @Test
    public void equals_true() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        userPrefs.setAddressBookFilePath(Path.of("addressbook.json"));
        UserPrefs otherUserPrefs = new UserPrefs();
        otherUserPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        otherUserPrefs.setAddressBookFilePath(Path.of("addressbook.json"));
        // same object -> returns true
        assert(userPrefs.equals(userPrefs));
        // same values -> returns true
        assert(userPrefs.equals(otherUserPrefs));
        // hashCode() same -> returns true
        assert(userPrefs.hashCode() == otherUserPrefs.hashCode());
    }
}
