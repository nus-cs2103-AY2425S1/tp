package seedu.address.model.person;

public class HDB extends Property {

    public static final String MESSAGE_CONSTRAINTS = "HDB names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+"; // Checks if the HDB name is alphanumeric


    public HDB(String hdbName, String unitNumber, String price) {
        super(hdbName, unitNumber, price);
    }

    /**
    * Returns true if a given string is a valid HDB name.
    */
    public static boolean isValidHdbName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
