package seedu.address.model.concert;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalConcertContacts.ALICE_COACHELLA;
import static seedu.address.testutil.TypicalConcertContacts.BENSON_COACHELLA;
import static seedu.address.testutil.TypicalConcerts.COACHELLA;
import static seedu.address.testutil.TypicalConcerts.GLASTONBURY;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.concert.exceptions.ConcertContactNotFoundException;
import seedu.address.model.concert.exceptions.DuplicateConcertContactException;
import seedu.address.model.person.Person;
import seedu.address.testutil.ConcertContactBuilder;
import seedu.address.testutil.PersonBuilder;

public class UniqueConcertContactListTest {
    private final UniqueConcertContactList uniqueConcertContactList =
            new UniqueConcertContactList();

    @Test
    public void contains_nullConcertContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertContactList.contains(null));
    }

    @Test
    public void contains_concertContactNotInList_returnsFalse() {
        assertFalse(uniqueConcertContactList.contains(ALICE_COACHELLA));
    }

    @Test
    public void contains_person_concert() {
        uniqueConcertContactList.add(BENSON_COACHELLA);

        // null throws NullPointerException
        assertThrows(NullPointerException.class, () -> uniqueConcertContactList.contains(
                (Person) null, (Concert) null));

        // inside returns true
        assertTrue(uniqueConcertContactList.contains(BENSON, COACHELLA));

        // not inside returns false
        assertFalse(uniqueConcertContactList.contains(ALICE, COACHELLA));
    }

    @Test
    public void contains_concertContactInList_returnsTrue() {
        uniqueConcertContactList.add(ALICE_COACHELLA);
        assertTrue(uniqueConcertContactList.contains(ALICE_COACHELLA));
    }

    @Test
    public void add_nullConcertContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertContactList.add(null));
    }

    @Test
    public void add_duplicateConcertContact_throwsDuplicateConcertException() {
        uniqueConcertContactList.add(ALICE_COACHELLA);
        assertThrows(DuplicateConcertContactException.class, () -> uniqueConcertContactList.add(
                ALICE_COACHELLA));
    }

    @Test
    public void add_differentFieldConcertContact_success() {
        uniqueConcertContactList.add(ALICE_COACHELLA);

        // change email field
        Person editedAlice = new PersonBuilder(ALICE).withEmail("new_email@example.com").build();
        ConcertContact cc = new ConcertContactBuilder(ALICE_COACHELLA).withPerson(editedAlice).build();
        assertDoesNotThrow(() -> uniqueConcertContactList.add(cc));
    }

    @Test
    public void setConcertContact_nullTargetConcert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertContactList.setConcertContact(
                null, ALICE_COACHELLA));
    }

    @Test
    public void setConcertContact_nullEditedConcert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConcertContactList.setConcertContact(
                ALICE_COACHELLA, null));
    }

    @Test
    public void setConcertContact_targetConcertContactNotInList_throwsConcertNotFoundException() {
        assertThrows(ConcertContactNotFoundException.class, () -> uniqueConcertContactList
                .setConcertContact(BENSON_COACHELLA, ALICE_COACHELLA));
    }

    @Test
    public void setConcertContact_editedConcertIsSameConcert_success() {
        uniqueConcertContactList.add(ALICE_COACHELLA);
        uniqueConcertContactList.setConcertContact(ALICE_COACHELLA, ALICE_COACHELLA);
        UniqueConcertContactList expectedConcertContactList = new UniqueConcertContactList();
        expectedConcertContactList.add(ALICE_COACHELLA);
        assertEquals(expectedConcertContactList, uniqueConcertContactList);
    }

    @Test
    public void setConcertContact_editedConcertDifferent_success() {
        uniqueConcertContactList.add(ALICE_COACHELLA);
        uniqueConcertContactList.setConcertContact(ALICE_COACHELLA, BENSON_COACHELLA);
        UniqueConcertContactList expectedConcertContactList = new UniqueConcertContactList();
        expectedConcertContactList.add(BENSON_COACHELLA);
        assertEquals(expectedConcertContactList, uniqueConcertContactList);
    }

    @Test
    public void remove_nullConcertContact_throwsNullPointerException() {
        // remove(person)
        assertThrows(NullPointerException.class, () -> uniqueConcertContactList.remove(
                (Person) null));

        // remove(concert)
        assertThrows(NullPointerException.class, () -> uniqueConcertContactList.remove(
                (Concert) null));

        // remove(Person, Concert)
        assertThrows(NullPointerException.class, () -> uniqueConcertContactList.remove(
                (ConcertContact) null));
    }

    @Test
    public void remove_concertContactNotInList_throwsConcertContactNotFoundException() {
        assertThrows(ConcertContactNotFoundException.class, () -> uniqueConcertContactList.remove(
                ALICE_COACHELLA));

        // overloaded methods do not throw
        assertDoesNotThrow(() -> uniqueConcertContactList.remove(ALICE));
        assertDoesNotThrow(() -> uniqueConcertContactList.remove(COACHELLA));
    }

    @Test
    public void remove_multipleConcertContacts_success() {
        // Remove all instances of Coachella concert
        uniqueConcertContactList.add(ALICE_COACHELLA);
        uniqueConcertContactList.add(BENSON_COACHELLA);

        uniqueConcertContactList.remove(COACHELLA);
        assertFalse(uniqueConcertContactList.contains(ALICE_COACHELLA));
        assertFalse(uniqueConcertContactList.contains(BENSON_COACHELLA));

        // Remove all instances of Alice
        uniqueConcertContactList.add(ALICE_COACHELLA);
        ConcertContact cc = new ConcertContactBuilder(ALICE_COACHELLA).withConcert(GLASTONBURY).build();
        uniqueConcertContactList.add(cc);

        uniqueConcertContactList.remove(ALICE);
        assertFalse(uniqueConcertContactList.contains(ALICE_COACHELLA));
        assertFalse(uniqueConcertContactList.contains(cc));
    }

    @Test
    public void setConcertContact_duplicateConcertContact_throwsDuplicateConcertContactException() {
        uniqueConcertContactList.add(BENSON_COACHELLA);
        uniqueConcertContactList.add(ALICE_COACHELLA);
        assertThrows(DuplicateConcertContactException.class, () -> uniqueConcertContactList
                .setConcertContact(ALICE_COACHELLA, BENSON_COACHELLA));
    }

    @Test
    public void setConcertContacts_replacement() {
        // valid replacement
        uniqueConcertContactList.add(ALICE_COACHELLA);
        UniqueConcertContactList expectedConcertContactList = new UniqueConcertContactList();
        expectedConcertContactList.add(BENSON_COACHELLA);
        uniqueConcertContactList.setConcertContacts(expectedConcertContactList);
        assertEquals(expectedConcertContactList, uniqueConcertContactList);

        // valid list
        List<ConcertContact> valid = Arrays.asList(BENSON_COACHELLA);
        uniqueConcertContactList.setConcertContacts(valid);
        assertEquals(expectedConcertContactList, uniqueConcertContactList);

        // duplicates in replacement -> throws exception
        List<ConcertContact> duplicates = Arrays.asList(BENSON_COACHELLA, BENSON_COACHELLA);
        assertThrows(DuplicateConcertContactException.class, () -> uniqueConcertContactList
                .setConcertContacts(duplicates));

        // null list -> throws exception
        assertThrows(NullPointerException.class, () -> uniqueConcertContactList.setConcertContacts(
                (List<ConcertContact>) null));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueConcertContactList
                .asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueConcertContactList.asUnmodifiableObservableList().toString(),
                uniqueConcertContactList.toString());
    }

    @Test
    public void equals() {
        uniqueConcertContactList.add(BENSON_COACHELLA);
        UniqueConcertContactList test = new UniqueConcertContactList();
        test.add(BENSON_COACHELLA);

        // same object returns true
        assertTrue(uniqueConcertContactList.equals(uniqueConcertContactList));

        // same contents returns true
        assertTrue(uniqueConcertContactList.equals(test));

        // different returns false
        test.add(ALICE_COACHELLA);
        assertFalse(uniqueConcertContactList.equals(test));
    }
}
