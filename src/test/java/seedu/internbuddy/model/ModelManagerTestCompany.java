package seedu.internbuddy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.model.Model.PREDICATE_SHOW_ALL_COMPANIES;
import static seedu.internbuddy.testutil.Assert.assertThrows;
import static seedu.internbuddy.testutil.TypicalCompanies.GOOGLE;
import static seedu.internbuddy.testutil.TypicalCompanies.MICROSOFT;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.commons.core.GuiSettings;
import seedu.internbuddy.model.company.NameContainsKeywordsPredicate;
import seedu.internbuddy.testutil.AddressBookCompanyBuilder;

/**
 * Test class for ModelManagerCompany
 */
public class ModelManagerTestCompany {

    private ModelManager modelManager = new ModelManager();

    /**
     * Test for constructor
     */
    @Test
    public void constructor() {
        assertEquals(new UserPrefsCompany(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefsCompany userPrefs = new UserPrefsCompany();
        userPrefs.setAddressBookCompanyFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefsCompany oldUserPrefs = new UserPrefsCompany(userPrefs);
        userPrefs.setAddressBookCompanyFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasCompany_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCompany(null));
    }

    @Test
    public void hasCompany_companyNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasCompany(GOOGLE));
    }

    /**
     * Test for deleteCompany
     */
    @Test
    public void hasCompany_companyInAddressBook_returnsTrue() {
        modelManager.addCompany(GOOGLE);
        assertTrue(modelManager.hasCompany(GOOGLE));
    }

    @Test
    public void getFilteredCompanyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager
            .getFilteredCompanyList().remove(0));
    }

    /**
     * Test for equals method
     */
    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookCompanyBuilder()
            .withCompany(GOOGLE).withCompany(MICROSOFT).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefsCompany userPrefs = new UserPrefsCompany();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = GOOGLE.getName().fullName.split("\\s+");
        modelManager.updateFilteredCompanyList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);

        // different userPrefs -> returns false
        UserPrefsCompany differentUserPrefs = new UserPrefsCompany();
        differentUserPrefs.setAddressBookCompanyFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
