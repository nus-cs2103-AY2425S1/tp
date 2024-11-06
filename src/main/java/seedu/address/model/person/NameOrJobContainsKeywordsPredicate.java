package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} or {@code Job} matches any of the keywords given.
 */
public class NameOrJobContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> jobKeywords;

    /**
     * Constructs a NameOrJobContainsKeywordsPredicate with the given name and job keywords.
     */
    public NameOrJobContainsKeywordsPredicate(List<String> nameKeywords, List<String> jobKeywords) {
        this.nameKeywords = nameKeywords;
        this.jobKeywords = jobKeywords;
    }

    @Override
    public boolean test(Person person) {
        boolean matchesName = nameKeywords.stream()
                .anyMatch(keyword -> {
                    ArrayList<String> nameWords = new ArrayList<>();
                    String personName = person.getName().fullName.toLowerCase().toString();
                    nameWords.add(personName);
                    String[] splitPersonName = personName.split("\\s+");
                    for (String name : splitPersonName) {
                        nameWords.add(name);
                    }
                    return nameWords.contains(keyword.toLowerCase());
                });
        boolean matchesJob = jobKeywords.stream()
                .anyMatch(keyword -> {
                    ArrayList<String> jobWords = new ArrayList<>();
                    String jobName = person.getJob().value.toLowerCase().toString();
                    jobWords.add(jobName);
                    String[] splitPersonJob = jobName.split("\\s+");
                    for (String job : splitPersonJob) {
                        jobWords.add(job);
                    }
                    return jobWords.contains(keyword.toLowerCase());
                });
        return matchesName || matchesJob;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NameOrJobContainsKeywordsPredicate)) {
            return false;
        }

        NameOrJobContainsKeywordsPredicate otherPredicate = (NameOrJobContainsKeywordsPredicate) other;
        return nameKeywords.equals(otherPredicate.nameKeywords)
                && jobKeywords.equals(otherPredicate.jobKeywords);
    }

    @Override
    public String toString() {
        return "NameOrJobContainsKeywordsPredicate{"
                + "nameKeywords=" + nameKeywords
                + ", jobKeywords=" + jobKeywords
                + '}';
    }
}
