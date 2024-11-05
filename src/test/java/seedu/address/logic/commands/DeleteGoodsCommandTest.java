package seedu.address.logic.commands;

// import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGoods.getTypicalGoodsReceipts;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.goodsreceipt.GoodsReceipt;

/**
 * Contains integration tests for the {@code DeleteGoodsCommand}.
 */
public class DeleteGoodsCommandTest {

    private Model getDefaultModel() {
        return new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalGoodsReceipts());
    }

    @Test
    public void execute_existingGoodsReceipt_success() {
        // TODO: Expand to ensure no unncessary deletions for goods of the same name
        Model expectedModel = getDefaultModel();
        GoodsReceipt toBeDeleted = expectedModel.getGoods().getReceiptList().get(0);
        GoodsName refGoodsName = toBeDeleted.getGoods().getGoodsName();
        DeleteGoodsCommand cmd = new DeleteGoodsCommand(refGoodsName.toString());
        expectedModel.deleteGoods(refGoodsName);

        assertCommandSuccess(cmd, getDefaultModel(),
                String.format(DeleteGoodsCommand.MESSAGE_SUCCESS, refGoodsName), expectedModel);
    }

    // @Test
    // public void execute_nonExistingGoodsReceipt_throwsCommandException() {
    //     // TODO: Expand to include duplicate case
    //     GoodsName goodsName = new GoodsName("Alex");
    //     DeleteGoodsCommand cmd = new DeleteGoodsCommand(goodsName.toString());
    //     assertCommandFailure(cmd, getDefaultModel(),
    //         String.format(DeleteGoodsCommand.MESSAGE_FAILURE, goodsName.toString()));
    // }
}
