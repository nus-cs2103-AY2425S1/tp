package seedu.address.model.concert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalConcertContacts.ALICE_COACHELLA;
import static seedu.address.testutil.TypicalConcertContacts.ALICE_GLASTONBURY;
import static seedu.address.testutil.TypicalConcertContacts.BENSON_COACHELLA;
import static seedu.address.testutil.TypicalConcerts.COACHELLA;
import static seedu.address.testutil.TypicalConcerts.GLASTONBURY;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ConcertContactTest {
    @Test
    void createConcertContact() {
        // checks if ConcertContact object can be created
        ConcertContact contactTest1 = new ConcertContact(ALICE, COACHELLA);

        assertEquals(COACHELLA, contactTest1.getConcert());
        assertEquals(ALICE, contactTest1.getPerson());
    }

    @Test
    public void isSameConcertContact() {
        // same concert returns true
        assertTrue(ALICE_COACHELLA.isSameConcertContact(ALICE_COACHELLA));

        // different person returns false
        assertFalse(ALICE_COACHELLA.isSameConcertContact(BENSON_COACHELLA));

        // different concert returns false
        assertFalse(ALICE_COACHELLA.isSameConcertContact(ALICE_GLASTONBURY));
    }

    @Test
    public void isAssociated_person() {
        // person in concertContact returns true
        assertTrue(ALICE_COACHELLA.isAssociated(ALICE));

        // person not in concertContact returns false
        assertFalse(ALICE_COACHELLA.isAssociated(BOB));

        // person is null returns false
        assertFalse(ALICE_COACHELLA.isAssociated((Person) null));

        // person's email is different returns false
        Person editedAlice = new PersonBuilder(ALICE).withEmail("edited_email@gmail.com").build();
        assertFalse(ALICE_COACHELLA.isAssociated(editedAlice));
    }

    @Test
    public void isAssociated_concert() {
        // concert is the same returns true
        assertTrue(ALICE_COACHELLA.isAssociated(COACHELLA));

        // concert different returns false
        assertFalse(ALICE_COACHELLA.isAssociated(GLASTONBURY));

        // null returns false
        assertFalse(ALICE_COACHELLA.isAssociated((Concert) null));
    }

    @Test
    public void equals() {
        ConcertContact contactTest1 = new ConcertContact(ALICE, COACHELLA);
        ConcertContact contactTest2 = new ConcertContact(BOB, COACHELLA);

        assertTrue(contactTest1.equals(contactTest1)); // should return true since same contact
        assertFalse(contactTest1.equals(contactTest2)); // should return false since different contact
    }

    @Test
    public void toStringMethod() {
        String expected = ConcertContact.class.getCanonicalName() + "{concert=" + ALICE_COACHELLA.getConcert()
                + ", person=" + ALICE_COACHELLA.getPerson() + "}";
        assertEquals(expected, ALICE_COACHELLA.toString());
    }

}
