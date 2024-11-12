package seedu.address.model.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.TelegramUsername;
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

    @Test
    public void equals_differentType_returnFalse() {
        List<String> keywords = Collections.singletonList("test@gmail.com");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);

        assertFalse(predicate.equals(5.0f));
    }

    @Test
    public void test_nullEmail_returnsFalse() {
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(
                Collections.singletonList("Jurong"));

        assertFalse(predicate.test(new PersonStubNullEmail()));
    }

    private class PersonStubNullEmail extends Person {
        private Email email = null;

        public PersonStubNullEmail() {
            super(new seedu.address.model.person.Name("Alice"), new seedu.address.model.person.Phone("12345678"),
                    new seedu.address.model.person.Email("test@gmail.com"),
                    new seedu.address.model.person.Address("123, Jurong West Ave 6, #08-111"),
                    new TelegramUsername("alice"),
                    new HashSet<>());
        }

        @Override
        public Email getEmail() {
            return this.email;
        }
    }
}

