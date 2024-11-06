package tuteez.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.model.person.Person;
import tuteez.model.person.lesson.Day;
import tuteez.model.person.lesson.Lesson;

/**
 * Tests that a {@code Person}'s {@code Lesson} matches any of the keywords given.
 */
public class LessonContainsKeywordsPredicate implements Predicate<Person> {
    private static Logger logger = LogsCenter.getLogger(LessonContainsKeywordsPredicate.class);
    private final List<String> keywords;

    public LessonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {

        boolean hasMatch = person.getLessons().stream()
                .anyMatch(lesson -> matchesAnyKeyword(lesson));

        if (!hasMatch) {
            logger.info("No matches found for person: " + person.getName());
        }

        return hasMatch;
    }

    private boolean matchesAnyKeyword(Lesson lesson) {
        return keywords.stream().anyMatch(keyword -> (
                Day.isValidDay(keyword) && lesson.getLessonDay().toString().equalsIgnoreCase(keyword))
                || (Lesson.isValidTimeRange(keyword) && checkOverlappingTimeRange(lesson, keyword)));
    }

    private boolean checkOverlappingTimeRange(Lesson lesson, String keyword) {
        return lesson.checkOverlappingTimeRange(keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonContainsKeywordsPredicate)) {
            return false;
        }

        LessonContainsKeywordsPredicate otherLessonContainsKeywordsPredicate = (LessonContainsKeywordsPredicate) other;
        return keywords.equals(otherLessonContainsKeywordsPredicate.keywords);
    }
}
