package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RelationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Relation(null));
    }

    @Test
    public void constructor_invalidRsvp_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Relation("invalidRelation"));
    }

    @Test
    public void isValidRelation() {
        // null Rsvp
        assertThrows(NullPointerException.class, () -> Relation.isValidRelation(null));

        // invalid Relation
        assertFalse(Relation.isValidRelation("")); // empty string
        assertFalse(Relation.isValidRelation(" ")); // spaces only
        assertFalse(Relation.isValidRelation("^")); // only non-alphanumeric characters
        assertFalse(Relation.isValidRelation("HW")); // contains non-alphanumeric characters

        // valid Rsvp
        assertTrue(Relation.isValidRelation("H"));
        assertTrue(Relation.isValidRelation("W"));
        assertTrue(Relation.isValidRelation("U"));
        assertTrue(Relation.isValidRelation("h"));

    }

    @Test
    public void equals() {
        Relation relation = new Relation("H");

        // same values -> returns true
        assertTrue(relation.equals(new Relation("H")));

        // same object -> returns true
        assertTrue(relation.equals(relation));

        // null -> returns false
        assertFalse(relation.equals(null));

        // different types -> returns false
        assertFalse(relation.equals(5.0f));

        // different values -> returns false
        assertFalse(relation.equals(new Relation("W")));
    }

    @Test
    public void hashcode() {
        Relation relationH = new Relation("H");
        Relation relationW = new Relation("W");
        Relation relationHCopy = new Relation("H");

        // Same values should have same hash code
        assertEquals(relationH.hashCode(), relationHCopy.hashCode());

        // Different values should have different hash codes
        assertNotEquals(relationH.hashCode(), relationW.hashCode());
    }
}
