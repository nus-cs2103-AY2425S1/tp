package seedu.address.model.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.person.TelegramUsername;
import seedu.address.model.person.predicates.AddressContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;
public class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AddressContainsKeywordsPredicate firstPredicate = new AddressContainsKeywordsPredicate(
                firstPredicateKeywordList);
        AddressContainsKeywordsPredicate secondPredicate = new AddressContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy = new AddressContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnTrue() {
        // One keyword
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections
                .singletonList("Jurong"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Jurong", "West"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));

        // Only one matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Jurong", "East"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("jUroNg", "wEst"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));
    }

    @Test
    public void test_addressPartialKeyword_returnTrue() {
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(
                Collections.singletonList("Juron"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));
    }

    @Test
    public void test_addressContainOneKeyword_returnTrue() {
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(
                new ArrayList<>(Arrays.asList("Jurong", "East")));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));
    }

    @Test
    public void test_addressDoesNotContainAnyPartial_returnFalse() {
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(
                new ArrayList<>(Arrays.asList("Juron", "East")));
        assertFalse(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));
    }

    @Test
    public void equals_differentNumberKeywords() {
        AddressContainsKeywordsPredicate firstPredicate = new
                AddressContainsKeywordsPredicate(Collections.singletonList("first"));
        AddressContainsKeywordsPredicate secondPredicate = new
                AddressContainsKeywordsPredicate(Arrays.asList("first", "second"));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nullAddress_returnsFalse() {
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(
                Collections.singletonList("Jurong"));
        assertFalse(predicate.test(new PersonStubNullAddress()));
    }

    /**
     * A stub class to test for null address as Person should have a non null address
     */
    private class PersonStubNullAddress extends Person {
        private Address address = null;

        public PersonStubNullAddress() {
            super(new seedu.address.model.person.Name("Alice"), new seedu.address.model.person.Phone("12345678"),
                    new seedu.address.model.person.Email("test@gmail.com"),
                    new seedu.address.model.person.Address("123, Jurong West Ave 6, #08-111"),
                    new TelegramUsername("alice"),
                    new HashSet<>());
        }

        @Override
        public Address getAddress() {
            return this.address;
        }
    }

}
