// package seedu.address.model;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUYERS;
// import static seedu.address.testutil.Assert.assertThrows;
// import static seedu.address.testutil.buyer.TypicalBuyers.ALICE;
// import static seedu.address.testutil.buyer.TypicalBuyers.BENSON;
// import static seedu.address.testutil.meetup.TypicalMeetUps.NETWORKING_MEETUP;
// import static seedu.address.testutil.meetup.TypicalMeetUps.PITCH_MEETUP;

// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.Arrays;

// import org.junit.jupiter.api.Test;

// import seedu.address.commons.core.GuiSettings;
// import seedu.address.model.buyer.NameContainsKeywordsPredicate;
// import seedu.address.testutil.buyer.BuyerListBuilder;
// import seedu.address.testutil.meetup.MeetUpListBuilder;

// public class ModelManagerTest {

//     private ModelManager modelManager = new ModelManager();

//     @Test
//     public void constructor() {
//         assertEquals(new UserPrefs(), modelManager.getUserPrefs());
//         assertEquals(new GuiSettings(), modelManager.getGuiSettings());
//         assertEquals(new BuyerList(), new BuyerList(modelManager.getBuyerList()));
//     }

//     @Test
//     public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
//     }

//     @Test
//     public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
//         UserPrefs userPrefs = new UserPrefs();
//         userPrefs.setBuyerListFilePath(Paths.get("address/book/file/path"));
//         userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
//         modelManager.setUserPrefs(userPrefs);
//         assertEquals(userPrefs, modelManager.getUserPrefs());

//         // Modifying userPrefs should not modify modelManager's userPrefs
//         UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
//         userPrefs.setBuyerListFilePath(Paths.get("new/address/book/file/path"));
//         assertEquals(oldUserPrefs, modelManager.getUserPrefs());
//     }

//     @Test
//     public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
//     }

//     @Test
//     public void setGuiSettings_validGuiSettings_setsGuiSettings() {
//         GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
//         modelManager.setGuiSettings(guiSettings);
//         assertEquals(guiSettings, modelManager.getGuiSettings());
//     }

//     @Test
//     public void setBuyerListFilePath_nullPath_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> modelManager.setBuyerListFilePath(null));
//     }

//     @Test
//     public void setBuyerListFilePath_validPath_setsBuyerListFilePath() {
//         Path path = Paths.get("address/book/file/path");
//         modelManager.setBuyerListFilePath(path);
//         assertEquals(path, modelManager.getBuyerListFilePath());
//     }

//     @Test
//     public void setMeetUpListFilePath_nullPath_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> modelManager.setMeetUpListFilePath(null));
//     }

//     @Test
//     public void setMeetUpListFilePath_validPath_setsMeetUpListFilePath() {
//         Path path = Paths.get("meet/up/list/file/path");
//         modelManager.setMeetUpListFilePath(path);
//         assertEquals(path, modelManager.getMeetUpListFilePath());
//     }

//     @Test
//     public void hasBuyer_nullBuyer_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> modelManager.hasBuyer(null));
//     }

//     @Test
//     public void hasBuyer_buyerNotInBuyerList_returnsFalse() {
//         assertFalse(modelManager.hasBuyer(ALICE));
//     }

//     @Test
//     public void hasBuyer_buyerInBuyerList_returnsTrue() {
//         modelManager.addBuyer(ALICE);
//         assertTrue(modelManager.hasBuyer(ALICE));
//     }

//     @Test
//     public void hasMeetUp_nullMeetUp_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> modelManager.hasMeetUp(null));
//     }

//     @Test
//     public void hasMeetUp_meetUpNotInMeetUpList_returnsFalse() {
//         assertFalse(modelManager.hasMeetUp(PITCH_MEETUP));
//     }

//     @Test
//     public void hasMeetUp_meetUpInMeetUpList_returnsTrue() {
//         modelManager.addMeetUp(PITCH_MEETUP);
//         assertTrue(modelManager.hasMeetUp(PITCH_MEETUP));
//     }

//     @Test
//     public void getFilteredBuyerList_modifyList_throwsUnsupportedOperationException() {
//         assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredBuyerList().remove(0));
//     }

//     @Test
//     public void equals() {
//         BuyerList buyerList = new BuyerListBuilder().withBuyer(ALICE).withBuyer(BENSON).build();
//         BuyerList differentBuyerList = new BuyerList();
//         UserPrefs userPrefs = new UserPrefs();
//         MeetUpList meetUpList = new MeetUpListBuilder().withMeetUp(PITCH_MEETUP)
// .withMeetUp(NETWORKING_MEETUP).build();
//         MeetUpList differentMeetUpList = new MeetUpList();

//         // same values -> returns true
//         modelManager = new ModelManager(buyerList, userPrefs, meetUpList);
//         ModelManager modelManagerCopy = new ModelManager(buyerList, userPrefs, meetUpList);
//         assertTrue(modelManager.equals(modelManagerCopy));

//         // same object -> returns true
//         assertTrue(modelManager.equals(modelManager));

//         // null -> returns false
//         assertFalse(modelManager.equals(null));

//         // different types -> returns false
//         assertFalse(modelManager.equals(5));

//         // different buyerList -> returns false
//         assertFalse(modelManager.equals(new ModelManager(differentBuyerList, userPrefs, meetUpList)));

//         // different meetUpList -> returns false
//         assertFalse(modelManager.equals(new ModelManager(buyerList, userPrefs, differentMeetUpList)));

//         // different filteredList -> returns false
//         String[] keywords = ALICE.getName().fullName.split("\\s+");
//         modelManager.updateFilteredBuyerList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
//         assertFalse(modelManager.equals(new ModelManager(buyerList, userPrefs, meetUpList)));

//         // resets modelManager to initial state for upcoming tests
//         modelManager.updateFilteredBuyerList(PREDICATE_SHOW_ALL_BUYERS);

//         // different userPrefs -> returns false
//         UserPrefs differentUserPrefs = new UserPrefs();
//         differentUserPrefs.setBuyerListFilePath(Paths.get("differentFilePath"));
//         assertFalse(modelManager.equals(new ModelManager(buyerList, differentUserPrefs, meetUpList)));
//     }
// }
