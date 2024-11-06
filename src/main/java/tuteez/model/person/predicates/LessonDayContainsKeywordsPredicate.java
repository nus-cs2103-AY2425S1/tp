package tuteez.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.model.person.Person;
import tuteez.model.person.lesson.Day;
import tuteez.model.person.lesson.Lesson;

/**
 * Tests that a {@code Person}'s {@code Lesson} day matches any of the keywords given.
 */
public class LessonDayContainsKeywordsPredicate implements Predicate<Person> {
    private static Logger logger = LogsCenter.getLogger(LessonDayContainsKeywordsPredicate.class);
    private final List<String> keywords;

    public LessonDayContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {

        boolean hasMatch = person.getLessons().stream()
                .anyMatch(lesson -> matchesAnyKeyword(lesson));

        if (!hasMatch) {
            logger.info("No matches for lesson day found for person: " + person.getName());
        }

        return hasMatch;
    }

    private boolean matchesAnyKeyword(Lesson lesson) {
        return keywords.stream().anyMatch(keyword -> {
            Day day = Day.convertDayToEnum(keyword);
            return lesson.getLessonDay() == day;
        });
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonDayContainsKeywordsPredicate)) {
            return false;
        }

        LessonDayContainsKeywordsPredicate otherLessonDayContainsKeywordsPredicate =
                (LessonDayContainsKeywordsPredicate) other;
        return keywords.equals(otherLessonDayContainsKeywordsPredicate.keywords);
    }
}
