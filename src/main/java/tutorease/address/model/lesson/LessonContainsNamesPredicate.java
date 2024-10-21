package tutorease.address.model.lesson;

import java.util.List;
import java.util.function.Predicate;

import tutorease.address.commons.util.StringUtil;
import tutorease.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Lesson}'s student's {@code Name} matches any of the keywords given.
 */
public class LessonContainsNamesPredicate implements Predicate<Lesson> {
    private final List<String> keywords;

    public LessonContainsNamesPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Lesson lesson) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(lesson.getStudentName(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonContainsNamesPredicate)) {
            return false;
        }

        LessonContainsNamesPredicate otherLessonContainsNamesPredicate =
                (LessonContainsNamesPredicate) other;
        return keywords.equals(otherLessonContainsNamesPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords in lessons", keywords).toString();
    }
}
