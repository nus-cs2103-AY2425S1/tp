package seedu.address.model.person.comparator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class SexCompareTest {

    private final SexCompare comparator = new SexCompare();
    private final Person p1 = ALICE;
    private final Person p2 = BOB;

    @Test
    public void compare_equal() {
        // EP: Sexes are equal
        assertTrue(comparator.compare(p1, p1) == 0);
        assertTrue(comparator.compare(p2, p2) == 0);
    }

    @Test
    public void compare_lessThan() {
        // EP: first Sex is less than second Sex
        assertTrue(comparator.compare(p1, p2) <= -1);
    }

    @Test
    public void compare_greaterThan() {
        // EP: first Sex is less than second Sex
        assertTrue(comparator.compare(p2, p1) >= 1);
    }

}
