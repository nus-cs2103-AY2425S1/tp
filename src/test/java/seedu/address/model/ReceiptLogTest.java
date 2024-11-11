package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoodsReceipts.ALICE_RECEIPT;
import static seedu.address.testutil.TypicalGoodsReceipts.BOB_RECEIPT;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.goodsreceipt.GoodsReceipt;
import seedu.address.model.goodsreceipt.exceptions.DuplicateReceiptException;
import seedu.address.model.goodsreceipt.exceptions.ReceiptNotFoundException;
import seedu.address.model.person.Name;


public class ReceiptLogTest {

    private ReceiptLog receiptLog = new ReceiptLog();

    @Test
    public void modify_getReceiptList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> receiptLog.getReceiptList().remove(0));
    }

    @Test
    public void addReceipt_goodsReceipt_success() {
        receiptLog.addReceipt(ALICE_RECEIPT);
        assertTrue(receiptLog.hasReceipt(ALICE_RECEIPT));
    }

    @Test
    public void addReceipt_duplicateReceipt_throwsDuplicateReceiptException() {
        receiptLog.addReceipt(ALICE_RECEIPT);
        assertThrows(DuplicateReceiptException.class, () -> receiptLog.addReceipt(ALICE_RECEIPT));
    }

    @Test
    public void deleteReceipt_goodsReceipt_success() {
        receiptLog.addReceipt(ALICE_RECEIPT);
        receiptLog.deleteReceipt(ALICE_RECEIPT);
        assertFalse(receiptLog.hasReceipt(ALICE_RECEIPT));
    }

    @Test
    public void deleteReceipt_notInList_throwsReceiptNotFoundException() {
        assertThrows(ReceiptNotFoundException.class, () -> receiptLog.deleteReceipt(ALICE_RECEIPT));
    }

    @Test
    public void setReceipt_goodsReceipts_success() {
        List<GoodsReceipt> goodsReceiptLists = new ArrayList<>();
        goodsReceiptLists.add(ALICE_RECEIPT);
        goodsReceiptLists.add(BOB_RECEIPT);

        receiptLog.setReceipts(goodsReceiptLists);
        assertEquals(receiptLog.getReceiptList(), goodsReceiptLists);
    }

    @Test
    public void removeIf_predicate_success() {
        List<GoodsReceipt> goodsReceiptLists = new ArrayList<>();
        goodsReceiptLists.add(ALICE_RECEIPT);
        receiptLog.addReceipt(ALICE_RECEIPT);
        receiptLog.addReceipt(BOB_RECEIPT);
        receiptLog.removeIf(x -> x.getSupplierName().equals(new Name("Bob Choo")));

        assertEquals(receiptLog.getReceiptList(), goodsReceiptLists);
    }

    @Test
    public void equals_sameObject_success() {
        assertEquals(receiptLog, receiptLog);
    }

    @Test
    public void equals_notSameObject_failure() {
        assertNotEquals(receiptLog, new AddressBook());
    }

}

