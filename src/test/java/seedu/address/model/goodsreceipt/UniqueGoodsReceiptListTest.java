package seedu.address.model.goodsreceipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoodsReceipts.ALICE_RECEIPT;
import static seedu.address.testutil.TypicalGoodsReceipts.BOB_RECEIPT;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.goodsreceipt.exceptions.DuplicateReceiptException;
import seedu.address.model.goodsreceipt.exceptions.ReceiptNotFoundException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.GoodsReceiptBuilder;

public class UniqueGoodsReceiptListTest {
    private final UniqueGoodsReceiptList uniqueGoodsReceiptList = new UniqueGoodsReceiptList();

    @Test
    public void contains_nullReceipt_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodsReceiptList.contains(null));
    }

    @Test
    public void contains_receiptNotInList_returnsFalse() {
        assertFalse(uniqueGoodsReceiptList.contains(ALICE_RECEIPT));
    }

    @Test
    public void contains_receiptInList_returnsTrue() {
        uniqueGoodsReceiptList.add(ALICE_RECEIPT);
        assertTrue(uniqueGoodsReceiptList.contains(ALICE_RECEIPT));
    }

    @Test
    public void contains_receiptWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGoodsReceiptList.add(ALICE_RECEIPT);
        GoodsReceipt editedAlice = new GoodsReceiptBuilder().withSupplierName(ALICE.getName()).build();
        boolean isPresent = uniqueGoodsReceiptList.contains(editedAlice);
        assertTrue(isPresent);
    }

    @Test
    public void add_nullreceipt_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodsReceiptList.add(null));
    }

    @Test
    public void add_duplicateReceipt_throwsDuplicatePersonException() {
        uniqueGoodsReceiptList.add(ALICE_RECEIPT);
        assertThrows(DuplicateReceiptException.class, () -> uniqueGoodsReceiptList.add(ALICE_RECEIPT));
    }


    @Test
    public void remove_nullReceipt_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodsReceiptList.remove(null));
    }

    @Test
    public void remove_receiptDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(ReceiptNotFoundException.class, () -> uniqueGoodsReceiptList.remove(ALICE_RECEIPT));
    }

    @Test
    public void remove_existingReceipt_removesPerson() {
        uniqueGoodsReceiptList.add(ALICE_RECEIPT);
        uniqueGoodsReceiptList.remove(ALICE_RECEIPT);
        UniqueGoodsReceiptList expectedUniqueReceiptList = new UniqueGoodsReceiptList();
        assertEquals(expectedUniqueReceiptList, uniqueGoodsReceiptList);
    }

    @Test
    public void setPersons_nullUniqueReceiptList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueGoodsReceiptList.setReceipts((UniqueGoodsReceiptList) null));
    }

    @Test
    public void setPersons_uniqueReceiptList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueGoodsReceiptList.add(ALICE_RECEIPT);
        UniqueGoodsReceiptList expectedUniqueReceiptList = new UniqueGoodsReceiptList();
        uniqueGoodsReceiptList.add(BOB_RECEIPT);
        uniqueGoodsReceiptList.setReceipts(expectedUniqueReceiptList);
        assertEquals(expectedUniqueReceiptList, uniqueGoodsReceiptList);
    }

    @Test
    public void setReceipts_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodsReceiptList.setReceipts((List<GoodsReceipt>) null));
    }

    @Test
    public void setReceipt_populatedList_replacesOwnListWithProvidedList() {
        uniqueGoodsReceiptList.add(ALICE_RECEIPT);
        List<GoodsReceipt> goodsReceipts = Collections.singletonList(BOB_RECEIPT);
        uniqueGoodsReceiptList.setReceipts(goodsReceipts);
        UniqueGoodsReceiptList expectedUniqueReceiptList = new UniqueGoodsReceiptList();
        expectedUniqueReceiptList.add(BOB_RECEIPT);
        assertEquals(expectedUniqueReceiptList, uniqueGoodsReceiptList);
    }

    @Test
    public void setReceipt_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<GoodsReceipt> listWithDuplicatePersons = Arrays.asList(ALICE_RECEIPT, ALICE_RECEIPT);
        assertThrows(DuplicatePersonException.class, () ->
                uniqueGoodsReceiptList.setReceipts(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueGoodsReceiptList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueGoodsReceiptList.asUnmodifiableObservableList().toString(),
                uniqueGoodsReceiptList.toString());
    }
}
