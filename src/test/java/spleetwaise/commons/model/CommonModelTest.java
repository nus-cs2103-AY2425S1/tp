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

public class CommonModelTest {

    @Test
    void constructor() {
        TransactionBookModel tbModel = new TransactionBookModelManager();
        AddressBookModel abModel = new AddressBookModelManager();

        // test constructor values
        CommonModel.initialise(abModel, tbModel);
        assertEquals(abModel.getAddressBook(), CommonModel.getInstance().getAddressBook());
        assertEquals(tbModel.getTransactionBook(), CommonModel.getInstance().getTransactionBook());
        assertEquals(new UserPrefs(), CommonModel.getInstance().getUserPrefs());
        assertEquals(new GuiSettings(), CommonModel.getInstance().getGuiSettings());

        CommonModel.initialise(abModel, tbModel, new UserPrefs());
        assertEquals(abModel.getAddressBook(), CommonModel.getInstance().getAddressBook());
        assertEquals(tbModel.getTransactionBook(), CommonModel.getInstance().getTransactionBook());
        assertEquals(new UserPrefs(), CommonModel.getInstance().getUserPrefs());
        assertEquals(new GuiSettings(), CommonModel.getInstance().getGuiSettings());

        assertThrows(NullPointerException.class, () -> CommonModel.initialise(abModel, tbModel, null));
    }

    @Test
    void shouldBeSingleton() {
        TransactionBookModel tbModel = new TransactionBookModelManager();
        AddressBookModel abModel = new AddressBookModelManager();

        CommonModel.initialise(abModel, tbModel);

        CommonModel x = CommonModel.getInstance();
        CommonModel y = CommonModel.getInstance();
        CommonModel z = CommonModel.getInstance();

        assertEquals(x.hashCode(), y.hashCode());
        assertEquals(y.hashCode(), z.hashCode());
    }

    @Test
    void getInstanceDeinitialised() {
        CommonModel.deinitialise();
        assertThrows(NullPointerException.class, CommonModel::getInstance);
    }

    @Test
    void nullInitialisation() {
        CommonModel.initialise(null, null);
        CommonModel model = CommonModel.getInstance();
        assertThrows(NullPointerException.class, model::getAddressBook);
        assertThrows(NullPointerException.class, model::getTransactionBook);
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> CommonModel.getInstance().setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        CommonModel.initialise(null, null);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));

        CommonModel.getInstance().setUserPrefs(userPrefs);
        assertEquals(userPrefs, CommonModel.getInstance().getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, CommonModel.getInstance().getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> CommonModel.getInstance().setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        CommonModel.initialise(null, null);
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        CommonModel.getInstance().setGuiSettings(guiSettings);
        assertEquals(guiSettings, CommonModel.getInstance().getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> CommonModel.getInstance().setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        CommonModel.initialise(null, null);
        Path path = Paths.get("address/book/file/path");
        CommonModel.getInstance().setAddressBookFilePath(path);
        assertEquals(path, CommonModel.getInstance().getAddressBookFilePath());
    }
}
