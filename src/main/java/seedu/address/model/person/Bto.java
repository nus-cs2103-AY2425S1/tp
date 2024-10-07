package seedu.address.model.person;

public class Bto extends Property {

    public static final String MESSAGE_CONSTRAINTS = "BTO names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+"; // Checks if the BTO name is alphanumeric

    /**
     * Constructs a {@code BTO}.
     *
     * @param btoName A valid BTO name.
     * @param unitNumber A valid unit number.
     * @param price A valid price.
     */
    public Bto(String btoName, String unitNumber, String price) {
        super(btoName, unitNumber, price);
    }

    /**
     * Returns true if a given string is a valid BTO name.
     */
    public static boolean isValidBTOName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
