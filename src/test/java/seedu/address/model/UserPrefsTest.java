package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

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
    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();
        UserPrefs sameUserPrefs = new UserPrefs();

        userPrefs.setAddressBookFilePath(Paths.get("data/addressbook.json"));
        sameUserPrefs.setAddressBookFilePath(Paths.get("data/addressbook.json"));

        userPrefs.setListingsFilePath(Paths.get("data/listings.json"));
        sameUserPrefs.setListingsFilePath(Paths.get("data/listings.json"));

        assertTrue(userPrefs.equals(userPrefs));

        assertFalse(userPrefs.equals(null));

        assertFalse(userPrefs.equals("string"));

        assertTrue(userPrefs.equals(sameUserPrefs));

        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("data/different.json"));
        assertFalse(userPrefs.equals(differentUserPrefs));
    }

    @Test
    public void hashCodeTest() {
        UserPrefs userPrefs = new UserPrefs();
        UserPrefs sameUserPrefs = new UserPrefs();

        userPrefs.setAddressBookFilePath(Paths.get("data/addressbook.json"));
        sameUserPrefs.setAddressBookFilePath(Paths.get("data/addressbook.json"));

        userPrefs.setListingsFilePath(Paths.get("data/listings.json"));
        sameUserPrefs.setListingsFilePath(Paths.get("data/listings.json"));

        assertEquals(userPrefs.hashCode(), userPrefs.hashCode());

        assertEquals(userPrefs.hashCode(), sameUserPrefs.hashCode());

        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("data/different.json"));
        assertFalse(userPrefs.hashCode() == differentUserPrefs.hashCode());
    }
}
