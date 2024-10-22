package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.goodsreceipt.GoodsReceipt;

/**
 * Unmodifiable view of a Receipt list
 */
public interface ReadOnlyReceiptLog {

    ObservableList<GoodsReceipt> getReceiptList();
}
