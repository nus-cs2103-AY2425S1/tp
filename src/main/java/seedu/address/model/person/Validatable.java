package seedu.address.model.person;

/**
 * Interface representing an entity that can be validated.
 * Classes implementing this interface should define logic for validating
 * a provided string.
 */
public interface Validatable {

    /**
     * Validates the given string.
     *
     * @param test the string to be validated.
     * @return true if the string passes validation, false otherwise.
     */
    boolean validate(String test);
}


