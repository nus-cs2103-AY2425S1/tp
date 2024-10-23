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
        // Compare job code exactly (case-sensitive)
        return person.getJobCode().value.equals(jobCode);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobCodePredicate // instanceof handles nulls
                && jobCode.equals(((JobCodePredicate) other).jobCode)); // state check
    }
}
