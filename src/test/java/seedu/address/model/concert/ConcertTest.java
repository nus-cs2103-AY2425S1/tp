package seedu.address.model.concert;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.commons.Address;
import seedu.address.model.commons.Name;

public class ConcertTest {
    // TODO: extract to typical concerts
    private final Concert c1 = new Concert(new Name("Concert C1"), new Address("C1"),
            new ConcertDate("2002-10-10 1000"));

    @Test
    public void isSameConcert() {
        // same object -> returns true
        assertTrue(c1.isSameConcert(c1));

        // null -> returns false
        assertFalse(c1.isSameConcert(null));
    }
}
