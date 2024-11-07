package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Relationship between a Person and their EmergencyContact
 * Guarantees: immutable; is valid as declared in {@link #isValidRelationship(String)}
 * and {@link #isAlphanumericRelationship(String)}.
 */
public class Relationship {
    public static final String RELATIONSHIP_TYPE_CONSTRAINTS =
            "Relationship type should be Parent, Child, Sibling, Spouse, "
            + "Grandparent or Relative or their gendered variants";
    public static final String ALPHANUMERIC_CONSTRAINTS = "Relationship name "
            + "should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String relationship;

    /**
     * Constructs a {@code Relationship}.
     *
     * @param relationship A valid relationship type.
     */
    public Relationship(String relationship) {
        requireNonNull(relationship);
        checkArgument(isAlphanumericRelationship(relationship), ALPHANUMERIC_CONSTRAINTS);
        checkArgument(isValidRelationship(relationship), RELATIONSHIP_TYPE_CONSTRAINTS);
        this.relationship = getRelationshipString(relationship);
    }

    private static String getRelationshipString(String relationship) {
        assert isValidRelationship(relationship);
        return relationship.substring(0, 1).toUpperCase() + relationship.substring(1).toLowerCase();
    }

    /**
     * Returns true if a given string is a valid relationship name alphanumerically.
     */
    public static boolean isAlphanumericRelationship(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string is a valid relationship type.
     *
     * @param relationship The relationship string to test.
     * @return True if the string is a valid relationship type, false otherwise.
     */
    public static Boolean isValidRelationship(String relationship) {
        for (RelationshipType relationshipType : RelationshipType.values()) {
            if (relationshipType.name().equals(relationship.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return relationship;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(relationship);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Relationship)) {
            return false;
        }

        Relationship otherRelationship = (Relationship) other;
        return relationship.equals(otherRelationship.relationship);
    }

    /**
     * Enum representing different types of relationships.
     */
    private enum RelationshipType {
        PARENT,
        MOTHER,
        FATHER,
        CHILD,
        SON,
        DAUGHTER,
        SIBLING,
        BROTHER,
        SISTER,
        FRIEND,
        SPOUSE,
        HUSBAND,
        WIFE,
        PARTNER,
        COUSIN,
        RELATIVE,
        UNCLE,
        AUNT,
        GRANDPARENT,
        GRANDMOTHER,
        GRANDFATHER,
        GRANDCHILD,
        GRANDSON,
        GRANDDAUGHTER;
    }

}
