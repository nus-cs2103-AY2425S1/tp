package spleetwaise.commons.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.commons.core.GuiSettings;
import spleetwaise.commons.testutil.Assert;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.TransactionBookModelManager;

public class CommonModelManagerTest {

    @Test
    void constructor() {
        TransactionBookModel tbModel = new TransactionBookModelManager();
        AddressBookModel abModel = new AddressBookModelManager();

        // test constructor values
        CommonModelManager.initialise(abModel, tbModel);
        assertEquals(abModel.getAddressBook(), CommonModelManager.getInstance().getAddressBook());
        assertEquals(tbModel.getTransactionBook(), CommonModelManager.getInstance().getTransactionBook());
        assertEquals(new UserPrefs(), CommonModelManager.getInstance().getUserPrefs());
        assertEquals(new GuiSettings(), CommonModelManager.getInstance().getGuiSettings());

        CommonModelManager.initialise(abModel, tbModel, new UserPrefs());
        assertEquals(abModel.getAddressBook(), CommonModelManager.getInstance().getAddressBook());
        assertEquals(tbModel.getTransactionBook(), CommonModelManager.getInstance().getTransactionBook());
        assertEquals(new UserPrefs(), CommonModelManager.getInstance().getUserPrefs());
        assertEquals(new GuiSettings(), CommonModelManager.getInstance().getGuiSettings());

        assertThrows(NullPointerException.class, () -> CommonModelManager.initialise(abModel, tbModel, null));
    }

    @Test
    void shouldBeSingleton() {
        TransactionBookModel tbModel = new TransactionBookModelManager();
        AddressBookModel abModel = new AddressBookModelManager();

        CommonModelManager.initialise(abModel, tbModel);

        CommonModelManager x = CommonModelManager.getInstance();
        CommonModelManager y = CommonModelManager.getInstance();
        CommonModelManager z = CommonModelManager.getInstance();

        assertEquals(x.hashCode(), y.hashCode());
        assertEquals(y.hashCode(), z.hashCode());
    }

    @Test
    void getInstanceDeinitialised() {
        CommonModelManager.deinitialise();
        assertThrows(NullPointerException.class, CommonModelManager::getInstance);
    }

    @Test
    void nullInitialisation() {
        CommonModelManager.initialise(null, null);
        CommonModelManager model = CommonModelManager.getInstance();
        assertThrows(NullPointerException.class, model::getAddressBook);
        assertThrows(NullPointerException.class, model::getTransactionBook);
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> CommonModelManager.getInstance().setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        CommonModelManager.initialise(null, null);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));

        CommonModelManager.getInstance().setUserPrefs(userPrefs);
        assertEquals(userPrefs, CommonModelManager.getInstance().getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, CommonModelManager.getInstance().getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> CommonModelManager.getInstance().setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        CommonModelManager.initialise(null, null);
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        CommonModelManager.getInstance().setGuiSettings(guiSettings);
        assertEquals(guiSettings, CommonModelManager.getInstance().getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(
                NullPointerException.class, () -> CommonModelManager.getInstance().setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        CommonModelManager.initialise(null, null);
        Path path = Paths.get("address/book/file/path");
        CommonModelManager.getInstance().setAddressBookFilePath(path);
        assertEquals(path, CommonModelManager.getInstance().getAddressBookFilePath());
    }
}
