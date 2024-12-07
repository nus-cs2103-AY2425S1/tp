package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class DateRecentToDistantComparatorTest {
    @Test
    public void compare() {
        DateRecentToDistantComparator recentToDistantComparator = new DateRecentToDistantComparator();
        // first person has an earlier date -> returns negative number
        assertTrue(recentToDistantComparator.compare(ALICE, CARL) < 0);
        // second person has an earlier date -> returns positive number
        assertTrue(recentToDistantComparator.compare(CARL, ALICE) > 0);
        // same person -> returns 0
        assertTrue(recentToDistantComparator.compare(ALICE, ALICE) == 0);
        // first person with lexicographically smaller email but all fields same -> returns negative number
        Person editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(recentToDistantComparator.compare(ALICE, editedAlice) < 0);
        // second person with lexicographically smaller email but all fields same -> returns positive number
        assertTrue(recentToDistantComparator.compare(editedAlice, ALICE) > 0);
    }

    @Test
    public void equals() {
        DateRecentToDistantComparator recentToDistantComparator = new DateRecentToDistantComparator();
        DateDistantToRecentComparator distantToRecentComparator = new DateDistantToRecentComparator();

        assertTrue(recentToDistantComparator.equals(recentToDistantComparator));
        assertTrue(recentToDistantComparator.equals(new DateRecentToDistantComparator()));
        assertFalse(recentToDistantComparator.equals(distantToRecentComparator));
        assertFalse(recentToDistantComparator.equals(null));
    }
}
