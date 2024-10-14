package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class CombinedPredicateTest {

    @Test
    public void test_nameMatches_returnsTrue() {
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        CombinedPredicate combinedPredicate = new CombinedPredicate(namePredicate, phonePredicate);

        Person alice = new Person(
                new Name("Alice Wonderland"),
                new Phone("98765432"),
                new Email("alice@example.com"),
                new Address("123 Wonderland Ave"),
                getTags("friend")
        );
        assertTrue(combinedPredicate.test(alice));
    }

    @Test
    public void test_phoneMatches_returnsTrue() {
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(Arrays.asList("98765432"));
        CombinedPredicate combinedPredicate = new CombinedPredicate(namePredicate, phonePredicate);

        Person person = new Person(
                new Name("Bob Marley"),
                new Phone("98765432"),
                new Email("bob@example.com"),
                new Address("456 Reggae Road"),
                getTags("musician")
        );
        assertTrue(combinedPredicate.test(person));
    }

    @Test
    public void test_bothMatch_returnsTrue() {
        // Both name and phone match
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(Arrays.asList("98765432"));
        CombinedPredicate combinedPredicate = new CombinedPredicate(namePredicate, phonePredicate);

        // Person with matching name and phone number
        Person person = new Person(
                new Name("Alice Wonderland"),
                new Phone("98765432"),
                new Email("alice@example.com"),
                new Address("123 Wonderland Ave"),
                getTags("friend")
        );
        assertTrue(combinedPredicate.test(person));
    }

    @Test
    public void test_neitherMatch_returnsFalse() {
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(Arrays.asList("98765432"));
        CombinedPredicate combinedPredicate = new CombinedPredicate(namePredicate, phonePredicate);

        Person person = new Person(
                new Name("Charlie Brown"),
                new Phone("12345678"),
                new Email("charlie@example.com"),
                new Address("789 Snoopy Street"),
                getTags("peanuts")
        );
        assertFalse(combinedPredicate.test(person));
    }

    @Test
    public void test_onlyPhoneMatches_returnsTrue() {
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(Arrays.asList("98765432"));
        CombinedPredicate combinedPredicate = new CombinedPredicate(namePredicate, phonePredicate);

        Person person = new Person(
                new Name("Charlie Brown"),
                new Phone("98765432"),
                new Email("charlie@example.com"),
                new Address("789 Snoopy Street"),
                getTags("peanuts")
        );
        assertTrue(combinedPredicate.test(person));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        // Same object should return true
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(Arrays.asList("98765432"));
        CombinedPredicate combinedPredicate = new CombinedPredicate(namePredicate, phonePredicate);

        assertTrue(combinedPredicate.equals(combinedPredicate));
    }

    @Test
    public void equals_samePredicates_returnsTrue() {
        NameContainsKeywordsPredicate namePredicate1 = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        PhoneContainsKeywordsPredicate phonePredicate1 = new PhoneContainsKeywordsPredicate(Arrays.asList("98765432"));

        NameContainsKeywordsPredicate namePredicate2 = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        PhoneContainsKeywordsPredicate phonePredicate2 = new PhoneContainsKeywordsPredicate(Arrays.asList("98765432"));

        CombinedPredicate combinedPredicate1 = new CombinedPredicate(namePredicate1, phonePredicate1);
        CombinedPredicate combinedPredicate2 = new CombinedPredicate(namePredicate2, phonePredicate2);

        assertTrue(combinedPredicate1.equals(combinedPredicate2));
    }

    @Test
    public void equals_differentPredicates_returnsFalse() {
        NameContainsKeywordsPredicate namePredicate1 = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        PhoneContainsKeywordsPredicate phonePredicate1 = new PhoneContainsKeywordsPredicate(Arrays.asList("98765432"));

        NameContainsKeywordsPredicate namePredicate2 = new NameContainsKeywordsPredicate(Arrays.asList("Bob"));
        PhoneContainsKeywordsPredicate phonePredicate2 = new PhoneContainsKeywordsPredicate(Arrays.asList("12345678"));

        CombinedPredicate combinedPredicate1 = new CombinedPredicate(namePredicate1, phonePredicate1);
        CombinedPredicate combinedPredicate2 = new CombinedPredicate(namePredicate2, phonePredicate2);

        assertFalse(combinedPredicate1.equals(combinedPredicate2));
    }

    private Set<Tag> getTags(String... tagNames) {
        Set<Tag> tags = new HashSet<>();
        for (String tagName : tagNames) {
            tags.add(new Tag(tagName));
        }
        return tags;
    }
}
