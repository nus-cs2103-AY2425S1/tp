package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteGoodsCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goodsreceipt.GoodsReceipt;
import seedu.address.testutil.GoodsBuilder;
import seedu.address.testutil.GoodsReceiptBuilder;

public class DeleteGoodsCommandTest {

    @Test
    public void execute_nonExistingSupplierNameAndExistingGoodsName_failure() {
        Model model = new ModelManager();
        model.addPerson(ALICE);
        Goods apple = new GoodsBuilder()
                .withName("Apple")
                .withGoodsCategory(GoodsCategories.CONSUMABLES)
                .build();
        GoodsReceipt appleReceipt = new GoodsReceiptBuilder()
                .withSupplierName(ALICE.getName())
                .withGoods(apple)
                .build();
        model.addGoods(appleReceipt);
        DeleteGoodsCommand cmd = new DeleteGoodsCommand(BOB.getName(), apple.goodsName());
        assertCommandFailure(cmd, model, Messages.MESSAGE_GOODS_RECEIPT_NOT_FOUND);
    }

    @Test
    public void execute_existingSupplierNameAndNonExistingGoodsName_failure() {
        Model model = new ModelManager();
        model.addPerson(ALICE);
        model.addPerson(BOB);
        Goods apple = new GoodsBuilder()
                .withName("Apple")
                .withGoodsCategory(GoodsCategories.CONSUMABLES)
                .build();
        Goods banana = new GoodsBuilder()
                .withName("Banana")
                .withGoodsCategory(GoodsCategories.CONSUMABLES)
                .build();
        GoodsReceipt appleReceipt = new GoodsReceiptBuilder()
                .withSupplierName(ALICE.getName())
                .withGoods(apple)
                .build();
        GoodsReceipt bananaReceipt = new GoodsReceiptBuilder()
                .withSupplierName(BOB.getName())
                .withGoods(banana)
                .build();
        model.addGoods(appleReceipt);
        model.addGoods(bananaReceipt);
        DeleteGoodsCommand cmd = new DeleteGoodsCommand(ALICE.getName(), banana.goodsName());
        assertCommandFailure(cmd, model, Messages.MESSAGE_GOODS_RECEIPT_NOT_FOUND);
    }

    @Test
    public void execute_existingSupplierNameAndExistingGoodsName_success() {
        ModelManager model = new ModelManager();
        model.addPerson(ALICE);
        Goods apple = new GoodsBuilder()
                .withName("Apple")
                .withGoodsCategory(GoodsCategories.CONSUMABLES)
                .build();
        GoodsReceipt appleReceipt = new GoodsReceiptBuilder()
                .withSupplierName(ALICE.getName())
                .withGoods(apple)
                .build();
        model.addGoods(appleReceipt);
        DeleteGoodsCommand cmd = new DeleteGoodsCommand(ALICE.getName(), apple.goodsName());
        String expectedMessage = String.format(MESSAGE_SUCCESS, apple.goodsName());
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(ALICE);
        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_existingSupplierNameAndMultipleExistingGoodsName_success() {
        ModelManager model = new ModelManager();
        model.addPerson(ALICE);
        Goods apple = new GoodsBuilder()
                .withName("Apple")
                .withGoodsCategory(GoodsCategories.CONSUMABLES)
                .build();
        GoodsReceipt appleReceipt = new GoodsReceiptBuilder()
                .withSupplierName(ALICE.getName())
                .withQuantity(6)
                .withGoods(apple)
                .build();
        GoodsReceipt appleReceipt2 = new GoodsReceiptBuilder()
                .withSupplierName(ALICE.getName())
                .withQuantity(5)
                .withGoods(apple)
                .build();
        model.addGoods(appleReceipt);
        model.addGoods(appleReceipt2);

        DeleteGoodsCommand cmd = new DeleteGoodsCommand(ALICE.getName(), apple.goodsName());
        String expectedMessage = String.format(MESSAGE_SUCCESS, apple.goodsName());
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(ALICE);
        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }
}
