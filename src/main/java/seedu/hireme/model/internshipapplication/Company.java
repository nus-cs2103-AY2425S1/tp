package seedu.hireme.model.internshipapplication;

import static java.util.Objects.requireNonNull;

import seedu.hireme.commons.util.AppUtil;
import seedu.hireme.logic.validator.EmailValidator;
import seedu.hireme.logic.validator.NameValidator;

/**
 * Represents a Company in the internship book.
 * Guarantees: immutable; the email and name are valid as declared in their respective validation methods.
 */
public class Company {

    private final Email email;
    private final Name name;

    /**
     * Constructs a {@code Company} object.
     *
     * @param email A valid email.
     * @param name A valid name.
     * @throws NullPointerException if the {@code email} or {@code name} is null.
     * @throws IllegalArgumentException if the {@code email} or {@code name} do not satisfy their constraints.
     */
    public Company(Email email, Name name) {
        requireNonNull(email);
        requireNonNull(name);
        AppUtil.checkArgument(EmailValidator.of().validate(email.toString()), Email.MESSAGE_CONSTRAINTS);
        AppUtil.checkArgument(NameValidator.of().validate(name.toString()), Name.MESSAGE_CONSTRAINTS);
        this.email = email;
        this.name = name;
    }

    /**
     * Returns the company's email.
     *
     * @return The email of the company.
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Returns the company's name.
     *
     * @return The name of the company.
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the string value of the company's name.
     *
     * @return The string representation of the company's name.
     */
    public String getNameValue() {
        return name.toString();
    }

    /**
     * Returns a string representation of the company, including its name and email.
     *
     * @return A formatted string representing the company.
     */
    @Override
    public String toString() {
        return String.format("Company: %s, Email: %s", name.toString(), email.toString());
    }

    /**
     * Checks whether this company is equal to another object.
     *
     * @param other The object to compare with.
     * @return True if the other object is a {@code Company} with the same email and name, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Company)) {
            return false;
        }

        Company otherCompany = (Company) other;
        return email.equals(otherCompany.email) && name.equals(otherCompany.name);
    }

    /**
     * Returns the hash code of the company.
     *
     * @return The combined hash code of the company's email and name.
     */
    @Override
    public int hashCode() {
        return email.hashCode() + name.hashCode();
    }
}
