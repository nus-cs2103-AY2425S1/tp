package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PRODUCTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalProducts.APPLE;
import static seedu.address.testutil.TypicalProducts.APPLE_JUICE;
import static seedu.address.testutil.TypicalProducts.PINEAPPLE;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.ProductNameContainsKeywordsPredicate;
import seedu.address.testutil.TypicalProducts;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewProductCommand}.
 */
public class ViewProductCommandTest {
    private Model model = new ModelManager(TypicalProducts.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalProducts.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ProductNameContainsKeywordsPredicate firstPredicate =
                new ProductNameContainsKeywordsPredicate(Collections.singletonList("first"));
        ProductNameContainsKeywordsPredicate secondPredicate =
                new ProductNameContainsKeywordsPredicate(Collections.singletonList("second"));

        ViewProductCommand findFirstCommand = new ViewProductCommand(firstPredicate, null);
        ViewProductCommand findSecondCommand = new ViewProductCommand(secondPredicate, null);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ViewProductCommand findFirstCommandCopy = new ViewProductCommand(firstPredicate, null);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different supplier -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noProductFound() {
        String expectedMessage = String.format(MESSAGE_PRODUCTS_LISTED_OVERVIEW, 0);
        ProductNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        ViewProductCommand command = new ViewProductCommand(predicate, null);
        expectedModel.updateFilteredProductList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getModifiedProductList());
    }

    @Test
    public void execute_multipleKeywords_multipleProductsFound() {
        String expectedMessage = String.format(MESSAGE_PRODUCTS_LISTED_OVERVIEW, 3);
        ProductNameContainsKeywordsPredicate predicate = preparePredicate("APPLE JUICE");
        ViewProductCommand command = new ViewProductCommand(predicate, null);
        expectedModel.updateFilteredProductList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(APPLE, APPLE_JUICE, PINEAPPLE), model.getModifiedProductList());
    }

    @Test
    public void toStringMethod() {
        ProductNameContainsKeywordsPredicate predicate =
                new ProductNameContainsKeywordsPredicate(Arrays.asList("keyword"));
        ViewProductCommand viewProductCommand = new ViewProductCommand(predicate, null);
        String expected = ViewProductCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, viewProductCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code ProductNameContainsKeywordsPredicate}.
     */
    private ProductNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ProductNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
