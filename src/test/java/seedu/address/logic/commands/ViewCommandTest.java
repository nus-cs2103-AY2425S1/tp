package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PRODUCTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalProducts.APPLE;
import static seedu.address.testutil.TypicalProducts.APPLE_JUICE;
import static seedu.address.testutil.TypicalProducts.PINEAPPLE;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.ProductNameContainsKeywordsPredicate;
import seedu.address.testutil.TypicalProducts;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewCommand}.
 */
public class ViewCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ProductNameContainsKeywordsPredicate firstPredicate =
                new ProductNameContainsKeywordsPredicate(Collections.singletonList("first"));
        ProductNameContainsKeywordsPredicate secondPredicate =
                new ProductNameContainsKeywordsPredicate(Collections.singletonList("second"));

        ViewCommand findFirstCommand = new ViewCommand(firstPredicate);
        ViewCommand findSecondCommand = new ViewCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ViewCommand findFirstCommandCopy = new ViewCommand(firstPredicate);
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
        Model model1 = new ModelManager(TypicalProducts.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel1 = new ModelManager(TypicalProducts.getTypicalAddressBook(), new UserPrefs());
        String expectedMessage = String.format(MESSAGE_PRODUCTS_LISTED_OVERVIEW, 0);
        ProductNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel1.updateFilteredProductList(predicate);
        assertCommandSuccess(command, model1, expectedMessage, expectedModel1);
        assertEquals(Collections.emptyList(), model1.getFilteredProductList());
    }

    @Test
    public void execute_multipleKeywords_multipleProductsFound() {
        Model model2 = new ModelManager(TypicalProducts.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel2 = new ModelManager(TypicalProducts.getTypicalAddressBook(), new UserPrefs());
        String expectedMessage = String.format(MESSAGE_PRODUCTS_LISTED_OVERVIEW, 3);
        ProductNameContainsKeywordsPredicate predicate = preparePredicate("APPLE JUICE");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredProductList(predicate);
        assertCommandSuccess(command, model2, expectedMessage, expectedModel2);
        assertEquals(Arrays.asList(APPLE, APPLE_JUICE, PINEAPPLE), model2.getFilteredProductList());
    }

    @Test
    public void toStringMethod() {
        ProductNameContainsKeywordsPredicate predicate = new ProductNameContainsKeywordsPredicate(Arrays.asList("keyword"));
        ViewCommand viewCommand = new ViewCommand(predicate);
        String expected = ViewCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, viewCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code ProductNameContainsKeywordsPredicate}.
     */
    private ProductNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ProductNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
