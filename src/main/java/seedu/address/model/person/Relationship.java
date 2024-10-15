package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

public class Relationship {
    public static final String RELATIONSHIP_TYPE_CONSTRAINTS =
            "Relationship type should be Parent, Child, Sibling, Spouse, " +
            "Grandparent or Relative or their gendered variants";
    public static final String ALPHANUMERIC_CONSTRAINTS = "Relationship name should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    public final String relationship;
    public Relationship(String relationship) {
        requireNonNull(relationship);
        checkArgument(isAlphanumericRelationship(relationship), ALPHANUMERIC_CONSTRAINTS);
        checkArgument(isValidRelationship(relationship), RELATIONSHIP_TYPE_CONSTRAINTS);
        this.relationship = relationship;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isAlphanumericRelationship(String test) {
        return test.matches(VALIDATION_REGEX);
    }

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
