package seedu.address.model.person.comparator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class RegisterNumberCompareTest {

    private final RegisterNumberCompare comparator = new RegisterNumberCompare();
    private final Person p1 = ALICE;
    private final Person p2 = BOB;

    @Test
    public void compare_equal() {
        // EP: RegisterNumbers are equal
        assertTrue(comparator.compare(p1, p1) == 0);
        assertTrue(comparator.compare(p2, p2) == 0);
    }

    @Test
    public void compare_lessThan() {
        // EP: first RegisterNumber is less than second RegisterNumber
        assertTrue(comparator.compare(p2, p1) <= -1);
    }

    @Test
    public void compare_greaterThan() {
        // EP: first RegisterNumber is less than second RegisterNumber
        assertTrue(comparator.compare(p1, p2) >= 1);
    }

}
