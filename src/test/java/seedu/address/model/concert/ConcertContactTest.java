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
        ConcertContact contactTest1 = new ConcertContact(ALICE, COACHELLA);

        assertEquals(COACHELLA, contactTest1.getConcert());
        assertEquals(ALICE, contactTest1.getPerson());
    }

    @Test
    void getContactFromConcertTest(){
        ConcertContact contactTest1 = new ConcertContact(ALICE, COACHELLA);
        ConcertContact contactTest2 = new ConcertContact(BOB, COACHELLA);

        ArrayList<ConcertContact> concertListTest = new ArrayList<>();

        concertListTest.add(contactTest1);
        concertListTest.add(contactTest2);

        assertEquals(concertListTest, contactTest1.concertContactList(COACHELLA));
    }

}
