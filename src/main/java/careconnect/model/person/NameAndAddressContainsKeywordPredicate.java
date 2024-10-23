package careconnect.model.person;

import java.util.List;
import java.util.function.Predicate;

import careconnect.commons.util.StringUtil;
import careconnect.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameAndAddressContainsKeywordPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> addressKeywords;

    /**
     * Constructor for predicate taking in a list of name keywords and a list of address keywords for matching
     * @param nameKeywords
     * @param addressKeywords
     */
    public NameAndAddressContainsKeywordPredicate(List<String> nameKeywords, List<String> addressKeywords) {
        this.nameKeywords = nameKeywords;
        this.addressKeywords = addressKeywords;
    }

    /**
     * condition that seach keyword is tested against
     * @param person the input argument
     * @return boolean of whether predicat test passed
     */
    @Override
    public boolean test(Person person) {
        if (nameKeywords.isEmpty()) {
            return addressKeywords.stream()
                    .anyMatch(
                            keyword -> StringUtil.containsPartialWordIgnoreCase(person.getAddress().toString(), keyword)
                    );
        }
        if (addressKeywords.isEmpty()) {
            return nameKeywords.stream()
                    .anyMatch(
                            keyword -> StringUtil.containsPartialWordIgnoreCase(person.getName().fullName, keyword)
                    );
        }
        return (nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(person.getName().fullName, keyword))
                && addressKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(person.getAddress().toString(), keyword))
            );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameAndAddressContainsKeywordPredicate)) {
            return false;
        }

        NameAndAddressContainsKeywordPredicate otherNameContainsKeywordsPredicate =
                (NameAndAddressContainsKeywordPredicate) other;
        return nameKeywords.equals(otherNameContainsKeywordsPredicate.nameKeywords)
                && addressKeywords.equals(otherNameContainsKeywordsPredicate.addressKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("addressKeywords", addressKeywords).toString();
    }
}
