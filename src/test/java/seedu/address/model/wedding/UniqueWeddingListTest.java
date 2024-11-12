package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_ONE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_TWO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.wedding.exceptions.DuplicateWeddingException;
import seedu.address.model.wedding.exceptions.WeddingNotFoundException;
import seedu.address.testutil.WeddingBuilder;

public class UniqueWeddingListTest {

    private final UniqueWeddingList uniqueWeddingList = new UniqueWeddingList();

    @Test
    public void contains_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.contains(null));
    }

    @Test
    public void contains_weddingNotInList_returnsFalse() {
        assertFalse(uniqueWeddingList.contains(WEDDING_ONE));
    }

    @Test
    public void contains_weddingInList_returnsTrue() {
        uniqueWeddingList.add(WEDDING_ONE);
        assertTrue(uniqueWeddingList.contains(WEDDING_ONE));
    }

    @Test
    public void contains_weddingWithSameIdentityFieldsInList_returnsTrue() {
        uniqueWeddingList.add(WEDDING_ONE);
        Wedding editedWedding = new WeddingBuilder(WEDDING_ONE).build();
        assertTrue(uniqueWeddingList.contains(editedWedding));
    }

    @Test
    public void add_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.add(null));
    }

    @Test
    public void add_duplicateWedding_throwsDuplicateWeddingException() {
        uniqueWeddingList.add(WEDDING_ONE);
        assertThrows(DuplicateWeddingException.class, () -> uniqueWeddingList.add(WEDDING_ONE));
    }

    @Test
    public void setWedding_nullTargetWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWedding(null, WEDDING_ONE));
    }

    @Test
    public void setWedding_nullEditedWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWedding(WEDDING_ONE, null));
    }

    @Test
    public void setWedding_targetWeddingNotInList_throwsWeddingNotFoundException() {
        assertThrows(WeddingNotFoundException.class, () -> uniqueWeddingList.setWedding(WEDDING_ONE, WEDDING_ONE));
    }

    @Test
    public void setWedding_editedWeddingIsSameWedding_success() {
        uniqueWeddingList.add(WEDDING_ONE);
        uniqueWeddingList.setWedding(WEDDING_ONE, WEDDING_ONE);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(WEDDING_ONE);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWedding_editedWeddingHasSameIdentity_success() {
        uniqueWeddingList.add(WEDDING_ONE);
        Wedding editedWedding = new WeddingBuilder(WEDDING_ONE).withVenue(VALID_VENUE_ONE).build();
        uniqueWeddingList.setWedding(WEDDING_ONE, editedWedding);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(editedWedding);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWedding_editedWeddingHasDifferentIdentity_success() {
        uniqueWeddingList.add(WEDDING_ONE);
        uniqueWeddingList.setWedding(WEDDING_ONE, WEDDING_TWO);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(WEDDING_TWO);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWedding_editedWeddingHasNonUniqueIdentity_throwsDuplicateWeddingException() {
        uniqueWeddingList.add(WEDDING_ONE);
        uniqueWeddingList.add(WEDDING_TWO);
        assertThrows(DuplicateWeddingException.class, () -> uniqueWeddingList.setWedding(WEDDING_ONE, WEDDING_TWO));
    }

    @Test
    public void remove_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.remove(null));
    }

    @Test
    public void remove_weddingDoesNotExist_throwsWeddingNotFoundException() {
        assertThrows(WeddingNotFoundException.class, () -> uniqueWeddingList.remove(WEDDING_ONE));
    }

    @Test
    public void remove_existingWedding_removesWedding() {
        uniqueWeddingList.add(WEDDING_ONE);
        uniqueWeddingList.remove(WEDDING_ONE);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_nullUniqueWeddingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWeddings((UniqueWeddingList) null));
    }

    @Test
    public void setWeddings_uniqueWeddingList_replacesOwnListWithProvidedUniqueWeddingList() {
        uniqueWeddingList.add(WEDDING_ONE);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(WEDDING_TWO);
        uniqueWeddingList.setWeddings(expectedUniqueWeddingList);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWeddings((List<Wedding>) null));
    }

    @Test
    public void setWeddings_list_replacesOwnListWithProvidedList() {
        uniqueWeddingList.add(WEDDING_ONE);
        List<Wedding> weddingList = Collections.singletonList(WEDDING_TWO);
        uniqueWeddingList.setWeddings(weddingList);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(WEDDING_TWO);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_listWithDuplicateWeddings_throwsDuplicateWeddingException() {
        List<Wedding> listWithDuplicateWeddings = Arrays.asList(WEDDING_ONE, WEDDING_ONE);
        assertThrows(DuplicateWeddingException.class, () -> uniqueWeddingList.setWeddings(listWithDuplicateWeddings));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueWeddingList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueWeddingList.asUnmodifiableObservableList().toString(), uniqueWeddingList.toString());
    }
}
