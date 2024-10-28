package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_WITH_RENTAL;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithRental;
import static seedu.address.testutil.TypicalRentalInformation.RENTAL_ONE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.client.Client;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RentalInformationBuilder;

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
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
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
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getSortedPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getSortedPersonList().remove(0));
    }

    @Test
    public void hasRentalInformation_nullClientAndRentalInformation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasRentalInformation(null, null));
        assertThrows(NullPointerException.class, () -> modelManager.hasRentalInformation(ALICE_WITH_RENTAL, null));
        assertThrows(NullPointerException.class, () -> modelManager.hasRentalInformation(null, RENTAL_ONE));
    }

    @Test
    public void hasRentalInformation_clientNotExist_returnsFalse() {
        assertFalse(modelManager.hasRentalInformation(CARL, RENTAL_ONE));
    }

    @Test
    public void hasRentalInformation_rentalInformationNotInClientInAddressBook_returnsFalse() {
        modelManager.addPerson(ALICE_WITH_RENTAL);
        assertFalse(modelManager.hasRentalInformation(ALICE_WITH_RENTAL, RENTAL_ONE));

        modelManager.deletePerson(ALICE_WITH_RENTAL);
        assertFalse(modelManager.hasPerson(ALICE_WITH_RENTAL));
    }

    @Test
    public void hasRentalInformation_rentalInformationInClientInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE_WITH_RENTAL);
        RentalInformation rentalInformation = new RentalInformationBuilder().withAddress("BLK 10 Ang Mo Kio")
                .withRentalStartDate("01/01/2024").withRentalEndDate("31/12/2024").withRentDueDate("10")
                .withMonthlyRent("3000").withDeposit("9000").withCustomerList("David").build();
        assertTrue(modelManager.hasRentalInformation(ALICE_WITH_RENTAL, rentalInformation));

        modelManager.deletePerson(ALICE_WITH_RENTAL);
        assertFalse(modelManager.hasPerson(ALICE_WITH_RENTAL));
    }

    @Test
    public void getLastViewClientAsObjectProperty_nil_lastViewedClientAsObjectProperty() {
        modelManager.setLastViewedClient(null);
        ObjectProperty<Client> lastViewedClient = modelManager.getLastViewedClientAsObjectProperty();
        ObjectProperty<Client> expectedClient = new SimpleObjectProperty<>();

        expectedClient.set(null);
        assertEquals(expectedClient.get(), lastViewedClient.get());

        expectedClient.set(ALICE_WITH_RENTAL);
        modelManager.setLastViewedClient(ALICE_WITH_RENTAL);
        lastViewedClient = modelManager.getLastViewedClientAsObjectProperty();
        assertEquals(expectedClient.get(), lastViewedClient.get());
    }

    @Test
    public void setPerson_updatedPerson_success() {
        Client updatedClient = new PersonBuilder(FIONA).withPhone("88888888").build();
        modelManager.addPerson(FIONA);
        assertTrue(modelManager.hasPerson(FIONA));
        modelManager.setPerson(FIONA, updatedClient);
        assertTrue(modelManager.hasPerson(updatedClient));
        assertFalse(modelManager.hasPerson(FIONA));
    }

    @Test
    public void setAddressBook_addressBookTobeSet_success() {
        AddressBook newAddressBook = getTypicalAddressBookWithRental();
        modelManager.setAddressBook(newAddressBook);
        assertEquals(newAddressBook, modelManager.getAddressBook());
    }

    @Test
    public void getLastViewClient_nil_lastViewedClient() {
        modelManager.setLastViewedClient(null);
        Client lastViewedClient = modelManager.getLastViewedClient();
        assertEquals(null, lastViewedClient);

        modelManager.setLastViewedClient(ALICE_WITH_RENTAL);
        lastViewedClient = modelManager.getLastViewedClient();
        assertEquals(ALICE_WITH_RENTAL, lastViewedClient);
    }

    @Test
    public void getVisibleRentalInformationList_initialContents_empty() {
        assertEquals(0, modelManager.getVisibleRentalInformationList().size());
    }

    @Test
    public void getPreviousCommand_initial_empty() {
        assertEquals("", modelManager.getPreviousCommand());
    }

    @Test
    public void getNextCommand_initial_empty() {
        assertEquals("", modelManager.getPreviousCommand());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

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
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
