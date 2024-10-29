package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.listing.exceptions.ListingNotFoundException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setListingsFilePath(Paths.get("listings/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setListingsFilePath(Paths.get("new/listings/file/path"));
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
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void deletePerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deletePerson(null));
    }

    @Test
    public void deletePerson_personNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.deletePerson(ALICE));
    }

    @Test
    public void deletePerson_personInAddressBook_success() {
        modelManager.addPerson(ALICE);
        modelManager.deletePerson(ALICE);
        ModelManager expected = new ModelManager();
        assertEquals(modelManager, expected);
    }

    @Test
    public void addPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addPerson(null));
    }

    @Test
    public void setPerson_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.setPerson(ALICE, ALICE));
    }

    @Test
    public void setListingsFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setListingsFilePath(null));
    }

    @Test
    public void setListingsFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("listings/file/path");
        modelManager.setListingsFilePath(path);
        assertEquals(path, modelManager.getListingsFilePath());
    }

    @Test
    public void hasListing_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasListing(null));
    }

    @Test
    public void hasListing_listingNotInListings_returnsFalse() {
        assertFalse(modelManager.hasListing(PASIR_RIS));
    }

    @Test
    public void hasListing_listingInListings_returnsTrue() {
        modelManager.addListing(PASIR_RIS);
        assertTrue(modelManager.hasListing(PASIR_RIS));
    }

    @Test
    public void deleteListing_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteListing(null));
    }

    @Test
    public void deleteListing_listingNotInListings_throwsListingNotFoundException() {
        assertThrows(ListingNotFoundException.class, () -> modelManager.deleteListing(PASIR_RIS));
    }

    @Test
    public void deleteListing_listingInListings_success() {
        modelManager.addListing(PASIR_RIS);
        modelManager.deleteListing(PASIR_RIS);
        ModelManager expected = new ModelManager();
        assertEquals(modelManager, expected);
    }

    @Test
    public void addListing_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addListing(null));
    }

    @Test
    public void setListing_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setListing(null, PASIR_RIS));
    }

    @Test
    public void setListing_nullEditedListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setListing(PASIR_RIS, null));
    }

    @Test
    public void setListing_targetNotInListings_throwsListingNotFoundException() {
        assertThrows(ListingNotFoundException.class, () -> modelManager.setListing(PASIR_RIS, PASIR_RIS));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getPersonByName_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.getPersonByName(null));
    }

    @Test
    public void getPersonByName_personNotFound_returnNull() {
        assertNull(modelManager.getPersonByName(new Name("Jake")));
    }

    @Test
    public void getPersonByName_personFound_returnPerson() {
        modelManager.addPerson(ALICE);
        assertEquals(ALICE, modelManager.getPersonByName(ALICE.getName()));
    }

    @Test
    public void getFilteredListingsList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredListingList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        Listings listings = new Listings();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, listings);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, listings);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs, listings)));

        // different filteredPersonList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, listings)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs, listings)));

        // different listings -> returns false
        Listings differentListings = new Listings();
        differentListings.addListing(PASIR_RIS);
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, differentListings)));
    }
}
