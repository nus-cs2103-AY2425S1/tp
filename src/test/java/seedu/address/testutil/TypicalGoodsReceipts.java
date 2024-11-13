package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import seedu.address.model.goodsreceipt.GoodsReceipt;

/**
 * A utility class containing a list of {@code GoodsReceipts} objects to be used in tests.
 */
public class TypicalGoodsReceipts {
    public static final GoodsReceipt ALICE_RECEIPT = new GoodsReceiptBuilder()
            .withSupplierName(ALICE.getName())
            .build();

    public static final GoodsReceipt BOB_RECEIPT = new GoodsReceiptBuilder()
            .withSupplierName(BOB.getName())
            .build();
}
