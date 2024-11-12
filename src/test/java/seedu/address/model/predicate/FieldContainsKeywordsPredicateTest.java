package seedu.address.model.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class FieldContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");
        List<String> thirdPredicateKeywordList = List.of("97564312");
        List<String> forthPredicateKeywordList = List.of("ali@example.com");
        List<String> fifthPredicateKeywordList = Arrays.asList("Clementi, Ave, 11, #02-20");

        FieldContainsKeywordsPredicate<Person> firstNamePredicate = new FieldContainsKeywordsPredicate<>(
                firstPredicateKeywordList, Person::getFullName, true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
        FieldContainsKeywordsPredicate<Person> secondNamePredicate = new FieldContainsKeywordsPredicate<>(
                secondPredicateKeywordList, Person::getFullName, true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
        FieldContainsKeywordsPredicate<Person> phonePredicate = new FieldContainsKeywordsPredicate<>(
                thirdPredicateKeywordList, Person::getPhoneValue,
                false, FieldContainsKeywordsPredicate.PHONE_IDENTIFIER);
        FieldContainsKeywordsPredicate<Person> emailPredicate = new FieldContainsKeywordsPredicate<>(
                forthPredicateKeywordList, Person::getEmailValue,
                false, FieldContainsKeywordsPredicate.EMAIL_IDENTIFIER);
        FieldContainsKeywordsPredicate<Person> addressPredicate = new FieldContainsKeywordsPredicate<>(
                fifthPredicateKeywordList, Person::getAddressValue,
                true, FieldContainsKeywordsPredicate.ADDRESS_IDENTIFIER);

        // same object -> returns true
        assertEquals(firstNamePredicate, firstNamePredicate);

        // same values -> returns true
        FieldContainsKeywordsPredicate<Person> firstNamePredicateCopy = new FieldContainsKeywordsPredicate<>(
                firstPredicateKeywordList, Person::getFullName, true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
        assertEquals(firstNamePredicate, firstNamePredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstNamePredicate);

        // null -> returns false
        assertNotEquals(null, firstNamePredicate);

        // different person -> returns false
        assertNotEquals(firstNamePredicate, secondNamePredicate);

        // Test case: Different field extractor, same keywords, should return false
        FieldContainsKeywordsPredicate<Person> differentFieldPredicate = new FieldContainsKeywordsPredicate<>(
                firstPredicateKeywordList, Person::getAddressValue,
                true, FieldContainsKeywordsPredicate.ADDRESS_IDENTIFIER);
        assertNotEquals(firstNamePredicate, differentFieldPredicate);

        // Test case: Different isMultipleKeywords flag, should return false
        FieldContainsKeywordsPredicate<Person> differentFlagPredicate = new FieldContainsKeywordsPredicate<>(
                firstPredicateKeywordList, Person::getFullName,
                false, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
        assertNotEquals(firstNamePredicate, differentFlagPredicate);

        // Test case: Different identifiers but same keywords and extractor, should return false
        FieldContainsKeywordsPredicate<Person> differentIdentifierPredicate = new FieldContainsKeywordsPredicate<>(
                firstPredicateKeywordList, Person::getFullName,
                true, FieldContainsKeywordsPredicate.ADDRESS_IDENTIFIER);
        assertNotEquals(firstNamePredicate, differentIdentifierPredicate);

        // Test case: Different keyword lists, should return false
        FieldContainsKeywordsPredicate<Person> differentKeywordsPredicate = new FieldContainsKeywordsPredicate<>(
                secondPredicateKeywordList, Person::getFullName,
                true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
        assertNotEquals(firstNamePredicate, differentKeywordsPredicate);

        // Test case: Same extractor but different keyword content
        FieldContainsKeywordsPredicate<Person> phonePredicateDifferentKeyword = new FieldContainsKeywordsPredicate<>(
                List.of("12345678"), Person::getPhoneValue,
                false, FieldContainsKeywordsPredicate.PHONE_IDENTIFIER);
        assertNotEquals(phonePredicate, phonePredicateDifferentKeyword);

        // Test case: Identical phone predicate, should return true
        FieldContainsKeywordsPredicate<Person> phonePredicateCopy = new FieldContainsKeywordsPredicate<>(
                thirdPredicateKeywordList, Person::getPhoneValue,
                false, FieldContainsKeywordsPredicate.PHONE_IDENTIFIER);
        assertEquals(phonePredicate, phonePredicateCopy);

        // Test case: Identical email predicate, should return true
        FieldContainsKeywordsPredicate<Person> emailPredicateCopy = new FieldContainsKeywordsPredicate<>(
                forthPredicateKeywordList, Person::getEmailValue,
                false, FieldContainsKeywordsPredicate.EMAIL_IDENTIFIER);
        assertEquals(emailPredicate, emailPredicateCopy);

        // Test case: Identical address predicate, should return true
        FieldContainsKeywordsPredicate<Person> addressPredicateCopy = new FieldContainsKeywordsPredicate<>(
                fifthPredicateKeywordList, Person::getAddressValue,
                true, FieldContainsKeywordsPredicate.ADDRESS_IDENTIFIER);
        assertEquals(addressPredicate, addressPredicateCopy);
    }

    @Test
    public void test_fieldContainsKeywords_returnsTrue() {
        FieldContainsKeywordsPredicate<Person> singleKeywordPredicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList("Alice"), Person::getFullName,
                true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);

        FieldContainsKeywordsPredicate<Person> multipleKeywordPredicate = new FieldContainsKeywordsPredicate<>(
                Arrays.asList("Alice", "Jason"), Person::getFullName,
                true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);

        FieldContainsKeywordsPredicate<Person> mixedCaseKeywordPredicate = new FieldContainsKeywordsPredicate<>(
                Arrays.asList("ALiCe", "bOB"), Person::getFullName,
                true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);

        // Single keyword
        assertTrue(singleKeywordPredicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        assertTrue(multipleKeywordPredicate.test(new PersonBuilder().withName("Alice Bob").build()));
        assertTrue(multipleKeywordPredicate.test(new PersonBuilder().withName("Jason Ho").build()));

        // Mixed-case keywords
        assertTrue(mixedCaseKeywordPredicate.test(new PersonBuilder().withName("Alice Bob").build()));
        assertTrue(mixedCaseKeywordPredicate.test(new PersonBuilder().withName("Bob Ho").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        FieldContainsKeywordsPredicate<Person> singleKeywordPredicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList("Alice"), Person::getFullName,
                true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);

        FieldContainsKeywordsPredicate<Person> multipleKeywordPredicate = new FieldContainsKeywordsPredicate<>(
                Arrays.asList("Alice", "Jason"), Person::getFullName,
                true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);

        FieldContainsKeywordsPredicate<Person> mixedCaseKeywordPredicate = new FieldContainsKeywordsPredicate<>(
                Arrays.asList("ALiCe", "bOB"), Person::getFullName,
                true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);

        assertFalse(singleKeywordPredicate.test(new PersonBuilder().withName("Ali Bob").build()));

        assertFalse(multipleKeywordPredicate.test(new PersonBuilder().withName("Ali Bob").build()));

        // Non-matching keyword
        assertFalse(multipleKeywordPredicate.test(new PersonBuilder().withName("Ben Bob").build()));

        // Keywords match phone, email and address, but does not match name
        FieldContainsKeywordsPredicate<Person> noMatchNamePredicate = new FieldContainsKeywordsPredicate<>(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"),
                Person::getFullName, true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
        assertFalse(noMatchNamePredicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        FieldContainsKeywordsPredicate<Person> predicate = new FieldContainsKeywordsPredicate<>(
                keywords, Person::getFullName,
                true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);

        String expected = FieldContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
