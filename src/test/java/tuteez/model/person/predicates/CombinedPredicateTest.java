package tuteez.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import tuteez.model.person.Person;
import tuteez.testutil.PersonBuilder;

public class CombinedPredicateTest {

    private static final Person personWithNameAndLesson =
            new PersonBuilder().withName("Alice").withLessons("monday 1730-1830").build();

    @Test
    public void test_noPredicates_returnsFalse() {
        CombinedPredicate predicate = new CombinedPredicate(List.of());
        assertFalse(predicate.test(personWithNameAndLesson));
    }

    @Test
    public void test_singlePredicateMatches_returnsTrue() {
        Predicate<Person> namePredicate = person -> person.getName().toString().equals("Alice");
        CombinedPredicate predicate = new CombinedPredicate(List.of(namePredicate));
        assertTrue(predicate.test(personWithNameAndLesson));
    }

    @Test
    public void test_singlePredicateDoesNotMatch_returnsFalse() {
        Predicate<Person> namePredicate = person -> person.getName().toString().equals("Bob");
        CombinedPredicate predicate = new CombinedPredicate(List.of(namePredicate));
        assertFalse(predicate.test(personWithNameAndLesson));
    }

    @Test
    public void test_multiplePredicatesOneMatches_returnsTrue() {
        Predicate<Person> nameAlicePredicate = person -> person.getName().toString().equals("Alice");
        Predicate<Person> nameBobPredicate = person -> person.getName().toString().equals("Bob");
        CombinedPredicate predicate = new CombinedPredicate(List.of(nameAlicePredicate, nameBobPredicate));
        assertTrue(predicate.test(personWithNameAndLesson));
    }

    @Test
    public void test_multiplePredicatesNoneMatch_returnsFalse() {
        Predicate<Person> nameBobPredicate = person -> person.getName().toString().equals("Bob");
        Predicate<Person> nameCharliePredicate = person -> person.getName().toString().equals("Charlie");
        CombinedPredicate predicate = new CombinedPredicate(List.of(nameBobPredicate, nameCharliePredicate));
        assertFalse(predicate.test(personWithNameAndLesson));
    }

    @Test
    public void test_multiplePredicatesAllMatch_returnsTrue() {
        Predicate<Person> nameAlicePredicate = person -> person.getName().toString().equals("Alice");
        Predicate<Person> hasLessonPredicate = person -> !person.getLessons().isEmpty();
        CombinedPredicate predicate = new CombinedPredicate(List.of(nameAlicePredicate, hasLessonPredicate));
        assertTrue(predicate.test(personWithNameAndLesson));
    }

    @Test
    public void equals_differentPredicates_returnsFalse() {
        Predicate<Person> predicate1 = person -> person.getName().toString().equals("Alice");
        Predicate<Person> predicate2 = person -> person.getName().toString().equals("Bob");

        CombinedPredicate combinedPredicate1 = new CombinedPredicate(List.of(predicate1));
        CombinedPredicate combinedPredicate2 = new CombinedPredicate(List.of(predicate2));

        assertFalse(combinedPredicate1.equals(combinedPredicate2));
    }

    @Test
    public void equals_null_returnsFalse() {
        Predicate<Person> predicate = person -> person.getName().toString().equals("Alice");
        CombinedPredicate combinedPredicate = new CombinedPredicate(List.of(predicate));
        assertFalse(combinedPredicate.equals(null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Predicate<Person> predicate = person -> person.getName().toString().equals("Alice");
        CombinedPredicate combinedPredicate = new CombinedPredicate(List.of(predicate));
        assertTrue(combinedPredicate.equals(combinedPredicate));
    }
}
