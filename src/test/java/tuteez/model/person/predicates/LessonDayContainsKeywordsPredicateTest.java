package tuteez.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import tuteez.model.person.Person;
import tuteez.testutil.PersonBuilder;

public class LessonDayContainsKeywordsPredicateTest {
    private static final Person personWithLesson =
            new PersonBuilder().withName("bob").withLessons("monday 1730-1830").build();
    @Test
    public void test_lessonMatchesDayKeyword_returnsTrue() {
        List<String> keywords = List.of("monday");
        LessonDayContainsKeywordsPredicate predicate = new LessonDayContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(personWithLesson));
    }

    @Test
    public void test_lessonMatchesDayKeywordAbbreviation_returnsTrue() {
        List<String> keywords = List.of("mon");
        LessonDayContainsKeywordsPredicate predicate = new LessonDayContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(personWithLesson));
    }

    @Test
    public void test_lessonDoesNotMatchAnyKeyword_returnsFalse() {
        List<String> keywords = List.of("tuesday", "wed", "wednesday");
        LessonDayContainsKeywordsPredicate predicate = new LessonDayContainsKeywordsPredicate(keywords);
        assertFalse(predicate.test(personWithLesson));
    }

    @Test
    public void equals_sameKeywords_returnsTrue() {
        List<String> keywords1 = List.of("friday");
        List<String> keywords2 = List.of("friday");

        LessonDayContainsKeywordsPredicate predicate1 = new LessonDayContainsKeywordsPredicate(keywords1);
        LessonDayContainsKeywordsPredicate predicate2 = new LessonDayContainsKeywordsPredicate(keywords2);

        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        List<String> keywords1 = List.of("tuesday");
        List<String> keywords2 = List.of("thursday");

        LessonDayContainsKeywordsPredicate predicate1 = new LessonDayContainsKeywordsPredicate(keywords1);
        LessonDayContainsKeywordsPredicate predicate2 = new LessonDayContainsKeywordsPredicate(keywords2);

        assertFalse(predicate1.equals(predicate2));
    }
}
