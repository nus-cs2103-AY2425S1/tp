package seedu.address.model;

import org.junit.jupiter.api.Test;

public class ReceiptLogTest {

    private ReceiptLog receiptLog = new ReceiptLog();
    @Test
    public void equals_sameContent_success() {
        //TODO
        receiptLog.equals(new AddressBook());
        assert true;
    }

    @Test
    public void equals_sameObject_success() {
        //TODO
        assert receiptLog.equals(receiptLog);
    }

    @Test
    public void equals_notSameObject_failure() {
        //TODO
        assert !receiptLog.equals(new AddressBook());
    }

    @Test
    public void hashCode_sameContent_success() {
        //TODO
        assert true;
    }
}
