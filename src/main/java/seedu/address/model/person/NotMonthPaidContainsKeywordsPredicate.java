package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code MonthPaid} does not match any of the keywords given.
 */
public class NotMonthPaidContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public NotMonthPaidContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .noneMatch(keyword -> regexMatch(person.getMonthsPaid().toString(), keyword));
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

        if (!(other instanceof NotMonthPaidContainsKeywordsPredicate)) {
            return false;
        }

        NotMonthPaidContainsKeywordsPredicate otherPredicate = (NotMonthPaidContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}
