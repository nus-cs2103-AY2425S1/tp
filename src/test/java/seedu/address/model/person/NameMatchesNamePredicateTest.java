package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameMatchesNamePredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first second");

        NameMatchesNamePredicate firstPredicate = new NameMatchesNamePredicate(firstPredicateKeywordList);
        NameMatchesNamePredicate secondPredicate = new NameMatchesNamePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameMatchesNamePredicate firstPredicateCopy = new NameMatchesNamePredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameMatchesName_returnsTrue() {
        // Name with one word, partial name keyword
        NameMatchesNamePredicate predicate = new NameMatchesNamePredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new NameMatchesNamePredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob Alice").build()));

        // Name with multiple words, exact name keyword
        predicate = new NameMatchesNamePredicate(Arrays.asList("Alice Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new NameMatchesNamePredicate(Arrays.asList("Alice Bob Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob Carol").build()));

        // Mixed-case keywords
        predicate = new NameMatchesNamePredicate(Arrays.asList("aLIce bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotMatchKeywords_returnsFalse() {
        // Zero keywords
        NameMatchesNamePredicate predicate = new NameMatchesNamePredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameMatchesNamePredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Non-exact name keyword
        predicate = new NameMatchesNamePredicate(Arrays.asList("Alice Bob"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob Carol").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameMatchesNamePredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("89321901")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameMatchesNamePredicate predicate = new NameMatchesNamePredicate(keywords);

        String expected = NameMatchesNamePredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
