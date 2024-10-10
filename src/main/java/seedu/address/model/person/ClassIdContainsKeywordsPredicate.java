package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code ClassId} matches any of the keywords given.
 */
public class ClassIdContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public ClassIdContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> regexMatch(person.getClassId().value , keyword));
    }

    private boolean regexMatch(String name, String keyword) {
        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(name).find();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClassIdContainsKeywordsPredicate)) {
            return false;
        }

        ClassIdContainsKeywordsPredicate otherClassIdContainsKeywordsPredicate = (ClassIdContainsKeywordsPredicate)
                other;
        return keywords.equals(otherClassIdContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
