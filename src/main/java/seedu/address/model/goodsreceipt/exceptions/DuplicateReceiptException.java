package seedu.address.model.goodsreceipt.exceptions;

/**
 * Signals that the operation will result in duplicate goodsReceipts
 */
public class DuplicateReceiptException extends RuntimeException {
    public DuplicateReceiptException() {
        super("Operation would result in duplicate goods receipts");
    }
}
