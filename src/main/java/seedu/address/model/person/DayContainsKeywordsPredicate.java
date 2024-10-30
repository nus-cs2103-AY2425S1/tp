package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class DayContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public DayContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (!(person instanceof Student)) {
            return false;
        }
        Student student = (Student) person;
        String dayString = student.getLessonTime().day.name().toLowerCase();
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(dayString, keyword));
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

        DayContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (DayContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
