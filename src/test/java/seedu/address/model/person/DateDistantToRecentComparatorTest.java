package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class DateDistantToRecentComparatorTest {
    @Test
    public void compare() {
        DateDistantToRecentComparator dateDistantToRecentComparator = new DateDistantToRecentComparator();
        // first person has a later date -> returns negative number
        assertTrue(dateDistantToRecentComparator.compare(CARL, ALICE) < 0);
        // second person has a later date -> returns positive number
        assertTrue(dateDistantToRecentComparator.compare(ALICE, CARL) > 0);
        // same person -> returns 0
        assertTrue(dateDistantToRecentComparator.compare(ALICE, ALICE) == 0);
        // first person with lexicographically smaller email but all fields same -> returns negative number
        Person editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(dateDistantToRecentComparator.compare(ALICE, editedAlice) < 0);
        // second person with lexicographically smaller email but all fields same -> returns positive number
        assertTrue(dateDistantToRecentComparator.compare(editedAlice, ALICE) > 0);
    }

    @Test
    public void equals() {
        DateDistantToRecentComparator dateDistantToRecentComparator = new DateDistantToRecentComparator();
        DateRecentToDistantComparator dateRecentToDistantComparator = new DateRecentToDistantComparator();

        // same object -> returns true
        assertTrue(dateDistantToRecentComparator.equals(dateDistantToRecentComparator));
        // same comparator -> returns true
        assertTrue(dateDistantToRecentComparator.equals(new DateDistantToRecentComparator()));
        // different kind of comparator -> returns false
        assertFalse(dateDistantToRecentComparator.equals(dateRecentToDistantComparator));
        // check with null -> returns false
        assertFalse(dateDistantToRecentComparator.equals(null));
    }
}
