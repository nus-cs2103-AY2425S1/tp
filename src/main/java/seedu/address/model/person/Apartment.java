package seedu.address.model.person;

public class Apartment extends Property {

    public static final String MESSAGE_CONSTRAINTS = "Apartment names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+"; // Checks if the Apartment name is alphanumeric

    /**
     * Constructs a {@code Apartment}.
     *
     * @param apartmentName A valid apartment name.
     * @param unitNumber A valid unit number.
     * @param price A valid price.
     */
    public Apartment(String apartmentName, String unitNumber, String price) {
        super(apartmentName, unitNumber, price);
    }

    /**
     * Returns true if a given string is a valid Apartment name.
     */
    public static boolean isValidApartmentName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
