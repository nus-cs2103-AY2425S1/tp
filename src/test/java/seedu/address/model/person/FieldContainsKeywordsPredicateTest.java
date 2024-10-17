package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.testutil.PersonBuilder;

public class FieldContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        ArgumentMultimap firstArgMultimap = new ArgumentMultimap();
        firstArgMultimap.put(PREFIX_NAME, "first");
        ArgumentMultimap secondArgMultimap = new ArgumentMultimap();
        secondArgMultimap.put(PREFIX_NAME, "first");
        secondArgMultimap.put(PREFIX_NAME, "second");

        FieldContainsKeywordsPredicate firstPredicate = new FieldContainsKeywordsPredicate(firstArgMultimap);
        FieldContainsKeywordsPredicate secondPredicate = new FieldContainsKeywordsPredicate(secondArgMultimap);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy = new FieldContainsKeywordsPredicate(firstArgMultimap);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_NAME, "Alice");
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_NAME, "Alice");
        argumentMultimap.put(PREFIX_NAME, "Bob");
        predicate = new FieldContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_NAME, "Bob");
        argumentMultimap.put(PREFIX_NAME, "Carol");
        predicate = new FieldContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_NAME, "aLIce");
        argumentMultimap.put(PREFIX_NAME, "bOB");
        predicate = new FieldContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_NAME, "Carol");
        predicate = new FieldContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        //// Keywords match phone, email and address, but does not match name
        //predicate = new FieldContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        //assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
        //        .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_TAG, "friends");
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
        // Multiple keywords
        argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_TAG, "friends");
        argumentMultimap.put(PREFIX_TAG, "owesMoney");
        predicate = new FieldContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "owesMoney").build()));
        // Only one matching keyword
        argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_TAG, "friends");
        argumentMultimap.put(PREFIX_TAG, "owesMoney");
        predicate = new FieldContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "family").build()));
        // Mixed-case keywords
        argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_TAG, "fRiEnDs");
        argumentMultimap.put(PREFIX_TAG, "oWeSmOnEy");
        predicate = new FieldContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "owesMoney").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));
        // Non-matching keyword
        argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_TAG, "family");
        predicate = new FieldContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(new Prefix(""), "");
        argumentMultimap.put(PREFIX_NAME, "keyword1");
        argumentMultimap.put(PREFIX_NAME, "keyword2");
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(argumentMultimap);

        String expected = "{=[], n/=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
