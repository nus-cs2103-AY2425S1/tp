package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWeddings.AMY_WEDDING;
import static seedu.address.testutil.TypicalWeddings.BOB_WEDDING;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.wedding.exceptions.DuplicateWeddingException;
import seedu.address.model.wedding.exceptions.WeddingNotFoundException;

public class UniqueWeddingListTest {
    private final UniqueWeddingList uniqueWeddingList = new UniqueWeddingList();

    @Test
    public void contains_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.contains(null));
    }

    @Test
    public void contains_weddingNotInList_returnsFalse() {
        assertFalse(uniqueWeddingList.contains(AMY_WEDDING));
    }

    @Test
    public void contains_weddingInList_returnsTrue() {
        uniqueWeddingList.add(AMY_WEDDING);
        assertTrue(uniqueWeddingList.contains(AMY_WEDDING));
    }

    @Test
    public void contains_weddingWithSameIdentityFieldsInList_returnsTrue() {
        uniqueWeddingList.add(AMY_WEDDING);
        Wedding sameWedding = new Wedding(new WeddingName("Amy's Wedding"));
        assertTrue(uniqueWeddingList.contains(sameWedding));
    }

    @Test
    public void add_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.add(null));
    }

    @Test
    public void add_duplicateWedding_throwsDuplicateWeddingException() {
        uniqueWeddingList.add(AMY_WEDDING);
        assertThrows(DuplicateWeddingException.class, () -> uniqueWeddingList.add(AMY_WEDDING));
    }

    @Test
    public void setWedding_nullTargetWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWedding(null, AMY_WEDDING));
    }

    @Test
    public void setWedding_nullEditedWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWedding(AMY_WEDDING, null));
    }

    @Test
    public void setWedding_targetWeddingNotInList_throwsWeddingNotFoundException() {
        assertThrows(WeddingNotFoundException.class, () -> uniqueWeddingList.setWedding(AMY_WEDDING, BOB_WEDDING));
    }

    @Test
    public void setWedding_editedWeddingIsSameWedding_success() {
        uniqueWeddingList.add(AMY_WEDDING);
        uniqueWeddingList.setWedding(AMY_WEDDING, AMY_WEDDING);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(AMY_WEDDING);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWedding_editedWeddingHasDifferentIdentity_success() {
        uniqueWeddingList.add(AMY_WEDDING);
        uniqueWeddingList.setWedding(AMY_WEDDING, BOB_WEDDING);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(BOB_WEDDING);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWedding_editedWeddingHasNonUniqueIdentity_throwsDuplicateWeddingException() {
        uniqueWeddingList.add(AMY_WEDDING);
        uniqueWeddingList.add(BOB_WEDDING);
        assertThrows(DuplicateWeddingException.class, () -> uniqueWeddingList.setWedding(AMY_WEDDING, BOB_WEDDING));
    }

    @Test
    public void remove_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.remove(null));
    }

    @Test
    public void remove_weddingDoesNotExist_throwsWeddingNotFoundException() {
        assertThrows(WeddingNotFoundException.class, () -> uniqueWeddingList.remove(AMY_WEDDING));
    }

    @Test
    public void remove_existingWedding_removesWedding() {
        uniqueWeddingList.add(AMY_WEDDING);
        uniqueWeddingList.remove(AMY_WEDDING);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_nullUniqueWeddingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWeddings((List<Wedding>) null));
    }

    @Test
    public void setWeddings_uniqueWeddingList_replacesOwnListWithProvidedUniqueWeddingList() {
        uniqueWeddingList.add(AMY_WEDDING);
        List<Wedding> weddingList = Collections.singletonList(BOB_WEDDING);
        uniqueWeddingList.setWeddings(weddingList);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(BOB_WEDDING);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_listWithDuplicateWeddings_throwsDuplicateWeddingException() {
        List<Wedding> listWithDuplicateWeddings = Arrays.asList(AMY_WEDDING, AMY_WEDDING);
        assertThrows(DuplicateWeddingException.class, () -> uniqueWeddingList.setWeddings(listWithDuplicateWeddings));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueWeddingList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        UniqueWeddingList anotherList = new UniqueWeddingList();
        anotherList.add(AMY_WEDDING);
        uniqueWeddingList.add(AMY_WEDDING);
        assertTrue(uniqueWeddingList.equals(anotherList));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        UniqueWeddingList anotherList = new UniqueWeddingList();
        anotherList.add(BOB_WEDDING);
        uniqueWeddingList.add(AMY_WEDDING);
        assertFalse(uniqueWeddingList.equals(anotherList));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(uniqueWeddingList.equals(uniqueWeddingList));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(uniqueWeddingList.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(uniqueWeddingList.equals(5));
    }

    @Test
    public void hashCode_sameValues_returnsSameHashCode() {
        UniqueWeddingList anotherList = new UniqueWeddingList();
        anotherList.add(AMY_WEDDING);
        uniqueWeddingList.add(AMY_WEDDING);
        assertEquals(uniqueWeddingList.hashCode(), anotherList.hashCode());
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHashCode() {
        UniqueWeddingList anotherList = new UniqueWeddingList();
        anotherList.add(BOB_WEDDING);
        uniqueWeddingList.add(AMY_WEDDING);
        assertFalse(uniqueWeddingList.hashCode() == anotherList.hashCode());
    }

    @Test
    public void toStringTest() {
        assertEquals(uniqueWeddingList.asUnmodifiableObservableList().toString(), uniqueWeddingList.toString());
    }
}
