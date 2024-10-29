package tuteez.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.commons.util.ToStringBuilder;
import tuteez.model.person.Person;
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
                .map(Lesson::getDayAndTime)
                .anyMatch(dayAndTime -> {
                    String[] parts = dayAndTime.split(" ");
                    String day = parts[0];
                    String time = parts[1];

                    boolean dayMatches = keywords.stream().anyMatch(keyword -> keyword.equalsIgnoreCase(day));
                    boolean timeMatches = keywords.stream().anyMatch(keyword -> keyword.equals(time));

                    if (dayMatches) {
                        logger.info("Match found for day: " + day);
                    }
                    if (timeMatches) {
                        logger.info("Match found for time: " + time);
                    }
                    return dayMatches || timeMatches;
                });

        if (!hasMatch) {
            logger.info("No matches found for person:" + person.getName());
        }

        return hasMatch;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
