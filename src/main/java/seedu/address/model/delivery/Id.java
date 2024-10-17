package seedu.address.model.delivery;

/**
 * Represents a delivery's id.
 * Guarantees: is valid as declared in {@link #isValidId(String)}
 */
public class Id {

    public static final String MESSAGE_CONSTRAINTS = "Id should be integer";
    private static int count = 0;

    public final String value;

    /**
     * Constructs a {@code Id}.
     */
    public Id() {
        this.value = String.valueOf(Id.count);
        Id.count += 1;
    }

    /**
     * Constructs a {@code Id}.
     * Used when a specific id is to be used.
     * Ensures no overlapping id by updating count when creating new ids, which might be an issue when reading from data
     * @param id A string with an integer
     */
    public Id(String id) {
        this.value = id;
        int intId = Integer.parseInt(id);
        count = Math.max(intId, count);
    }

    /**
     * Returns true if a given string is a valid id.
     */
    public static boolean isValidId(String test) {
        try {
            int intId = Integer.parseInt(test);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Id: " + value;
    }
}
