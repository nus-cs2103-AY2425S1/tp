package seedu.address.model.person;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name}, {@code Phone}, {@code Email}, {@code Address}, {@code Birthday},
 * or {@code Tags} match any of the criteria given in the search map.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final Map<String, Object> searchCriteria;

    public PersonContainsKeywordsPredicate(Map<String, Object> searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public boolean test(Person person) {
        boolean matches = true;
        matches &= !searchCriteria.isEmpty();

        if (searchCriteria.containsKey("name")) {
            matches &= StringUtil.containsIgnoreCase(person.getName().fullName, (String) searchCriteria.get("name"));
        }
        if (searchCriteria.containsKey("phone")) {
            matches &= StringUtil.containsIgnoreCase(person.getPhone().toString(),
                    (String) searchCriteria.get("phone"));
        }
        if (searchCriteria.containsKey("email")) {
            matches &= StringUtil.containsIgnoreCase(person.getEmail().toString(),
                    (String) searchCriteria.get("email"));
        }
        if (searchCriteria.containsKey("address")) {
            matches &= StringUtil.containsIgnoreCase(person.getAddress().toString(),
                    (String) searchCriteria.get("address"));
        }
        if (searchCriteria.containsKey("birthday")) {
            matches &= StringUtil.containsIgnoreCase(person.getBirthday().toString(),
                    (String) searchCriteria.get("birthday"));
        }
        if (searchCriteria.containsKey("tags")) {
            List<String> tags = (List<String>) searchCriteria.get("tags");
            matches &= tags.stream()
                    .allMatch(tag -> person.getTags().stream()
                            .anyMatch(personTag -> StringUtil.containsIgnoreCase(personTag.toString(), tag)));
        }

        return matches;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;
        return searchCriteria.equals(otherPredicate.searchCriteria);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("searchCriteria", searchCriteria).toString();
    }
}
