package seedu.address.logic.validator;

public abstract class Validator<T> {

    /**
     * Validates if input {@code input} is in the correct format.
     *
     * @return true if input is valid or false if input is invalid {@code T}.
     */
    public abstract boolean validate(T input);
}
