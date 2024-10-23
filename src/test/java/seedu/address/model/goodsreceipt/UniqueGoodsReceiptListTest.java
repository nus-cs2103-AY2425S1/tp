package seedu.address.model.goodsreceipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoodsReceipts.aliceReceipt;
import static seedu.address.testutil.TypicalGoodsReceipts.bobReceipt;
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
        assertFalse(uniqueGoodsReceiptList.contains(aliceReceipt));
    }

    @Test
    public void contains_receiptInList_returnsTrue() {
        uniqueGoodsReceiptList.add(aliceReceipt);
        assertTrue(uniqueGoodsReceiptList.contains(aliceReceipt));
    }

    @Test
    public void contains_receiptWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGoodsReceiptList.add(aliceReceipt);
        GoodsReceipt editedAlice = new GoodsReceiptBuilder().withSupplierName(ALICE.getName()).build();
        boolean wat = uniqueGoodsReceiptList.contains(editedAlice);
        assertTrue(uniqueGoodsReceiptList.contains(editedAlice));
    }

    @Test
    public void add_nullreceipt_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodsReceiptList.add(null));
    }

    @Test
    public void add_duplicateReceipt_throwsDuplicatePersonException() {
        uniqueGoodsReceiptList.add(aliceReceipt);
        assertThrows(DuplicateReceiptException.class, () -> uniqueGoodsReceiptList.add(aliceReceipt));
    }


    @Test
    public void remove_nullReceipt_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodsReceiptList.remove(null));
    }

    @Test
    public void remove_receiptDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(ReceiptNotFoundException.class, () -> uniqueGoodsReceiptList.remove(aliceReceipt));
    }

    @Test
    public void remove_existingReceipt_removesPerson() {
        uniqueGoodsReceiptList.add(aliceReceipt);
        uniqueGoodsReceiptList.remove(aliceReceipt);
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
        uniqueGoodsReceiptList.add(aliceReceipt);
        UniqueGoodsReceiptList expectedUniqueReceiptList = new UniqueGoodsReceiptList();
        uniqueGoodsReceiptList.add(bobReceipt);
        uniqueGoodsReceiptList.setReceipts(expectedUniqueReceiptList);
        assertEquals(expectedUniqueReceiptList, uniqueGoodsReceiptList);
    }

    @Test
    public void setReceipts_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodsReceiptList.setReceipts((List<GoodsReceipt>) null));
    }

    @Test
    public void setReceipt_list_replacesOwnListWithProvidedList() {
        uniqueGoodsReceiptList.add(aliceReceipt);
        List<GoodsReceipt> goodsReceipts = Collections.singletonList(bobReceipt);
        uniqueGoodsReceiptList.setReceipts(goodsReceipts);
        UniqueGoodsReceiptList expectedUniqueReceiptList = new UniqueGoodsReceiptList();
        expectedUniqueReceiptList.add(bobReceipt);
        assertEquals(expectedUniqueReceiptList, uniqueGoodsReceiptList);
    }

    @Test
    public void setReceipt_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<GoodsReceipt> listWithDuplicatePersons = Arrays.asList(aliceReceipt, aliceReceipt);
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
