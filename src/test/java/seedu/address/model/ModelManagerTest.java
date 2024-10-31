package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.APPLE;
import static seedu.address.testutil.TypicalDeliveries.BREAD;
import static seedu.address.testutil.TypicalSuppliers.ALICE;
import static seedu.address.testutil.TypicalSuppliers.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.exceptions.DeliveryNotFoundException;
import seedu.address.model.supplier.NameContainsKeywordsPredicate;
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
    public void hasSupplier_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasSupplier(null));
    }

    @Test
    public void hasSupplier_supplierNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasSupplier(ALICE));
    }

    @Test
    public void hasSupplier_supplierInAddressBook_returnsTrue() {
        modelManager.addSupplier(ALICE);
        assertTrue(modelManager.hasSupplier(ALICE));
    }

    @Test
    public void hasDelivery_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasDelivery(null));
    }

    @Test
    public void hasDeliveryNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasDelivery(APPLE));
    }

    @Test
    public void hasDelivery_deliveryInAddressBook_returnsTrue() {
        modelManager.addDelivery(BREAD);
        assertTrue(modelManager.hasDelivery(BREAD));
    }

    @Test
    public void deleteDelivery_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteDelivery(null));
    }

    @Test
    public void deleteDelivery_deliveryNotInAddressBook_throwsDeliveryNotFoundException() {
        assertThrows(DeliveryNotFoundException.class, () -> modelManager.deleteDelivery(APPLE));
    }

    @Test
    public void deleteDelivery_existingDelivery_removesSuccessfully() {
        modelManager.addDelivery(BREAD);
        modelManager.deleteDelivery(BREAD);
        assertFalse(modelManager.hasDelivery(BREAD));
    }

    @Test
    public void setDelivery_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setDelivery(null, APPLE));
    }

    @Test
    public void setDelivery_nullUpdatedDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setDelivery(APPLE, null));
    }

    @Test
    public void setDelivery_deliveryNotInAddressBook_throwsDeliveryNotFoundException() {
        assertThrows(DeliveryNotFoundException.class, () -> modelManager.setDelivery(APPLE, BREAD));
    }

    @Test
    public void setDelivery_existingDelivery_successfulUpdate() {
        modelManager.addDelivery(BREAD);
        Delivery updatedApple = new Delivery(
                BREAD.getDeliveryProduct(),
                BREAD.getDeliverySender(),
                Status.CANCELLED,
                BREAD.getDeliveryDate(),
                BREAD.getDeliveryCost(),
                BREAD.getDeliveryQuantity());

        modelManager.setDelivery(BREAD, updatedApple);
        assertTrue(modelManager.hasDelivery(updatedApple));
        assertFalse(modelManager.hasDelivery(BREAD));
    }

    @Test
    public void getFilteredDeliveryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredDeliveryList().remove(0));
    }

    @Test
    public void updateFilteredDeliveryList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredDeliveryList(null));
    }

    @Test
    public void getFilteredSupplierList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredSupplierList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withSupplier(ALICE).withSupplier(BENSON).build();
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
        modelManager.updateFilteredSupplierList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
