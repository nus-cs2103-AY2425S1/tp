package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Company in the internship book.
 * Guarantees: immutable; the email and name are valid as declared in their respective validation methods.
 */
public class Company {

    private final Email email;
    private final Name name;

    /**
     * Constructs a {@code Company} with a valid email and name.
     *
     * @param email A valid email.
     * @param name  A valid name.
     * @throws NullPointerException if the {@code email} or {@code name} is null.
     * @throws IllegalArgumentException if the {@code email} or {@code name} do not satisfy their constraints.
     */
    public Company(Email email, Name name) {
        requireNonNull(email);
        requireNonNull(name);
        checkArgument(email.validate(email.getValue()), Email.MESSAGE_CONSTRAINTS);
        checkArgument(name.validate(name.getValue()), Name.MESSAGE_CONSTRAINTS);
        this.email = email;
        this.name = name;
    }

    /**
     * Returns the company's email.
     *
     * @return the email object.
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Returns the company's name.
     *
     * @return the name object.
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the string representation of the company.
     *
     * @return a string that represents the company, including its email and name.
     */
    @Override
    public String toString() {
        return String.format("Company: %s, Email: %s", name.toString(), email.toString());
    }

    /**
     * Compares this company to another object.
     *
     * @param other the object to compare.
     * @return true if the object is an instance of {@code Company} and has the same email and name, false otherwise.
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
     * @return the hash code of the email and name combined.
     */
    @Override
    public int hashCode() {
        return email.hashCode() + name.hashCode();
    }
}
