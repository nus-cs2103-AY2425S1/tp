package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameMatchesNamePredicate implements Predicate<Person> {
    private final List<String> keyword;

    public NameMatchesNamePredicate(List<String> name) {
        this.keyword = name;
    }

    @Override
    public boolean test(Person person) {
        return keyword.stream()
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
        return keyword.equals(otherNameMatchesNamePredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keyword).toString();
    }
}
