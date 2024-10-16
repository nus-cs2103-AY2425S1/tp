package seedu.address.model.concert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalConcerts.COACHELLA;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class ConcertContactTest {
    @Test
    void createConcertContact() {
        //checks if ConcertContact object can be created
        ConcertContact contactTest1 = new ConcertContact(ALICE, COACHELLA);

        assertEquals(COACHELLA, contactTest1.getConcert());
        assertEquals(ALICE, contactTest1.getPerson());
    }

    @Test
    void getContactFromConcertTest() {
        //checks if list of ConcertContacts can be retrieved from concert
        ConcertContact contactTest1 = new ConcertContact(ALICE, COACHELLA);
        ConcertContact contactTest2 = new ConcertContact(BOB, COACHELLA);

        ArrayList<ConcertContact> concertListTest = new ArrayList<>();

        concertListTest.add(contactTest1);
        concertListTest.add(contactTest2);

        assertEquals(concertListTest, contactTest1.getConcertContactList(COACHELLA));
    }

    @Test
    void toStringTest() {
        //checks output of toString method in Concert Contact class
        ConcertContact contactTest1 = new ConcertContact(ALICE, COACHELLA);
        String expectedString = ALICE.getName() + " is a " + ALICE.getRole() + " for " + COACHELLA.getName();

        assertEquals(contactTest1.toString(), expectedString);
    }

}
