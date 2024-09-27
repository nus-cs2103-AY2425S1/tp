package seedu.address.model.person;

/**
 * Represents a Person's class ID in the address book.
 */
public class ClassId {
    public static final String MESSAGE_CONSTRAINTS = "Class ID can take any values, and it should not be blank";

    public final int value;

    /**
     * Constructs a {@code ClassId}.
     */
    public ClassId(int classId) {
        this.value = classId;
    }

    public static boolean isValidClassId(int classId) {
        return classId >= 0;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClassId otherClassId)) {
            return false;
        }

        return value == otherClassId.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
