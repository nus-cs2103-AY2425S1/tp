package seedu.address.model.link;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalOwners.ALICE;
import static seedu.address.testutil.TypicalOwners.BOB;
import static seedu.address.testutil.TypicalPets.AARFUL;
import static seedu.address.testutil.TypicalPets.FLUFFY;
import static seedu.address.testutil.TypicalPets.MILO;

import org.junit.jupiter.api.Test;

public class LinkTest {

    @Test
    public void testLinkEquality() {
        // different instances of same link -> returns true
        assertTrue(new Link(ALICE, BOB).equals(new Link(ALICE, BOB)));
        assertTrue(new Link(FLUFFY, MILO).equals(new Link(FLUFFY, MILO)));
        assertTrue(new Link(ALICE, MILO).equals(new Link(ALICE, MILO)));

        // comparison with null -> returns false
        assertFalse(new Link(ALICE, BOB).equals(null));

        // different instances of different link -> returns false
        assertFalse(new Link(ALICE, BOB).equals(new Link(BOB, ALICE)));
        assertFalse(new Link(ALICE, BOB).equals(new Link(FLUFFY, MILO)));
        assertFalse(new Link(ALICE, BOB).equals(new Link(ALICE, MILO)));
        assertFalse(new Link(ALICE, BOB).equals(new Link(FLUFFY, BOB)));
    }

    @Test
    public void descriptionMethod() {
        Link link = new Link(ALICE, AARFUL);
        String expected = "Link from owner " + ALICE.getUniqueID() + " to pet " + AARFUL.getUniqueID().substring(1);
        assertEquals(expected, new Link(ALICE, AARFUL).description());
    }

    @Test
    public void toStringMethod() {
        String expected = Link.class.getCanonicalName() + "{from=" + ALICE.getUniqueID() + ", to="
            + BOB.getUniqueID() + "}";
        assertEquals(expected, new Link(ALICE, BOB).toString());
    }
}
