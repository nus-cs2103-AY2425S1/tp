package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code JobCode} matches keywords given.
 */
public class JobCodePredicate implements Predicate<Person> {
    private final String jobCode;

    public JobCodePredicate(String jobCode) {
        this.jobCode = jobCode;
    }

    @Override
    public boolean test(Person person) {
        return person.getJobCode().value.toUpperCase().contains(jobCode);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JobCodePredicate)) {
            return false;
        }

        JobCodePredicate otherPredicate = (JobCodePredicate) other;
        return jobCode.contains(otherPredicate.jobCode);
    }
}
