package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() throws ParseException {
        // Same values -> returns true
        PersonContainsKeywordsPredicate predicateOne = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_NAME.getPrefix(), "John"));
        PersonContainsKeywordsPredicate predicateOneCopy = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_NAME.getPrefix(), "John"));
        assertTrue(predicateOne.equals(predicateOneCopy));

        // Same object -> returns true
        assertTrue(predicateOne.equals(predicateOne));

        // Null -> returns false
        assertFalse(predicateOne.equals(null));

        // Different type -> returns false
        assertFalse(predicateOne.equals(5));

        // Different values (name) -> returns false
        PersonContainsKeywordsPredicate differentPredicate = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_NAME.getPrefix(), "Alice"));
        assertFalse(predicateOne.equals(differentPredicate));

        // Different values (phone) -> returns false
        PersonContainsKeywordsPredicate predicateTwo = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_PHONE.getPrefix(), "12345678"));
        assertFalse(predicateOne.equals(predicateTwo));

        // Different values (email) -> returns false
        PersonContainsKeywordsPredicate predicateWithEmail = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_EMAIL.getPrefix(), "alice@example.com"));
        PersonContainsKeywordsPredicate predicateWithDifferentEmail = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_EMAIL.getPrefix(), "bob@example.com"));
        assertFalse(predicateWithEmail.equals(predicateWithDifferentEmail));

        // Different values (address) -> returns false
        PersonContainsKeywordsPredicate predicateWithAddress = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_ADDRESS.getPrefix(), "123 Main St"));
        PersonContainsKeywordsPredicate predicateWithDifferentAddress = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_ADDRESS.getPrefix(), "456 Elm St"));
        assertFalse(predicateWithAddress.equals(predicateWithDifferentAddress));

        // Different values (tags) -> returns false
        PersonContainsKeywordsPredicate predicateWithTag = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_TAG.getPrefix(), "friends"));
        PersonContainsKeywordsPredicate predicateWithDifferentTag = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_TAG.getPrefix(), "colleagues"));
        assertFalse(predicateWithTag.equals(predicateWithDifferentTag));

        // Different values (subjects) -> returns false
        PersonContainsKeywordsPredicate predicateWithSubject = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_SUBJECT.getPrefix(), "Physics"));
        PersonContainsKeywordsPredicate predicateWithDifferentSubject = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_SUBJECT.getPrefix(), "English"));
        assertFalse(predicateWithSubject.equals(predicateWithDifferentSubject));

        // Different values (classes) -> returns false
        PersonContainsKeywordsPredicate predicateWithClass = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_CLASSES.getPrefix(), "ML403"));
        PersonContainsKeywordsPredicate predicateWithDifferentClass = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_CLASSES.getPrefix(), "LE202"));
        assertFalse(predicateWithClass.equals(predicateWithDifferentClass));

        // Different fields (name and email) -> returns false
        PersonContainsKeywordsPredicate predicateWithNameAndEmail = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_NAME.getPrefix(), "John", PREFIX_EMAIL.getPrefix(), "john@example.com"));
        assertFalse(predicateOne.equals(predicateWithNameAndEmail));

        // Same email and tags -> returns true
        PersonContainsKeywordsPredicate predicateWithSameEmailAndTag = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_EMAIL.getPrefix(), "alice@example.com", PREFIX_TAG.getPrefix(), "friends"));
        PersonContainsKeywordsPredicate predicateWithSameEmailAndTagCopy = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_EMAIL.getPrefix(), "alice@example.com", PREFIX_TAG.getPrefix(), "friends"));
        assertTrue(predicateWithSameEmailAndTag.equals(predicateWithSameEmailAndTagCopy));

        // Multiple different fields (name, phone, email, and tags) -> returns false
        PersonContainsKeywordsPredicate predicateWithMultipleFields = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_NAME.getPrefix(), "John", PREFIX_PHONE.getPrefix(), "12345678",
                        PREFIX_EMAIL.getPrefix(), "john@example.com", PREFIX_TAG.getPrefix(), "friends"));
        assertFalse(predicateOne.equals(predicateWithMultipleFields));
    }


    @Test
    public void test_personContainsKeywords_returnsTrue() throws ParseException {
        // Name
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_NAME.getPrefix(), "John"));
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").build()));

        // Contact
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_PHONE.getPrefix(), "12345678"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Email
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_EMAIL.getPrefix(), "alice12@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice12@example.com").build()));

        // Address
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_ADDRESS.getPrefix(), "Test"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Test Rd").build()));

        // Tags
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_TAG.getPrefix(), "test"));
        assertTrue(predicate.test(new PersonBuilder().withTags("test").build()));

        // Subjects
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_SUBJECT.getPrefix(), "Physics"));
        assertTrue(predicate.test(new PersonBuilder().withSubject("Physics").build()));

        // Classes
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_CLASSES.getPrefix(), "7A"));
        assertTrue(predicate.test(new PersonBuilder().withClasses("7A").build()));

        // Multiple keywords in one category
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_NAME.getPrefix(), "John", "Adam"));
        assertTrue(predicate.test(new PersonBuilder().withName("John").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Adam").build()));

        // Multiple categories
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_NAME.getPrefix(), "John", "Adam",
                PREFIX_PHONE.getPrefix(), "12345678", PREFIX_EMAIL.getPrefix(), "test@example.com", "alice@nus.com"));
        assertTrue(predicate.test(new PersonBuilder().withName("John").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Adam").build()));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
        assertTrue(predicate.test(new PersonBuilder().withEmail("test@example.com").build()));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@nus.com").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Steve").withPhone("12345678").build()));

        // Empty search query
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(""));
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").build()));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice12@example.com").build()));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Test Rd").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("test").build()));
        assertTrue(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void test_personContainsKeywords_returnsFalse() throws ParseException {
        // Name
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_NAME.getPrefix(), "John"));
        assertFalse(predicate.test(new PersonBuilder().withName("Jane Doe").build()));

        // Contact
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_PHONE.getPrefix(), "87654321"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Email
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_EMAIL.getPrefix(), "alice12@example.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("bobby@example.com").build()));

        // Address
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_ADDRESS.getPrefix(), "Test"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("123 NUS Rd").build()));

        // Tags
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_TAG.getPrefix(), "test"));
        assertFalse(predicate.test(new PersonBuilder().withTags("noMatchingTags").build()));

        // Subjects
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_SUBJECT.getPrefix(), "Physics"));
        assertFalse(predicate.test(new PersonBuilder().withSubject("English").build()));

        // Classes
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_CLASSES.getPrefix(), "7A"));
        assertFalse(predicate.test(new PersonBuilder().withClasses("8B").build()));
    }
}
