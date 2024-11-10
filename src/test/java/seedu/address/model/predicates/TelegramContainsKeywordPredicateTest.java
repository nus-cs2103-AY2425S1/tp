package seedu.address.model.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TelegramUsername;
import seedu.address.model.person.predicates.TelegramContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;
public class TelegramContainsKeywordPredicateTest {
    @Test
    public void test_diffUsername() {
        List<String> keywords = Collections.singletonList("amybee");
        TelegramContainsKeywordsPredicate predicate = new TelegramContainsKeywordsPredicate(keywords);
        assertFalse(predicate.test(new PersonBuilder().withTelegramUsername("amybe").build()));
    }

    @Test
    public void test_samePartial_returnFalse() {
        List<String> keywords = Collections.singletonList("amybeee");
        TelegramContainsKeywordsPredicate predicate = new TelegramContainsKeywordsPredicate(keywords);
        assertFalse(predicate.test(new PersonBuilder().withTelegramUsername("amybee").build()));
    }

    @Test
    public void test_partialUsername_returnTrue() {
        List<String> keywords = Collections.singletonList("amybe");
        TelegramContainsKeywordsPredicate predicate = new TelegramContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(new PersonBuilder().withTelegramUsername("amybee").build()));
    }

    @Test
    public void test_multipleComplete_returnTrue() {
        List<String> keywords = Arrays.asList("amybee", "john");
        TelegramContainsKeywordsPredicate predicate = new TelegramContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(new PersonBuilder().withTelegramUsername("amybee").build()));
        assertTrue(predicate.test(new PersonBuilder().withTelegramUsername("john123").build()));
    }

    @Test
    public void equals_sameObject_returnTrue() {
        List<String> keywords = Collections.singletonList("amybee");
        TelegramContainsKeywordsPredicate predicate = new TelegramContainsKeywordsPredicate(keywords);
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValues_returnTrue() {
        List<String> keywords = Collections.singletonList("amybee");
        TelegramContainsKeywordsPredicate predicate = new TelegramContainsKeywordsPredicate(keywords);
        TelegramContainsKeywordsPredicate predicateCopy = new TelegramContainsKeywordsPredicate(keywords);
        assertTrue(predicate.equals(predicateCopy));
    }

    @Test
    public void test_contactWithNoTelegramUsername_returnsFalse() {
        List<String> keywords = Collections.singletonList("amybee");
        TelegramContainsKeywordsPredicate predicate = new TelegramContainsKeywordsPredicate(keywords);
        Person personWithNoTelegramUsername = new Person(new Name("Zeke Yaeger"), new Phone("8425131"),
                new Email("zeke@example.com"), new Address("Eldia"), new TelegramUsername(null), new HashSet<>());
        assertFalse(predicate.test(personWithNoTelegramUsername));
    }
}
