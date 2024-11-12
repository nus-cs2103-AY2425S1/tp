package seedu.address.model.person.comparator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class EmailCompareTest {

    private final EmailCompare comparator = new EmailCompare();
    private final Person p1 = ALICE;
    private final Person p2 = BOB;

    @Test
    public void compare_equal() {
        // EP: Emails are equal
        assertTrue(comparator.compare(p1, p1) == 0);
        assertTrue(comparator.compare(p2, p2) == 0);
    }

    @Test
    public void compare_lessThan() {
        // EP: first Email is less than second Email
        assertTrue(comparator.compare(p1, p2) <= -1);
    }

    @Test
    public void compare_greaterThan() {
        // EP: first Email is less than second Email
        assertTrue(comparator.compare(p2, p1) >= 1);
    }

}
