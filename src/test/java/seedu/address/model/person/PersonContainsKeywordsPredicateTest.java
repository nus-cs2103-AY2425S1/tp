package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsKeywordsPredicate firstP = new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondP = new PersonContainsKeywordsPredicate(secondPredicateKeywordList);

        // sam
        // e object -> returns true
        assertTrue(firstP.equals(firstP));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPCopy = new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstP.equals(firstPCopy));

        // different types -> returns false
        assertFalse(firstP.equals(1));

        // null -> returns false
        assertFalse(firstP.equals(null));

        // different person -> returns false
        assertFalse(firstP.equals(secondP));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new PersonContainsKeywordsPredicate(Arrays
                .asList("91234567", "alice@email.com", "Main", "Street"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Keywords match birthday, but does not match name
        predicate = new PersonContainsKeywordsPredicate(Arrays
                .asList("26", "June", "2002"));
        assertTrue(predicate.test(new PersonBuilder().withBirthday("26 06 2002").build()));

        // Keywords match address, but does not match name, phone and email
        predicate = new PersonContainsKeywordsPredicate(Arrays
                .asList("Jurong"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").withPhone("81111111")
                .withEmail("bob123@gmail.com").build()));

        // Keywords match tags, but does not match anything else
        predicate = new PersonContainsKeywordsPredicate(Arrays
                .asList("likesCoffee"));
        assertTrue(predicate.test(new PersonBuilder().withTags("likesCoffee", "wealthy").build()));

        // Partial match (keyword is a substring of name but not a full word match)
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Ali"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Extra spaces in keywords
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("  Alice  "));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords matching multiple parts of the name
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol Bob").build()));

        // Keyword matches a word in the middle of the name
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol Bob").build()));

    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Special characters in keyword
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Alic@"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keyword with numbers
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Alice123"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(keywords);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
