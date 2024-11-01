package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

public class PhoneNumberContainsKeywordPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PhoneNumberContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)
                                    ||person.getPhone().value.toLowerCase().contains(keyword.toLowerCase()));
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

        PhoneNumberContainsKeywordPredicate otherPhoneNumberContainsKeywordPredicate = (PhoneNumberContainsKeywordPredicate) other;
        return keywords.equals(otherPhoneNumberContainsKeywordPredicate.keywords);
    }
}
