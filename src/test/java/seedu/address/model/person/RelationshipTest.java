package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RelationshipTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Relationship(null));
    }

    @Test
    public void constructor_invalidRelationship_throwsIllegalArgumentException() {
        String invalidRelationship = "";
        assertThrows(IllegalArgumentException.class, () -> new Relationship(invalidRelationship));
    }

    @Test
    public void isAlphanumericRelationship() {
        // null relationship
        assertThrows(NullPointerException.class, () -> Relationship.isAlphanumericRelationship(null));

        // invalid relationship
        assertFalse(Relationship.isAlphanumericRelationship("")); // empty string
        assertFalse(Relationship.isAlphanumericRelationship(" ")); // spaces only
        assertFalse(Relationship.isAlphanumericRelationship("^")); // only non-alphanumeric characters
        assertFalse(Relationship.isAlphanumericRelationship("peter*")); // contains non-alphanumeric characters

        // valid relationship
        assertTrue(Relationship.isAlphanumericRelationship("peter jack")); // alphabets only
        assertTrue(Relationship.isAlphanumericRelationship("12345")); // numbers only
        assertTrue(Relationship.isAlphanumericRelationship("peter the 2nd")); // alphanumeric characters
        assertTrue(Relationship.isAlphanumericRelationship("Capital Tan")); // with capital letters
        assertTrue(Relationship.isAlphanumericRelationship("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void isValidRelationship() {

        // invalid relationship
        assertFalse(Relationship.isValidRelationship("Tutor"));
        assertFalse(Relationship.isValidRelationship("teacher"));
        assertFalse(Relationship.isValidRelationship("baby"));
        assertFalse(Relationship.isValidRelationship("neighbour"));

        // valid relationship
        assertTrue(Relationship.isValidRelationship("Son")); // alphabets only
        assertTrue(Relationship.isValidRelationship("Daughter")); // numbers only
        assertTrue(Relationship.isValidRelationship("Grandson")); // alphanumeric characters
        assertTrue(Relationship.isValidRelationship("Granddaughter")); // with capital letters
        assertTrue(Relationship.isValidRelationship("Child")); // long names
    }

    @Test
    public void equals() {
        Relationship relationship = new Relationship("Sibling");

        // same values -> returns true
        assertTrue(relationship.equals(new Relationship("Sibling")));

        // same object -> returns true
        assertTrue(relationship.equals(relationship));

        // null -> returns false
        assertFalse(relationship.equals(null));

        // different types -> returns false
        assertFalse(relationship.equals(5.0f));

        // different values -> returns false
        assertFalse(relationship.equals(new Relationship("Cousin")));
    }
}
