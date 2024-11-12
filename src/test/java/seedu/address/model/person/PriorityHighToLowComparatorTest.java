package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PriorityHighToLowComparatorTest {
    @Test
    public void compare() {
        PriorityHighToLowComparator priorityHighToLowComparator = new PriorityHighToLowComparator();
        // first person has a higher priority -> returns negative number
        assertTrue(priorityHighToLowComparator.compare(BENSON, ALICE) < 0);
        // second person has a higher priority -> returns positive number
        assertTrue(priorityHighToLowComparator.compare(ALICE, BENSON) > 0);
        // same person -> returns 0
        assertTrue(priorityHighToLowComparator.compare(ALICE, ALICE) == 0);
        // first person with lexicographically smaller email but all fields same -> returns negative number
        Person editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(priorityHighToLowComparator.compare(ALICE, editedAlice) < 0);
        // second person with lexicographically smaller email but all fields same -> returns positive number
        assertTrue(priorityHighToLowComparator.compare(editedAlice, ALICE) > 0);
    }

    @Test
    public void equals() {
        PriorityHighToLowComparator highToLowComparator = new PriorityHighToLowComparator();
        PriorityLowToHighComparator lowToHighComparator = new PriorityLowToHighComparator();

        assertTrue(highToLowComparator.equals(highToLowComparator));
        assertTrue(highToLowComparator.equals(new PriorityHighToLowComparator()));
        assertFalse(highToLowComparator.equals(lowToHighComparator));
        assertFalse(highToLowComparator.equals(null));
    }
}
