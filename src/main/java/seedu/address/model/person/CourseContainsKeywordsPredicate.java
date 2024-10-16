package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Course} matches any of the keywords given.
 */
public class CourseContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Creates a CourseContainsKeywordsPredicate with the specified keywords.
     *
     * @param keywords A list of keywords to filter by.
     */
    public CourseContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        String course = person.getCourse().toString().toLowerCase();
        return keywords.stream().anyMatch(keyword -> {
            String keywordLower = keyword.toLowerCase();
            if (course.equals(keywordLower)) {
                return true;
            }
            // for partial match
            return course.startsWith(keywordLower);
        });
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof CourseContainsKeywordsPredicate)) {
            return false;
        }

        CourseContainsKeywordsPredicate otherCourseContainsKeywordsPredicate = (CourseContainsKeywordsPredicate) other;
        return keywords.equals(otherCourseContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }

}
