package seedu.address.model.person;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.isEmpty()) {
            return false;
        } else if (keywords.get(keywords.size() - 1).contains("/")) {
            return isExact(person);
        } else {
            return keywords.stream()
                    .allMatch(keyword ->
                            Arrays.stream(person.getName().fullName.split("\\s+"))
                                    .anyMatch(part -> part.toLowerCase().startsWith(keyword.toLowerCase())));
        }
    }

    public boolean isExact(Person person) {
        String fullname = String.join("", keywords).toLowerCase().split("/")[0].trim();
        String personName = person.getName().fullName.trim().toLowerCase().replace(" ", "");
        return fullname.equals(personName);
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
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
