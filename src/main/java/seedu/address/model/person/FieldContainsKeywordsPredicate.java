package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;

/**
 * Tests that a {@code Person}'s fields matches any of the keywords given.
 */
public class FieldContainsKeywordsPredicate implements Predicate<Person> {
    private final ArgumentMultimap argMultimap;

    public FieldContainsKeywordsPredicate(ArgumentMultimap argMultimap) {
        this.argMultimap = argMultimap;
    }

    /**
     * Tests that a {@code Person}'s fields matches any of the keywords given.
     */
    @Override
    public boolean test(Person person) {
        Set<Prefix> prefixes = argMultimap.getPrefixes();
        Boolean result = false;
        for (Prefix prefix : prefixes) {
            List<String> keywords = argMultimap.getAllValues(prefix);
            switch (prefix.getPrefix()) {

            case "n/":
                result = keywords.stream()
                        .anyMatch(keyword -> person.nameContainsKeyword(keyword));
                break;

            case "t/":
                result = keywords.stream()
                        .anyMatch(keyword -> person.hasTag(keyword));
                break;
            }
            //return keywords.stream()
            //        .anyMatch(keyword -> person.getName().fullName.toLowerCase()
            //                .contains(keyword.toLowerCase()));
            if (result) {
                break;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FieldContainsKeywordsPredicate)) {
            return false;
        }

        FieldContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (FieldContainsKeywordsPredicate) other;
        return argMultimap.equals(otherNameContainsKeywordsPredicate.argMultimap);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", argMultimap).toString();
    }
}
