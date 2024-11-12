package seedu.address.model.person.predicates;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} starts with the specified keyword.
 * The comparison is case-insensitive.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {

    /** The keyword to match against the person's name. */
    private final String keywords;

    /**
     * Constructs a {@code NameContainsKeywordsPredicate} with the specified keyword.
     *
     * @param keywords The keyword to match against a {@code Person}'s name.
     */
    public NameContainsKeywordsPredicate(String keywords) {
        this.keywords = keywords.toLowerCase();
    }

    /**
     * Evaluates this predicate on the given {@code Person}.
     *
     * @param person The person whose name is to be tested.
     * @return {@code true} if the person's name starts with the keyword; {@code false} otherwise.
     */
    @Override
    public boolean test(Person person) {
        String fullName = person.getName().fullName.toLowerCase();
        String[] nameKeywords = this.keywords.split("\\s+");
        List<String> listOfKeywords = Arrays.asList(nameKeywords);
        Stream<String> streamOfKeywords = listOfKeywords.stream();
        String[] fullNameSplit = fullName.split("\\s+");
        List<String> listOfFullName = Arrays.asList(fullNameSplit);
        Stream<String> streamOfFullName = listOfFullName.stream();
        boolean result = fullName.startsWith(this.keywords)
                || streamOfKeywords
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(fullName, keyword))
                || streamOfFullName
                        .anyMatch(keyword -> keyword.startsWith(this.keywords));
        return result;
    }

    /**
     * Checks if this predicate is equal to another object.
     *
     * @param other The other object to compare against.
     * @return {@code true} if the other object is the same instance or has the same keyword; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    /**
     * Returns a string representation of thae {@code NameContainsKeywordsPredicate}.
     *
     * @return A string that includes the class name and the keyword.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
