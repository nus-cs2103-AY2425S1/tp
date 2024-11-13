package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
    public void setFilterGoodsFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setFilterGoodsFilePath(null));
    }

    @Test
    public void setExportFilterGoodsToTrue_true_success() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setExportFilterGoodsToTrue();
        assertTrue(userPrefs.getExportFilterGoods());
    }

    @Test
    public void setExportFilterGoodsToFalse_false_success() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setExportFilterGoodsToFalse();
        assertFalse(userPrefs.getExportFilterGoods());
    }

}
