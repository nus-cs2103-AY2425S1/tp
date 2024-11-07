package spleetwaise.transaction.model.filterpredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static spleetwaise.transaction.testutil.TransactionBuilder.DEFAULT_CATEGORY;

import org.junit.jupiter.api.Test;

import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class CategoryFilterPredicateTest {
    private static final Category testCategory = DEFAULT_CATEGORY;

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new CategoryFilterPredicate(null));
    }

    @Test
    public void execute_category_success() {
        CategoryFilterPredicate pred = new CategoryFilterPredicate(testCategory);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.DANIELDEBT));
    }

    @Test
    public void execute_categoryDifferentCase_success() {
        Category testCategoryDifferentCase = new Category(testCategory.toString());
        CategoryFilterPredicate pred = new CategoryFilterPredicate(testCategoryDifferentCase);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.DANIELDEBT));
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        CategoryFilterPredicate pred = new CategoryFilterPredicate(testCategory);
        CategoryFilterPredicate pred2 = new CategoryFilterPredicate(testCategory);

        assertEquals(pred, pred);
        assertEquals(pred, pred2);
    }

    @Test
    public void equals_diffPredicate_returnsFalse() {
        CategoryFilterPredicate pred = new CategoryFilterPredicate(testCategory);
        CategoryFilterPredicate pred2 = new CategoryFilterPredicate(new Category("TRANSPORT"));

        assertNotEquals(pred, pred2);
        assertNotEquals(pred, null);
        assertNotEquals(pred, new Object());
    }

    @Test
    public void toString_success() {
        CategoryFilterPredicate pred = new CategoryFilterPredicate(testCategory);
        assertEquals("spleetwaise.transaction.model.filterpredicate.CategoryFilterPredicate{"
                        + "category=FOOD}",
                pred.toString()
        );
    }
}
