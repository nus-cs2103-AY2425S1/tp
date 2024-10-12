//package seedu.address.model.delivery;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;
//
///**
// * Represents a Delivery's description in the delivery.
// */
//public class Description {
//
//    public static final String MESSAGE_CONSTRAINTS =
//            "Description should not be blank, and it should only contain alphanumeric characters and spaces.";
//
//    /*
//     * The first character of the description must not be a whitespace,
//     * and the description must not be blank.
//     */
//    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
//
//    public final String value;
//
//    /**
//     * Constructs a {@code Description}.
//     *
//     * @param description A valid description.
//     */
//    public Description(String description) {
//        requireNonNull(description);
//        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
//        this.value = description;
//    }
//
//    /**
//     * Returns true if a given string is a valid description.
//     */
//    public static boolean isValidDescription(String test) {
//        return test.matches(VALIDATION_REGEX);
//    }
//
//    @Override
//    public String toString() {
//        return value;
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//
//        if (!(other instanceof Description)) {
//            return false;
//        }
//
//        Description otherDescription = (Description) other;
//        return value.equals(otherDescription.value);
//    }
//
//    @Override
//    public int hashCode() {
//        return value.hashCode();
//    }
//
//}
