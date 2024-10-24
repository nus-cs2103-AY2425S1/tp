package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGoods.getTypicalGoodsReceipts;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ListGoodsCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goodsreceipt.CategoryPredicate;
import seedu.address.model.goodsreceipt.GoodsNamePredicate;
import seedu.address.model.goodsreceipt.GoodsReceipt;
import seedu.address.model.goodsreceipt.SupplierNamePredicate;
import seedu.address.model.person.Name;

/**
 * Contains integration tests for {@code ListGoodsCommand}
 * For all success cases below, default quantity of 1 and default price of 10 is assumed.
 */
public class ListGoodsCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalGoodsReceipts());
    private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getGoods());

    @Test
    public void execute_zeroKeywords_allGoodsReturned() {
        Predicate<GoodsReceipt> predicate = unused -> true;
        ListGoodsCommand command = new ListGoodsCommand(predicate);
        expectedModel.updateFilteredReceiptsList(predicate);
        String statisticsString = String.format(ListGoodsCommand.MESSAGE_STATISTICS, 10, 100.00);
        CommandResult expectedCommandResult = new CommandResult(ListGoodsCommand.MESSAGE_SUCCESS + statisticsString);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_categoryKeyword_matchingGoodsReturned() {
        Predicate<GoodsReceipt> predicate = new CategoryPredicate(GoodsCategories.CONSUMABLES);
        ListGoodsCommand command = new ListGoodsCommand(predicate);
        expectedModel.updateFilteredReceiptsList(predicate);
        String statisticsString = String.format(ListGoodsCommand.MESSAGE_STATISTICS, 10, 100.00);
        CommandResult expectedCommandResult = new CommandResult(ListGoodsCommand.MESSAGE_SUCCESS + statisticsString);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_supplierNameKeyword_matchingGoodsReturned() {
        Predicate<GoodsReceipt> predicate = new SupplierNamePredicate(new Name("Supplier"));
        ListGoodsCommand command = new ListGoodsCommand(predicate);
        expectedModel.updateFilteredReceiptsList(predicate);
        String statisticsString = String.format(ListGoodsCommand.MESSAGE_STATISTICS, 10, 100.00);
        CommandResult expectedCommandResult = new CommandResult(ListGoodsCommand.MESSAGE_SUCCESS + statisticsString);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_goodsNameKeyword_matchingGoodsReturned() {
        Predicate<GoodsReceipt> predicate = new GoodsNamePredicate("Apple");
        ListGoodsCommand command = new ListGoodsCommand(predicate);
        expectedModel.updateFilteredReceiptsList(predicate);
        String statisticsString = String.format(ListGoodsCommand.MESSAGE_STATISTICS, 1, 10.00);
        CommandResult expectedCommandResult = new CommandResult(ListGoodsCommand.MESSAGE_SUCCESS + statisticsString);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_multipleKeywords_matchingGoodsReturned() {
        Predicate<GoodsReceipt> predicate1 = new GoodsNamePredicate("Apple");
        Predicate<GoodsReceipt> predicate2 = new SupplierNamePredicate(new Name("Supplier"));
        Predicate<GoodsReceipt> predicate = predicate1.and(predicate2);
        ListGoodsCommand command = new ListGoodsCommand(predicate);
        expectedModel.updateFilteredReceiptsList(predicate);
        String statisticsString = String.format(ListGoodsCommand.MESSAGE_STATISTICS, 1, 10.00);
        CommandResult expectedCommandResult = new CommandResult(ListGoodsCommand.MESSAGE_SUCCESS + statisticsString);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_multipleKeywords_noGoodsReturned() {
        Predicate<GoodsReceipt> predicate1 = new GoodsNamePredicate("GoodsName");
        Predicate<GoodsReceipt> predicate2 = new SupplierNamePredicate(new Name("SupplierName"));
        Predicate<GoodsReceipt> predicate = predicate1.and(predicate2);
        ListGoodsCommand command = new ListGoodsCommand(predicate);
        expectedModel.updateFilteredReceiptsList(predicate);
        CommandResult expectedCommandResult = new CommandResult(ListGoodsCommand.MESSAGE_EMPTY);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredReceiptsList());
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
