package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 * Different from NameContainsKeywordsPredicate as it requires all keywords to be present in the name.
 */
public class NameMatchesNamePredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameMatchesNamePredicate(List<String> names) {
        this.keywords = names;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(name -> {
                    if (name.contains(" ")) {
                        return person.getName().fullName.equalsIgnoreCase(name);
                    } else {
                        return StringUtil.containsWordIgnoreCase(person.getName().fullName, name);
                    }
                });
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameMatchesNamePredicate)) {
            return false;
        }

        NameMatchesNamePredicate otherNameMatchesNamePredicate = (NameMatchesNamePredicate) other;
        return keywords.equals(otherNameMatchesNamePredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
