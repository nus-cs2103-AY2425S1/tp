package seedu.address.model.person;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonPredicateTest {

    @Test
    public void name_equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonPredicate firstPredicate = new PersonPredicate(firstPredicateKeywordList, emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        PersonPredicate secondPredicate = new PersonPredicate(secondPredicateKeywordList, emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonPredicate firstPredicateCopy = new PersonPredicate(firstPredicateKeywordList, emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void all_equals() {

        List<String> names = Arrays.asList("Alice", "Bob");
        List<String> phones = Collections.singletonList("12345678");
        List<String> emails = Collections.singletonList("test@example.com");
        List<String> addresses = Collections.singletonList("123 Main St");
        List<String> registerNumbers = Collections.singletonList("RN123");
        List<String> sexes = Collections.singletonList("Female");
        List<String> classes = Collections.singletonList("ClassA");
        List<String> ecNames = Collections.singletonList("John Doe");
        List<String> ecNumbers = Collections.singletonList("87654321");
        List<String> tags = Collections.singletonList("VIP");

        PersonPredicate predicate1 = new PersonPredicate(
                names, phones, emails, addresses, registerNumbers, sexes, classes, ecNames, ecNumbers, tags);

        PersonPredicate predicate2 = new PersonPredicate(
                names, phones, emails, addresses, registerNumbers, sexes, classes, ecNames, ecNumbers, tags);

        // same object -> returns true
        assertTrue(predicate1.equals(predicate1));

        // same values -> returns true
        assertTrue(predicate1.equals(predicate2));

        // different types -> returns false
        assertFalse(predicate1.equals("not a predicate"));

        // null -> returns false
        assertFalse(predicate1.equals(null));

        // different person names -> returns false
        PersonPredicate diffNamesPredicate = new PersonPredicate(Collections.singletonList("Charlie"), phones,
                emails, addresses, registerNumbers, sexes, classes, ecNames, ecNumbers, tags);
        assertFalse(predicate1.equals(diffNamesPredicate));

        // different person tags -> returns false
        PersonPredicate differentTagsPredicate = new PersonPredicate(names, phones, emails, addresses, registerNumbers,
                sexes, classes, ecNames, ecNumbers, Collections.singletonList("Regular"));
        assertFalse(predicate1.equals(differentTagsPredicate));
    }


    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonPredicate predicate = new PersonPredicate(Collections.singletonList("Alice"), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonPredicate(Arrays.asList("Alice", "Bob"), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonPredicate(Arrays.asList("Bob", "Carol"), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonPredicate(Arrays.asList("aLIce", "bOB"), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonPredicate(Arrays.asList("Carol"), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PersonPredicate predicate = new PersonPredicate(emptyList(), Collections.singletonList("12345678"), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Multiple keywords
        predicate = new PersonPredicate(emptyList(), Arrays.asList("12345678", "45678912"), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withPhone("1234567845678912").build()));

        // Only one matching keyword
        predicate = new PersonPredicate(emptyList(), Arrays.asList("99999999", "45612345"), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withPhone("45612345").build()));

    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Non-matching keyword
        predicate = new PersonPredicate(emptyList(), Arrays.asList("99999999"), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Multiple non-matching keywords
        predicate = new PersonPredicate(emptyList(), Arrays.asList("98765432", "87654321"), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(),
                Collections.singletonList("example@gmail.com"), emptyList(), emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withEmail("example@gmail.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("example@example.com").build()));

        predicate = new PersonPredicate(emptyList(), emptyList(), Arrays.asList("wrong@example.com"),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("example@example.com").build()));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                Collections.singletonList("Sentosa"), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(),
                emptyList());
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Sentosa").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("123 Main St").build()));

        predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                Collections.singletonList("Thames"), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(),
                emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("123 Main St").build()));
    }

    @Test
    public void test_registerNumberContainsKeywords_returnsTrue() {
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), Collections.singletonList("1"), emptyList(), emptyList(), emptyList(), emptyList(),
                emptyList());
        assertTrue(predicate.test(new PersonBuilder().withRegisterNumber("1").build()));
    }

    @Test
    public void test_registerNumberDoesNotContainKeywords_returnsFalse() {
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withRegisterNumber("12").build()));

        predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), Collections.singletonList("9"), emptyList(), emptyList(), emptyList(), emptyList(),
                emptyList());
        assertFalse(predicate.test(new PersonBuilder().withRegisterNumber("12").build()));
    }

    @Test
    public void test_sexContainsKeywords_returnsTrue() {
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), Collections.singletonList("F"), emptyList(), emptyList(), emptyList(),
                emptyList());
        assertTrue(predicate.test(new PersonBuilder().withSex("F").build()));
    }

    @Test
    public void test_sexDoesNotContainKeywords_returnsFalse() {
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSex("F").build()));

        predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), Collections.singletonList("M"), emptyList(), emptyList(), emptyList(),
                emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSex("F").build()));

        predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), Collections.singletonList("Female"), emptyList(), emptyList(), emptyList(),
                emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSex("F").build()));
    }

    @Test
    public void test_classContainsKeywords_returnsTrue() {
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), Collections.singletonList("1A"), emptyList(), emptyList(),
                emptyList());
        assertTrue(predicate.test(new PersonBuilder().withStudentClass("1A").build()));
    }

    @Test
    public void test_classDoesNotContainKeywords_returnsFalse() {
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withStudentClass("1B").build()));

        predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), Collections.singletonList("1B"), emptyList(), emptyList(),
                emptyList());
        assertFalse(predicate.test(new PersonBuilder().withStudentClass("1C").build()));
    }

    @Test
    public void test_ecNameContainsKeywords_returnsTrue() {
        // One keyword
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), Collections.singletonList("Alice"),
                emptyList(), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withEcName("Alice").build()));

        // Multiple keywords
        predicate = new PersonPredicate(emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), Arrays.asList("Alice", "Bob"),
                emptyList(), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withEcName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonPredicate(emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), Arrays.asList("Bob", "Carol"),
                emptyList(), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withEcName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonPredicate(emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), Arrays.asList("aLIce", "bOB"),
                emptyList(), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withEcName("Alice Bob").build()));
    }

    @Test
    public void test_ecNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEcName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonPredicate(emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), Arrays.asList("Carol"),
                emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEcName("Alice Bob").build()));
    }

    @Test
    public void test_ecNumberContainsKeywords_returnsTrue() {
        // One keyword
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(),
                Collections.singletonList("12345678"), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withEcNumber("12345678").build()));

        // Only one matching keyword
        predicate = new PersonPredicate(emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(),
                Arrays.asList("99999999", "45612345"), emptyList());
        assertTrue(predicate.test(new PersonBuilder().withEcNumber("45612345").build()));

    }

    @Test
    public void test_ecNumberDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEcNumber("12345678").build()));

        // Non-matching keyword
        predicate = new PersonPredicate(emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(),
                Arrays.asList("99999999"), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEcNumber("12345678").build()));

        // Multiple non-matching keywords
        predicate = new PersonPredicate(emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(),
                Arrays.asList("98765432", "87654321"), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEcNumber("12345678").build()));
    }


    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(),
                Collections.singletonList("Weak"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Weak").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        PersonPredicate predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Weak").build()));

        predicate = new PersonPredicate(emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(),
                Collections.singletonList("Regular"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Weak").build()));
    }


    @Test
    public void toStringMethod() {
        List<String> names = List.of("name1", "name2");
        PersonPredicate predicate = new PersonPredicate(names, emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());

        String expected = PersonPredicate.class.getCanonicalName() + "{names=" + names + ", phones=[], emails=[], "
                + "addresses=[], registerNumbers=[], sexes=[], classes=[], ecNames=[], ecNumbers=[], tags=[]}";
        assertEquals(expected, predicate.toString());
    }
}
