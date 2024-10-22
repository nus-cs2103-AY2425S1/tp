package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Module in the Address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidModule(String)}
 */
public class Module {

    public static final String MESSAGE_CONSTRAINTS =
            "Modules should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the Module must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9]+$";

    public final String value;
    private Grade grade;

    /**
     * Constructs a {@code Module}.
     *
     * @param module A valid Module.
     */
    public Module(String module) {
        requireNonNull(module);
        checkArgument(isValidModule(module), MESSAGE_CONSTRAINTS);
        this.value = module;
    }

    /**
     * Returns true if a given string is a valid module.
     */
    public static boolean isValidModule(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    public Grade getGrade() {
        return this.grade;
    }

    public boolean hasGrade() {
        return this.grade != null;
    }

    @Override
    public String toString() {
        return value + ": " + (grade == null ? "Grade not assigned" : grade.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return value.equals(otherModule.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
