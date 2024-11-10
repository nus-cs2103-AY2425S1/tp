package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneNumberContainsKeywordPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructor for PhoneNumberContainsKeywordPredicate. Filters empty and trims input
     * @param keywords List of keywords to search for in the phone number field.
     */
    public PhoneNumberContainsKeywordPredicate(List<String> keywords) {

        this.keywords =
                keywords.stream()
                        .filter(keyword -> !keyword.isBlank())
                        .map(keyword -> keyword.trim())
                        .collect(Collectors.toList());
    }

    @Override
    public boolean test(Person person) {
        if (person.getPhone() == null) {
            return false;
        }
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword))
            || keywords.stream().allMatch(keyword -> person.getPhone().value.toLowerCase()
                .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneNumberContainsKeywordPredicate)) {
            return false;
        }

        PhoneNumberContainsKeywordPredicate otherPhoneNumberContainsKeywordPredicate =
                (PhoneNumberContainsKeywordPredicate) other;
        return keywords.equals(otherPhoneNumberContainsKeywordPredicate.keywords);
    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }
}
