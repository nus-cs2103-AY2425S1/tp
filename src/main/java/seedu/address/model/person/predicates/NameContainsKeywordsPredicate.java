package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructor for NameContainsKeywordsPredicate. Filters empty and trims input
     * @param keywords List of keywords to search for in the name field.
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords =
                keywords.stream()
                        .filter(keyword -> !keyword.isBlank())
                        .map(keyword -> keyword.trim())
                        .collect(Collectors.toList());

    }

    @Override
    public boolean test(Person person) {

        if (keywords.isEmpty()) {
            return false;
        }
        if (person.getName() == null) {
            return false;
        }
        List<String> filteredKeywords = keywords.stream()
                .filter(keyword -> !keyword.isBlank())
                .collect(Collectors.toList());
        return filteredKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                || filteredKeywords.stream().allMatch(keyword -> person.getName().fullName.toLowerCase()
                .contains(keyword.toLowerCase()));
    }

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

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
