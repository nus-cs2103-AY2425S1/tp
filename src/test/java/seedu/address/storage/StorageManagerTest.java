// package seedu.address.storage;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;

// import java.nio.file.Path;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.io.TempDir;

// import seedu.address.commons.core.GuiSettings;
// import seedu.address.model.BuyerList;
// import seedu.address.model.ReadOnlyBuyerList;
// import seedu.address.model.UserPrefs;
// import seedu.address.storage.buyer.JsonBuyerListStorage;
// import seedu.address.storage.meetup.JsonMeetUpListStorage;

// public class StorageManagerTest {

//     @TempDir
//     public Path testFolder;

//     private StorageManager storageManager;

//     @BeforeEach
//     public void setUp() {
//         JsonBuyerListStorage buyerListStorage = new JsonBuyerListStorage(getTempFilePath("ab"));
//         JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
//         JsonMeetUpListStorage meetUpListStorage = new JsonMeetUpListStorage(getTempFilePath("ml"));
//         storageManager = new StorageManager(buyerListStorage, userPrefsStorage, meetUpListStorage);
//     }

//     private Path getTempFilePath(String fileName) {
//         return testFolder.resolve(fileName);
//     }

//     @Test
//     public void prefsReadSave() throws Exception {
//         /*
//          * Note: This is an integration test that verifies the StorageManager is properly wired to the
//          * {@link JsonUserPrefsStorage} class.
//          * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
//          */
//         UserPrefs original = new UserPrefs();
//         original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
//         storageManager.saveUserPrefs(original);
//         UserPrefs retrieved = storageManager.readUserPrefs().get();
//         assertEquals(original, retrieved);
//     }

//     @Test
//     public void buyerListReadSave() throws Exception {
//         /*
//          * Note: This is an integration test that verifies the StorageManager is properly wired to the
//          * {@link JsonBuyerListStorage} class.
//          * More extensive testing of UserPref saving/reading is done in {@link JsonBuyerListStorageTest} class.
//          */
//         BuyerList original = getTypicalBuyerList();
//         storageManager.saveBuyerList(original);
//         ReadOnlyBuyerList retrieved = storageManager.readBuyerList().get();
//         assertEquals(original, new BuyerList(retrieved));
//     }

//     @Test
//     public void getBuyerListFilePath() {
//         assertNotNull(storageManager.getBuyerListFilePath());
//     }

// }
