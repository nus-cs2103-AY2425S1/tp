package seedu.address.model.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TelegramUsername;
import seedu.address.model.person.predicates.PhoneNumberContainsKeywordPredicate;
import seedu.address.testutil.PersonBuilder;


public class PhoneNumberContainsKeywordPredicateTest {

    @Test
    public void test_diffNumber() {
        List<String> keywords = Collections.singletonList("12345678");
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(keywords);
        assertFalse(predicate.test(new PersonBuilder().withPhone("87654321").build()));
    }

    @Test
    public void test_samePartial_returnFalse() {
        List<String> keywords = Collections.singletonList("12345679");
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(keywords);
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_partialNumber_returnTrue() {
        List<String> keywords = Collections.singletonList("1234567");
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(keywords);
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_multiplePartialNumber_returnFalse() {
        List<String> keywords = Arrays.asList("1234567", "8765432");
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(keywords);
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_multipleCompelte_returnTrue() {
        List<String> keywords = Arrays.asList("12345678", "87654321");
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(keywords);
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
        assertTrue(predicate.test(new PersonBuilder().withPhone("87654321").build()));
    }

    @Test
    public void test_nullAddress_returnsFalse() {
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(
                Collections.singletonList("Jurong"));
        assertFalse(predicate.test(new PersonStubNullPhoneNumber()));
    }

    @Test
    public void equals_sameObject_returnTrue() {
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(
                Collections.singletonList("98765432"));

        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValues_returnTrue() {
        List<String> keywords = Collections.singletonList("98765432");
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(keywords);
        PhoneNumberContainsKeywordPredicate predicateCopy = new PhoneNumberContainsKeywordPredicate(keywords);

        assertTrue(predicate.equals(predicateCopy));
    }

    @Test
    public void equals_differentType_returnFalse() {
        List<String> keywords = Collections.singletonList("98765432");
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(keywords);

        assertFalse(predicate.equals(5.0f));
    }

    private class PersonStubNullPhoneNumber extends Person {
        private Phone phone = null;

        public PersonStubNullPhoneNumber() {
            super(new seedu.address.model.person.Name("Alice"), new seedu.address.model.person.Phone("12345678"),
                    new seedu.address.model.person.Email("test@gmail.com"),
                    new seedu.address.model.person.Address("123, Jurong West Ave 6, #08-111"),
                    new TelegramUsername("alice"),
                    new HashSet<>());
        }

        @Override
        public Phone getPhone() {
            return this.phone;
        }
    }
}
