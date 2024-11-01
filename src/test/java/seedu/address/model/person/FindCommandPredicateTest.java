package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;


/**
 * Contains equality test for FindCommandPredicate
 */
class FindCommandPredicateTest {

    @Test
    void equals() {
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(
                Arrays.asList("Alex"));
        FindCommandPredicate firstPredicate = new FindCommandPredicate();
        FindCommandPredicate secondPredicate = new FindCommandPredicate();
        firstPredicate.addNameContainsKeywordsPredicate(nameContainsKeywordsPredicate);
        secondPredicate.addNameContainsKeywordsPredicate(nameContainsKeywordsPredicate);
        //Check if Predicate's "equals" is working instead of checking if it's the same object
        assertTrue(firstPredicate.equals(secondPredicate));
    }
}
