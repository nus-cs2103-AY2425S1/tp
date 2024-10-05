package seedu.internbuddy.model;

import static seedu.internbuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Contains tests for UserPrefsCompany.
 */
public class UserPrefsTestCompany {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefsCompany userPref = new UserPrefsCompany();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookCompanyFilePath_nullPath_throwsNullPointerException() {
        UserPrefsCompany userPrefs = new UserPrefsCompany();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookCompanyFilePath(null));
    }

}
