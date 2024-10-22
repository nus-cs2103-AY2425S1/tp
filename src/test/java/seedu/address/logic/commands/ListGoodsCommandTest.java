package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalGoods.getTypicalGoodsReceipts;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ListGoodsCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goodsreceipt.CategoryPredicate;
import seedu.address.model.goodsreceipt.GoodsReceipt;

/**
 * Contains integration tests for {@code ListGoodsCommand}
 */
public class ListGoodsCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalGoodsReceipts());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalGoodsReceipts());

    @Test
    public void execute_zeroKeywords_allGoodsReturned() {
        // TODO: Implement Test
    }

    @Test
    public void execute_categoryKeyword_matchingGoodsReturned() {
        // TODO: Implement Test
    }

    @Test
    public void execute_supplierNameKeyword_matchingGoodsReturned() {
        // TODO: Implement Test
    }

    @Test
    public void execute_goodsNameKeyword_matchingGoodsReturned() {
        // TODO: Implement Test
    }

    @Test
    public void execute_multipleKeywords_matchingGoodsReturned() {
        // TODO: Implement Test
    }

    @Test
    public void execute_multipleKeywords_noGoodsReturned() {
        // TODO: Implement Test
    }

    @Test
    public void equals() {
        ListGoodsCommand cmd = new ListGoodsCommand(ListGoodsCommandParser.DUMMY_PREDICATE);
        Predicate<GoodsReceipt> testPredicate = new CategoryPredicate(GoodsCategories.CONSUMABLES);
        ListGoodsCommand unequalCmd = new ListGoodsCommand(testPredicate);

        // Check if the object is equal to itself
        assertTrue(cmd.equals(cmd));

        // Check if the object is equal to command with different predicate
        assertFalse(cmd.equals(unequalCmd));

        // Check if the object is equal to an unrelated object
        assertFalse(cmd.equals(1.0));
    }
}
