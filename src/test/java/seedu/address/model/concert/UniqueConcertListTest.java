package seedu.address.model.concert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.commons.Address;
import seedu.address.model.commons.Name;
import seedu.address.model.concert.exceptions.ConcertNotFoundException;
import seedu.address.model.concert.exceptions.DuplicateConcertException;
import seedu.address.testutil.ConcertBuilder;

public class UniqueConcertListTest {
    // TODO: extract to typical concerts
    private final Concert c1 = new Concert(new Name("Concert C1"), new Address("C1"),
            new ConcertDate("2002-10-10 1000"));

    private final Concert c2 = new Concert(new Name("Concert C2"), new Address("C2"),
            new ConcertDate("2024-10-10 2359"));

    private final UniqueConcertList uniqueConcertList = new UniqueConcertList();

    @Test
    public void contains_nullConcert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.contains(null));
    }

    @Test
    public void contains_concertNotInList_returnsFalse() {
        assertFalse(uniqueConcertList.contains(c1));
    }

    @Test
    public void contains_concertInList_returnsTrue() {
        uniqueConcertList.add(c1);
        assertTrue(uniqueConcertList.contains(c1));
    }

    @Test
    public void contains_concertWithSameIdentityFieldsInList_returnsTrue() {
        uniqueConcertList.add(c1);
        Concert editedC1 = new ConcertBuilder(c1).build();
        assertTrue(uniqueConcertList.contains(editedC1));
    }

    @Test
    public void add_nullConcert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.add(null));
    }

    @Test
    public void add_duplicateConcert_throwsDuplicateConcertException() {
        uniqueConcertList.add(c1);
        assertThrows(DuplicateConcertException.class, () -> uniqueConcertList.add(c1));
    }

    @Test
    public void setConcert_nullTargetConcert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.setConcert(null, c1));
    }

    @Test
    public void setConcert_nullEditedConcert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.setConcert(c1, null));
    }

    @Test
    public void setConcert_targetConcertNotInList_throwsConcertNotFoundException() {
        assertThrows(ConcertNotFoundException.class, () -> uniqueConcertList.setConcert(c1, c1));
    }

    @Test
    public void setConcert_editedConcertIsSameConcert_success() {
        uniqueConcertList.add(c1);
        uniqueConcertList.setConcert(c1, c1);
        UniqueConcertList expectedUniqueConcertList = new UniqueConcertList();
        expectedUniqueConcertList.add(c1);
        assertEquals(expectedUniqueConcertList, uniqueConcertList);
    }

    @Test
    public void setConcert_editedConcertHasSameIdentity_success() {
        uniqueConcertList.add(c1);
        Concert editedC1 = new ConcertBuilder(c1).build();
        uniqueConcertList.setConcert(c1, editedC1);
        UniqueConcertList expectedUniqueConcertList = new UniqueConcertList();
        expectedUniqueConcertList.add(editedC1);
        assertEquals(expectedUniqueConcertList, uniqueConcertList);
    }

    @Test
    public void setConcert_editedConcertHasDifferentIdentity_success() {
        uniqueConcertList.add(c1);
        uniqueConcertList.setConcert(c1, c2);
        UniqueConcertList expectedUniqueConcertList = new UniqueConcertList();
        expectedUniqueConcertList.add(c2);
        assertEquals(expectedUniqueConcertList, uniqueConcertList);
    }

    @Test
    public void remove_nullConcert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.remove(null));
    }

    @Test
    public void remove_concertDoesNotExist_throwsConcertNotFoundException() {
        assertThrows(ConcertNotFoundException.class, () -> uniqueConcertList.remove(c1));
    }

    @Test
    public void remove_existingConcert_removesConcert() {
        uniqueConcertList.add(c1);
        uniqueConcertList.remove(c1);
        UniqueConcertList expectedUniqueConcertList = new UniqueConcertList();
        assertEquals(expectedUniqueConcertList, uniqueConcertList);
    }

    @Test
    public void setConcerts_nullUniqueConcertList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.setConcerts((UniqueConcertList) null));
    }

    @Test
    public void setConcerts_uniqueConcertList_replacesOwnListWithProvidedUniqueConcertList() {
        uniqueConcertList.add(c1);
        UniqueConcertList expectedUniqueConcertList = new UniqueConcertList();
        expectedUniqueConcertList.add(c2);
        uniqueConcertList.setConcerts(expectedUniqueConcertList);
        assertEquals(expectedUniqueConcertList, uniqueConcertList);
    }

    @Test
    public void setConcerts_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertList.setConcerts((List<Concert>) null));
    }

    @Test
    public void setConcerts_list_replacesOwnListWithProvidedList() {
        uniqueConcertList.add(c1);
        List<Concert> personList = Collections.singletonList(c2);
        uniqueConcertList.setConcerts(personList);
        UniqueConcertList expectedUniqueConcertList = new UniqueConcertList();
        expectedUniqueConcertList.add(c2);
        assertEquals(expectedUniqueConcertList, uniqueConcertList);
    }

    @Test
    public void setConcerts_listWithDuplicateConcerts_throwsDuplicateConcertException() {
        List<Concert> listWithDuplicateConcerts = Arrays.asList(c1, c1);
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
