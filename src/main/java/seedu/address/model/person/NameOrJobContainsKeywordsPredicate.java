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
                    String personName = person.getName().fullName.toLowerCase().toString();
                    ArrayList<String> nameWords = getWords(personName, keyword, person);
                    return nameWords.contains(keyword.toLowerCase());
                });
        boolean matchesJob = jobKeywords.stream()
                .anyMatch(keyword -> {
                    String jobName = person.getJob().value.toLowerCase().toString();
                    ArrayList<String> jobWords = getWords(jobName, keyword, person);
                    return jobWords.contains(keyword.toLowerCase());
                });
        return matchesName || matchesJob;
    }

    private ArrayList<String> getWords(String str, String keyword, Person person) {
        ArrayList<String> words = new ArrayList<>();
        words.add(str);
        String[] splitStr = str.split("\\s+");
        for (String s : splitStr) {
            words.add(s);
        }
        return words;
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
