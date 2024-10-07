package seedu.address.model.person;

public class OtherProperty extends Property {

    public static final String MESSAGE_CONSTRAINTS = "Other Property names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+"; // Checks if the Other Property name is alphanumeric

    public OtherProperty(String otherPropertyName, String unitNumber, String price) {
        super(otherPropertyName, unitNumber, price);
    }

    /**
    * Returns true if a given string is a valid Other Property name.
    */
    public static boolean isValidOtherPropertyName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
