package tuteez.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.commons.util.StringUtil;
import tuteez.commons.util.ToStringBuilder;
import tuteez.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Telegram} matches any of the keywords given.
 */
public class TelegramUsernameContainsKeywordsPredicate implements Predicate<Person> {
    private static final Logger logger = LogsCenter.getLogger(TelegramUsernameContainsKeywordsPredicate.class);
    private final List<String> keywords;

    public TelegramUsernameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        logger.info("Checking: " + person.getTelegramUsername());
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTelegramUsername().toString(),
                        keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TelegramUsernameContainsKeywordsPredicate)) {
            return false;
        }

        TelegramUsernameContainsKeywordsPredicate otherTelegramUsernameContainsKeywordsPredicate =
                (TelegramUsernameContainsKeywordsPredicate) other;
        return keywords.equals(otherTelegramUsernameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
