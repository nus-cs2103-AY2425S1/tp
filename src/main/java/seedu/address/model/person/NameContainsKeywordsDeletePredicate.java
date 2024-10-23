package seedu.address.model.person;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s name matches any of the specified keywords.
 */
public class NameContainsKeywordsDeletePredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a {@code NameContainsKeywordsDeletePredicate} with the specified keywords.
     *
     * @param keywords A list of keywords to match against the {@code Person}'s name.
     */
    public NameContainsKeywordsDeletePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param person The person to test.
     * @return {@code true} if the person's name matches the predicate's criteria, otherwise {@code false}.
     *         Returns {@code true} if all keywords are found in the person's name, or if the last keyword
     *         ends with a "/", indicating an exact match.
     */
    @Override
    public boolean test(Person person) {
        if (keywords.isEmpty()) {
            return false;
        } else if (keywords.get(keywords.size() - 1).contains("/")) {
            return isExact(person);
        } else {
            return keywords.stream()
                    .allMatch(keyword ->
                            Arrays.stream(person.getName().fullName.split("\\s+"))
                                    .anyMatch(part -> part.toLowerCase().startsWith(keyword.toLowerCase())));
        }
    }

    /**
     * Checks if the person's name is an exact match with the concatenated keywords.
     *
     * @param person The person to compare against the concatenated keywords.
     * @return {@code true} if the concatenated keywords, without the trailing "/", match the person's full name.
     */
    public boolean isExact(Person person) {
        String fullname = String.join("", keywords).toLowerCase().split("/")[0].trim();
        String personName = person.getName().fullName.trim().toLowerCase().replace(" ", "");
        return fullname.equals(personName);
    }

    /**
     * Checks if this predicate is equal to another object.
     *
     * @param other The other object to compare.
     * @return {@code true} if the other object is an instance of {@code NameContainsKeywordsDeletePredicate}
     *         and has the same keywords, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsDeletePredicate)) {
            return false;
        }

        NameContainsKeywordsDeletePredicate otherNameContainsKeywordsDeletePredicate =
                (NameContainsKeywordsDeletePredicate) other;
        return keywords.equals(otherNameContainsKeywordsDeletePredicate.keywords);
    }

    /**
     * Returns a string representation of this predicate.
     *
     * @return A string that includes the list of keywords.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
