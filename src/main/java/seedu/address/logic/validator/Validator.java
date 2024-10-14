package seedu.address.logic.validator;

public abstract class Validator<T> {
    public abstract boolean validate(T input);
}
