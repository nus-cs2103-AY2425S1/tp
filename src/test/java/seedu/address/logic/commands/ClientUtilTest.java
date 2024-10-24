//package seedu.address.logic.commands;
//
//import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//
//import java.nio.file.Path;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.io.TempDir;
//
//import seedu.address.logic.Logic;
//import seedu.address.logic.LogicManager;
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.UserPrefs;
//import seedu.address.storage.JsonAddressBookStorage;
//import seedu.address.storage.JsonUserPrefsStorage;
//import seedu.address.storage.Storage;
//import seedu.address.storage.StorageManager;
//
//public class ClientUtilTest {
//
//    @TempDir
//    public Path temporaryFolder;
//    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//    JsonAddressBookStorage addressBookStorage =
//            new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
//    JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
//    StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
//    private Logic logic = new LogicManager(model, storage);
//    @Test
//    public void findViewPerson() {
//
//        //
//    }
//}
