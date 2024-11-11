package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructor for AddressContainsKeywordsPredicate. Filters empty and trims input
     * @param keywords List of keywords to search for in the address field.
     */
    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords =
                keywords.stream()
                        .filter(keyword -> !keyword.isBlank())
                        .map(keyword -> keyword.trim())
                        .collect(Collectors.toList());
    }

    @Override
    public boolean test(Person person) {
        if (person.getAddress() == null) {
            return false;
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword))
                || keywords.stream().allMatch(keyword ->
                person.getAddress().value.toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressContainsKeywordsPredicate)) {
            return false;
        }

        AddressContainsKeywordsPredicate otherAddressContainsKeywordsPredicate =
                (AddressContainsKeywordsPredicate) other;
        return keywords.equals(otherAddressContainsKeywordsPredicate.keywords);
    }
}
