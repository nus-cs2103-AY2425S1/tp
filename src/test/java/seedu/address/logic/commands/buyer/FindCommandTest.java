// package seedu.address.logic.commands.buyer;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static seedu.address.logic.Messages.MESSAGE_BUYERS_LISTED_OVERVIEW;
// import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
// import static seedu.address.testutil.buyer.TypicalBuyers.CARL;
// import static seedu.address.testutil.buyer.TypicalBuyers.ELLE;
// import static seedu.address.testutil.buyer.TypicalBuyers.FIONA;
// import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
// import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;

// import java.util.Arrays;
// import java.util.Collections;

// import org.junit.jupiter.api.Test;

// import seedu.address.model.Model;
// import seedu.address.model.ModelManager;
// import seedu.address.model.UserPrefs;
// import seedu.address.model.buyer.NameContainsKeywordsPredicate;

// /**
//  * Contains integration tests (interaction with the Model) for {@code FindCommand}.
//  */
// public class FindCommandTest {
//     private Model model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList());
//     private Model expectedModel = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList());

//     @Test
//     public void equals() {
//         NameContainsKeywordsPredicate firstPredicate =
//                 new NameContainsKeywordsPredicate(Collections.singletonList("first"));
//         NameContainsKeywordsPredicate secondPredicate =
//                 new NameContainsKeywordsPredicate(Collections.singletonList("second"));

//         FindCommand findFirstCommand = new FindCommand(firstPredicate);
//         FindCommand findSecondCommand = new FindCommand(secondPredicate);

//         // same object -> returns true
//         assertTrue(findFirstCommand.equals(findFirstCommand));

//         // same values -> returns true
//         FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
//         assertTrue(findFirstCommand.equals(findFirstCommandCopy));

//         // different types -> returns false
//         assertFalse(findFirstCommand.equals(1));

//         // null -> returns false
//         assertFalse(findFirstCommand.equals(null));

//         // different buyer -> returns false
//         assertFalse(findFirstCommand.equals(findSecondCommand));
//     }

//     @Test
//     public void execute_zeroKeywords_noBuyerFound() {
//         String expectedMessage = String.format(MESSAGE_BUYERS_LISTED_OVERVIEW, 0);
//         NameContainsKeywordsPredicate predicate = preparePredicate(" ");
//         FindCommand command = new FindCommand(predicate);
//         expectedModel.updateFilteredBuyerList(predicate);
//         assertCommandSuccess(command, model, expectedMessage, expectedModel);
//         assertEquals(Collections.emptyList(), model.getFilteredBuyerList());
//     }

//     @Test
//     public void execute_multipleKeywords_multipleBuyersFound() {
//         String expectedMessage = String.format(MESSAGE_BUYERS_LISTED_OVERVIEW, 3);
//         NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
//         FindCommand command = new FindCommand(predicate);
//         expectedModel.updateFilteredBuyerList(predicate);
//         assertCommandSuccess(command, model, expectedMessage, expectedModel);
//         assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredBuyerList());
//     }

//     @Test
//     public void toStringMethod() {
//         NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
//         FindCommand findCommand = new FindCommand(predicate);
//         String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
//         assertEquals(expected, findCommand.toString());
//     }

//     /**
//      * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
//      */
//     private NameContainsKeywordsPredicate preparePredicate(String userInput) {
//         return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
//     }
// }
