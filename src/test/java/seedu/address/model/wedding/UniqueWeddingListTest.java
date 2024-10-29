package seedu.address.model.wedding;

import org.junit.jupiter.api.Test;
import seedu.address.model.wedding.exceptions.DuplicateWeddingException;
import seedu.address.model.wedding.exceptions.WeddingNotFoundException;
import seedu.address.testutil.WeddingBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMYWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOBWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOBWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_AMYWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_BOBWEDDING;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalWeddings.ALICE_WEDDING;
import static seedu.address.testutil.TypicalWeddings.AMY_WEDDING;

public class UniqueWeddingListTest {
    private final UniqueWeddingList uniqueWeddingList = new UniqueWeddingList();

    @Test
    public void contains_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.contains(null));
    }

    @Test
    public void contains_weddingNotInList_returnsFalse() {
        assertFalse(uniqueWeddingList.contains(ALICE_WEDDING));
    }

    @Test
    public void contains_weddingInList_returnsTrue() {
        uniqueWeddingList.add(ALICE_WEDDING);
        assertTrue(uniqueWeddingList.contains(ALICE_WEDDING));
    }

    @Test
    public void contains_weddingWithSameIdentityFieldsInList_returnsFalse() {
        uniqueWeddingList.add(ALICE_WEDDING);
        Wedding editedAliceWedding = new WeddingBuilder(ALICE_WEDDING).withVenue(VALID_VENUE_AMYWEDDING)
                .withDate(VALID_DATE_AMYWEDDING)
                .build();
        assertFalse(uniqueWeddingList.contains(editedAliceWedding));
    }

    @Test
    public void contains_weddingWithDifferentFieldsInList_returnsFalse() {
        uniqueWeddingList.add(AMY_WEDDING);

        // Different Name
        Wedding editedAmyWedding = new WeddingBuilder(AMY_WEDDING).withName(VALID_NAME_BOBWEDDING).build();
        assertFalse(uniqueWeddingList.contains(editedAmyWedding));

        // Different Venue
        editedAmyWedding = new WeddingBuilder(AMY_WEDDING).withVenue(VALID_VENUE_BOBWEDDING).build();
        assertFalse(uniqueWeddingList.contains(editedAmyWedding));

        // Different Date
        editedAmyWedding = new WeddingBuilder(AMY_WEDDING).withDate(VALID_DATE_BOBWEDDING).build();
        assertFalse(uniqueWeddingList.contains(editedAmyWedding));

        // Different Client
        editedAmyWedding = new WeddingBuilder(AMY_WEDDING).withClient(ALICE).build();
        assertFalse(uniqueWeddingList.contains(editedAmyWedding));

        // Different in all fields
        Wedding bobWedding = new WeddingBuilder().withName(VALID_NAME_BOBWEDDING).withDate(VALID_DATE_BOBWEDDING)
                .withVenue(VALID_VENUE_BOBWEDDING).withClient(BOB).build();
        assertFalse(uniqueWeddingList.contains(bobWedding));
    }

    @Test
    public void add_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.add(null));
    }

    @Test
    public void add_duplicateWedding_throwsDuplicateWeddingException() {
        uniqueWeddingList.add(ALICE_WEDDING);
        assertThrows(DuplicateWeddingException.class, () -> uniqueWeddingList.add(ALICE_WEDDING));
    }

    @Test
    public void setWedding_nullTargetWeeding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWedding(null, ALICE_WEDDING));
    }

    @Test
    public void setWedding_nullEditedWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWedding(ALICE_WEDDING, null));
    }

    @Test
    public void setWedding_targetWeddingNotInList_throwsWeddingNotFoundException() {
        assertThrows(WeddingNotFoundException.class, () -> uniqueWeddingList.setWedding(ALICE_WEDDING, ALICE_WEDDING));
    }

    @Test
    public void setWedding_editedWeddingIsSameWedding_success() {
        uniqueWeddingList.add(ALICE_WEDDING);
        uniqueWeddingList.setWedding(ALICE_WEDDING, ALICE_WEDDING);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(ALICE_WEDDING);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWedding_editedWeddingHasDifferentFields_success() {
        uniqueWeddingList.add(ALICE_WEDDING);
        uniqueWeddingList.setWedding(ALICE_WEDDING, AMY_WEDDING);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(AMY_WEDDING);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWedding_editedWeddingIsNonUnique_throwsDuplicateWeddingException() {
        uniqueWeddingList.add(ALICE_WEDDING);
        uniqueWeddingList.add(AMY_WEDDING);
        assertThrows(DuplicateWeddingException.class, () -> uniqueWeddingList.setWedding(ALICE_WEDDING, AMY_WEDDING));
    }

    @Test
    public void remove_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.remove(null));
    }

    @Test
    public void remove_weddingDoesNotExist_throwsWeddingNotFoundException() {
        assertThrows(WeddingNotFoundException.class, () -> uniqueWeddingList.remove(ALICE_WEDDING));
    }

    @Test
    public void remove_existingWedding_removesWedding() {
        uniqueWeddingList.add(ALICE_WEDDING);
        uniqueWeddingList.remove(ALICE_WEDDING);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_nullUniqueWeddingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWeddings((UniqueWeddingList) null));
    }

    @Test
    public void setWeddings_uniqueWeddingList_replacesOwnListWithProvidedUniqueWeddingList() {
        uniqueWeddingList.add(ALICE_WEDDING);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(AMY_WEDDING);
        uniqueWeddingList.setWeddings(expectedUniqueWeddingList);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWeddings((List<Wedding>) null));
    }

    @Test
    public void setWeddings_list_replacesOwnListWithProvidedList() {
        uniqueWeddingList.add(ALICE_WEDDING);
        List<Wedding> weddingList = Collections.singletonList(AMY_WEDDING);
        uniqueWeddingList.setWeddings(weddingList);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(AMY_WEDDING);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_listWithDuplicateWeddings_throwsDuplicateWeddingException() {
        List<Wedding> listWithDuplicateWeddings = Arrays.asList(ALICE_WEDDING, ALICE_WEDDING);
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
