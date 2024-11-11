package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class CombinedContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<Predicate<Person>> firstPredicateList = Collections.singletonList(
                new NameContainsKeywordsPredicate(Arrays.asList("first")));
        List<Predicate<Person>> secondPredicateList = Arrays.asList(
                new NameContainsKeywordsPredicate(Arrays.asList("first")),
                new PhoneContainsKeywordsPredicate(Arrays.asList("63251562")));

        CombinedContainsKeywordsPredicate firstPredicate =
                new CombinedContainsKeywordsPredicate(firstPredicateList);
        CombinedContainsKeywordsPredicate secondPredicate =
                new CombinedContainsKeywordsPredicate(secondPredicateList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CombinedContainsKeywordsPredicate firstPredicateCopy =
                new CombinedContainsKeywordsPredicate(firstPredicateList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicates -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_combinedPredicateReturnsTrue() {
        // Single matching predicate
        Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        Predicate<Person> phonePredicate = new PhoneContainsKeywordsPredicate(Arrays.asList("87223543"));
        CombinedContainsKeywordsPredicate combinedPredicate = new CombinedContainsKeywordsPredicate(
                Arrays.asList(namePredicate, phonePredicate));

        // Both must match
        Person person = new PersonBuilder().withName("Alice").withPhone("87223543").build();
        assertTrue(combinedPredicate.test(person));
    }

    @Test
    public void test_combinedPredicateReturnsFalse() {
        // Single matching predicate, but not all
        Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        Predicate<Person> phonePredicate = new PhoneContainsKeywordsPredicate(Arrays.asList("84563215"));
        CombinedContainsKeywordsPredicate combinedPredicate = new CombinedContainsKeywordsPredicate(
                Arrays.asList(namePredicate, phonePredicate));

        Person person = new PersonBuilder().withName("Alice").withPhone("94651326").build();
        assertFalse(combinedPredicate.test(person));

        // No predicates matching
        Predicate<Person> emailPredicate = new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com"));
        combinedPredicate = new CombinedContainsKeywordsPredicate(
                Arrays.asList(namePredicate, emailPredicate));

        person = new PersonBuilder().withName("Bob").withEmail("bob@example.com").build();
        assertFalse(combinedPredicate.test(person));
    }

    @Test
    public void toStringMethod() {
        List<Predicate<Person>> predicates = List.of(
                new NameContainsKeywordsPredicate(List.of("keyword1")),
                new PhoneContainsKeywordsPredicate(List.of("96541224")),
                new TagContainsKeywordsPredicate(List.of("friend")),
                new EmailContainsKeywordsPredicate(List.of("keyword1@example.com"))
        );
        CombinedContainsKeywordsPredicate combinedPredicate = new CombinedContainsKeywordsPredicate(predicates);

        String expected = CombinedContainsKeywordsPredicate.class.getCanonicalName() + predicates.toString();
        assertEquals(expected, combinedPredicate.toString());
    }

}
