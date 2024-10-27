package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code MonthPaid} matches any of the keywords given.
 */
public class MonthPaidContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public MonthPaidContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> regexMatch(person.getMonthsPaid().toString(), keyword));
    }

    private boolean regexMatch(String monthPaid, String keyword) {
        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(monthPaid).find();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MonthPaidContainsKeywordsPredicate)) {
            return false;
        }

        MonthPaidContainsKeywordsPredicate otherPredicate = (MonthPaidContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}
