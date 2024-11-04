package seedu.address.model.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;
public class EmailContainsKeywordsPredicateTest {

    @Test
    public void test_diffEmail() {
        List<String> keywords = Collections.singletonList("emailer@gmail.com");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);
        assertFalse(predicate.test(new PersonBuilder().withEmail("emailer2@gmail.com").build()));
    }

    @Test
    public void test_partial_returnTrue() {
        List<String> keywords = Collections.singletonList("test@gmai");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(new PersonBuilder().withEmail("test@gmail.com").build()));
    }

    @Test
    public void equals_sameObject_returnTrue() {
        List<String> keywords = Collections.singletonList("test@gmail.com");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValues_returnTrue() {
        List<String> keywords = Collections.singletonList("test@gmail.com");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);
        EmailContainsKeywordsPredicate predicateCopy = new EmailContainsKeywordsPredicate(keywords);
        assertTrue(predicate.equals(predicateCopy));
    }
}

