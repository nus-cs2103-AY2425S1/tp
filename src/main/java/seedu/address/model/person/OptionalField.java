package seedu.address.model.person;

/**
 * The API of an OptionalField that could contain nothing as a value.
 */
public interface OptionalField {
    boolean isEmpty();
    String getValueForUi();
}
