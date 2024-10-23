package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Telegram} matches any of the keywords given.
 */
public class TelegramContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TelegramContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getTelegram().equals(new Telegram(keyword)));
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
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

