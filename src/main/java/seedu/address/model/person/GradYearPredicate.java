package seedu.address.model.person;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;


/**
 * Tests that a {@code Person}'s {@code GradYear} is before the graduation year given.
 */
public class GradYearPredicate implements Predicate<Person> {
    private final GradYear graduationYear;


    /**
     * Constructs a GradDatePredicate with a specified graduation year.
     *
     * @param graduationYear The graduation year to match against Person objects.
     */
    public GradYearPredicate(GradYear graduationYear) {
        this.graduationYear = graduationYear;
    }

    /**
     * Evaluates this predicate on the given Person instance.
     * Tests that the Person's graduation year is before the predicate's graduation year.
     *
     * @param person The Person to be tested against this predicate.
     * @return true if the Person's graduation year is before the predicate's graduation year, otherwise false.
     */
    @Override
    public boolean test(Person person) {
        Optional<GradYear> otherGradYear = person.getGradYear();
        if (otherGradYear.isPresent()) {
            return Integer.parseInt(otherGradYear.get().toString()) < Integer.parseInt(this.graduationYear.toString());
        }

        return false;
    }

    /**
     * Compares this predicate with another object for equality.
     * Two GradYearPredicate objects are considered equal if their graduation years are equal.
     *
     * @param other The object to compare with this predicate.
     * @return true if the other object is an instance of GradYearPredicate and their grad years are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof GradYearPredicate)) {
            return false;
        }

        GradYearPredicate otherGradYearPredicate = (GradYearPredicate) other;
        return graduationYear.equals(otherGradYearPredicate.graduationYear);
    }

    /**
     * Returns a hash code value for the graduation year.
     * Ensures consistent behavior with equals when storing in hash-based collections.
     *
     * @return the hash code value of the graduation year.
     */
    @Override
    public int hashCode() {
        return graduationYear.hashCode();
    }

    /**
     * Provides a string representation of this GradDatePredicate.
     * Includes the graduation year in the string output to aid in debugging and logging.
     *
     * @return a string representation of this predicate, including the graduation year.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("graduationYear", graduationYear).toString();
    }
}
