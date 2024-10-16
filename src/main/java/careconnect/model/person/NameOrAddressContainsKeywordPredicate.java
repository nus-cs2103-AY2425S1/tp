package careconnect.model.person;

import java.util.List;
import java.util.function.Predicate;

import careconnect.commons.util.StringUtil;
import careconnect.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameOrAddressContainsKeywordPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameOrAddressContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * condition that seach keyword is tested against
     * @param person the input argument
     * @return
     */
    @Override
    public boolean test(Person person) {
        return (keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(person.getName().fullName, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(person.getAddress().toString(), keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(person.getEmail().toString(), keyword))
            );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameOrAddressContainsKeywordPredicate)) {
            return false;
        }

        NameOrAddressContainsKeywordPredicate otherNameContainsKeywordsPredicate = (NameOrAddressContainsKeywordPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
