package seedu.address.model.concert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalConcerts.COACHELLA;
import static seedu.address.testutil.TypicalConcerts.GLASTONBURY;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.concert.exceptions.ConcertNotFoundException;
import seedu.address.model.concert.exceptions.DuplicateConcertException;
import seedu.address.testutil.ConcertBuilder;

public class UniqueConcertListTest {
    private final UniqueConcertList uniqueConcertList = new UniqueConcertList();

    @Test
    public void contains_nullConcert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.contains(null));
    }

    @Test
    public void contains_concertNotInList_returnsFalse() {
        assertFalse(uniqueConcertList.contains(COACHELLA));
    }

    @Test
    public void contains_concertInList_returnsTrue() {
        uniqueConcertList.add(COACHELLA);
        assertTrue(uniqueConcertList.contains(COACHELLA));
    }

    @Test
    public void contains_concertWithSameIdentityFieldsInList_returnsTrue() {
        uniqueConcertList.add(COACHELLA);
        Concert editedC1 = new ConcertBuilder(COACHELLA).build();
        assertTrue(uniqueConcertList.contains(editedC1));
    }

    @Test
    public void add_nullConcert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.add(null));
    }

    @Test
    public void add_duplicateConcert_throwsDuplicateConcertException() {
        uniqueConcertList.add(COACHELLA);
        assertThrows(DuplicateConcertException.class, () -> uniqueConcertList.add(COACHELLA));
    }

    @Test
    public void setConcert_nullTargetConcert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.setConcert(null, COACHELLA));
    }

    @Test
    public void setConcert_nullEditedConcert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.setConcert(COACHELLA, null));
    }

    @Test
    public void setConcert_targetConcertNotInList_throwsConcertNotFoundException() {
        assertThrows(ConcertNotFoundException.class, () -> uniqueConcertList.setConcert(COACHELLA, COACHELLA));
    }

    @Test
    public void setConcert_editedConcertIsSameConcert_success() {
        uniqueConcertList.add(COACHELLA);
        uniqueConcertList.setConcert(COACHELLA, COACHELLA);
        UniqueConcertList expectedUniqueConcertList = new UniqueConcertList();
        expectedUniqueConcertList.add(COACHELLA);
        assertEquals(expectedUniqueConcertList, uniqueConcertList);
    }

    @Test
    public void setConcert_editedConcertHasSameIdentity_success() {
        uniqueConcertList.add(COACHELLA);
        Concert editedC1 = new ConcertBuilder(COACHELLA).build();
        uniqueConcertList.setConcert(COACHELLA, editedC1);
        UniqueConcertList expectedUniqueConcertList = new UniqueConcertList();
        expectedUniqueConcertList.add(editedC1);
        assertEquals(expectedUniqueConcertList, uniqueConcertList);
    }

    @Test
    public void setConcert_editedConcertHasDifferentIdentity_success() {
        uniqueConcertList.add(COACHELLA);
        uniqueConcertList.setConcert(COACHELLA, GLASTONBURY);
        UniqueConcertList expectedUniqueConcertList = new UniqueConcertList();
        expectedUniqueConcertList.add(GLASTONBURY);
        assertEquals(expectedUniqueConcertList, uniqueConcertList);
    }

    @Test
    public void remove_nullConcert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.remove(null));
    }

    @Test
    public void remove_concertDoesNotExist_throwsConcertNotFoundException() {
        assertThrows(ConcertNotFoundException.class, () -> uniqueConcertList.remove(COACHELLA));
    }

    @Test
    public void remove_existingConcert_removesConcert() {
        uniqueConcertList.add(COACHELLA);
        uniqueConcertList.remove(COACHELLA);
        UniqueConcertList expectedUniqueConcertList = new UniqueConcertList();
        assertEquals(expectedUniqueConcertList, uniqueConcertList);
    }

    @Test
    public void setConcerts_nullUniqueConcertList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.setConcerts((UniqueConcertList) null));
    }

    @Test
    public void setConcerts_uniqueConcertList_replacesOwnListWithProvidedUniqueConcertList() {
        uniqueConcertList.add(COACHELLA);
        UniqueConcertList expectedUniqueConcertList = new UniqueConcertList();
        expectedUniqueConcertList.add(GLASTONBURY);
        uniqueConcertList.setConcerts(expectedUniqueConcertList);
        assertEquals(expectedUniqueConcertList, uniqueConcertList);
    }

    @Test
    public void setConcerts_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.setConcerts((List<Concert>) null));
    }

    @Test
    public void setConcerts_list_replacesOwnListWithProvidedList() {
        uniqueConcertList.add(COACHELLA);
        List<Concert> personList = Collections.singletonList(GLASTONBURY);
        uniqueConcertList.setConcerts(personList);
        UniqueConcertList expectedUniqueConcertList = new UniqueConcertList();
        expectedUniqueConcertList.add(GLASTONBURY);
        assertEquals(expectedUniqueConcertList, uniqueConcertList);
    }

    @Test
    public void setConcerts_listWithDuplicateConcerts_throwsDuplicateConcertException() {
        List<Concert> listWithDuplicateConcerts = Arrays.asList(COACHELLA, COACHELLA);
        assertThrows(DuplicateConcertException.class, () -> uniqueConcertList.setConcerts(listWithDuplicateConcerts));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueConcertList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueConcertList.asUnmodifiableObservableList().toString(), uniqueConcertList.toString());
    }
}
