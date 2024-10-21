package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RelationshipTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidRelationship_throwsIllegalArgumentException() {
        String invalidRelationship = "";
        assertThrows(IllegalArgumentException.class, () -> new Relationship(invalidRelationship));
    }

    @Test
    public void isValidRelationship() {
        // null relationship
        assertThrows(NullPointerException.class, () -> Relationship.isValidRelationship(null));

        // invalid relationship
        assertFalse(Relationship.isValidRelationship("")); // empty string
        assertFalse(Relationship.isValidRelationship(" ")); // spaces only
        assertFalse(Relationship.isValidRelationship("123")); // only numbers
        assertFalse(Relationship.isValidRelationship("Father123")); // only non-alphanumeric characters
        assertFalse(Relationship.isValidRelationship("F@ther")); // contains non-alphanumeric characters

        // valid relationship
        assertTrue(Relationship.isValidRelationship("Father")); // alphabets only
    }

    @Test
    public void equals() {
        Relationship relationship = new Relationship("Father");

        // same values -> returns true
        assertTrue(relationship.equals(new Relationship("Father")));

        // same object -> returns true
        assertTrue(relationship.equals(relationship));

        // null -> returns false
        assertFalse(relationship.equals(null));

        // different types -> returns false
        assertFalse(relationship.equals(5.0f));

        // different values -> returns false
        assertFalse(relationship.equals(new Relationship("Brother")));
    }
}
