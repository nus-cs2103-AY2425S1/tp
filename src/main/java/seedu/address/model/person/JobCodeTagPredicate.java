package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code JobCode} matches any of the keywords given.
 */
public class JobCodeTagPredicate implements Predicate<Person> {
    private final String jobCode;
    private final String tag;

    public JobCodeTagPredicate(String jobCode, String tag) {
        this.jobCode = jobCode;
        this.tag = tag;
    }

    @Override
    public boolean test(Person person) {
        // Compare job code exactly (case-sensitive)
        return person.getJobCode().value.equals(jobCode) && person.getTag().tagCode.equals(tag);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobCodeTagPredicate // instanceof handles nulls
                && jobCode.equals(((JobCodeTagPredicate) other).jobCode)
                && tag.equals(((JobCodeTagPredicate) other).tag)); // state check
    }
}
