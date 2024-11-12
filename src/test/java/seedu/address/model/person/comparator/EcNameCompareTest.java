package seedu.address.model.person.comparator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class EcNameCompareTest {

    private final EcNameCompare comparator = new EcNameCompare();
    private final Person p1 = ALICE;
    private final Person p2 = BOB;

    @Test
    public void compare_equal() {
        // EP: EcNames are equal
        assertTrue(comparator.compare(p1, p1) == 0);
        assertTrue(comparator.compare(p2, p2) == 0);
    }

    @Test
    public void compare_lessThan() {
        // EP: first EcName is less than second EcName
        assertTrue(comparator.compare(p1, p2) <= -1);
    }

    @Test
    public void compare_greaterThan() {
        // EP: first EcName is more than the second EcName
        assertTrue(comparator.compare(p2, p1) >= 1);
    }

}
