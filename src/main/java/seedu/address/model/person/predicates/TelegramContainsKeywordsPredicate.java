package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Telegram} matches any of the keywords given.
 */
public class TelegramContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructor for TelegramContainsKeywordsPredicate. Filters empty and trims input
     * @param keywords List of keywords to search for in the telegram field.
     */
    public TelegramContainsKeywordsPredicate(List<String> keywords) {
        this.keywords =
                keywords.stream()
                        .filter(keyword -> !keyword.isBlank())
                        .map(keyword -> keyword.trim())
                        .collect(Collectors.toList());
    }

    @Override
    public boolean test(Person person) {
        if (!person.hasTelegramUsername()) {
            return false;
        }
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTelegramUsername().toString(), keyword)
                                    || person.getTelegramUsername().toString()
                        .toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TelegramContainsKeywordsPredicate)) {
            return false;
        }

        TelegramContainsKeywordsPredicate otherTelegramContainsKeywordsPredicate =
                (TelegramContainsKeywordsPredicate) other;
        return keywords.equals(otherTelegramContainsKeywordsPredicate.keywords);
    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }
}
