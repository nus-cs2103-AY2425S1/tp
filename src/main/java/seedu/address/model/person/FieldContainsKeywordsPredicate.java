package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.storage.JsonAdaptedMeeting;

/**
 * Tests that a {@code Person}'s fields matches any of the keywords given.
 * {@code Name} is partial match.
 * {@code Tag} is lower-cased exact match.
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
        Boolean isMatch = false;
        for (Prefix prefix : prefixes) {
            List<String> keywords = argMultimap.getAllValues(prefix);
            switch (prefix.getPrefix()) {

            case "n/":
                isMatch = keywords.stream()
                        .anyMatch(keyword -> person.nameContainsKeyword(keyword));
                break;

            case "t/":
                isMatch = keywords.stream()
                        .anyMatch(keyword -> person.hasTag(keyword));
                break;

            case "u/":
                assert(keywords.stream().allMatch(keyword -> keyword
                        .matches(JsonAdaptedMeeting.UIDREGEX)));
                isMatch = keywords.stream()
                    .anyMatch(keyword -> person.isSamePersonUid(UUID.fromString(keyword)));
                break;

            default:
                break;
            }
            if (isMatch) {
                break;
            }
        }
        return isMatch;
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

        FieldContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                (FieldContainsKeywordsPredicate) other;
        return argMultimap.equals(otherNameContainsKeywordsPredicate.argMultimap);
    }

    @Override
    public String toString() {
        return argMultimap.toString();
    }
}
