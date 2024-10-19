package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalGoods.getTypicalGoodsReceipts;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

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
}
