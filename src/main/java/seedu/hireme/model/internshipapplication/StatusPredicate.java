package seedu.hireme.model.internshipapplication;

import java.util.function.Predicate;

import seedu.hireme.commons.util.ToStringBuilder;


/**
 * Tests whether a {@code InternshipApplication}'s {@code Status} has the specified statusToFilterBy.
 */
public class StatusPredicate implements Predicate<InternshipApplication> {

    private final Status statusToFilterBy;

    /**
     * Constructs a {@code StatusPredicate} with the statusToFilterBy.
     *
     * @param statusToFilterBy A status that specifies which status to filter the internship applications by.
     */
    public StatusPredicate(Status statusToFilterBy) {
        this.statusToFilterBy = statusToFilterBy;
    }

    /**
     * Tests whether the status of the {@code InternshipApplication} has the specified statusToFilterBy
     *
     * @param internshipApplication The internship application to be tested.
     * @return True if statusToFilterBy matches the internship application status, false otherwise.
     */
    @Override
    public boolean test(InternshipApplication internshipApplication) {
        return statusToFilterBy.equals(internshipApplication.getStatus());
    }

    /**
     * Compares this predicate with another object for equality.
     *
     * @param other The other object to compare with.
     * @return True if both predicates contain the same statusToFilterBy, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatusPredicate)) {
            return false;
        }

        StatusPredicate otherPredicate = (StatusPredicate) other;
        return statusToFilterBy.equals(otherPredicate.statusToFilterBy);
    }

    /**
     * Returns a string representation of this predicate.
     *
     * @return A string that contains statusToFilterBy.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("statusToFilterBy", statusToFilterBy)
                .toString();
    }
}
