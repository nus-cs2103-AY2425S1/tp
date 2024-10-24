package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.testutil.PersonBuilder;

public class ContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        ArgumentMultimap mapForFirstPredicate = new ArgumentMultimap();
        ArgumentMultimap mapForSecondPredicate = new ArgumentMultimap();

        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        firstPredicateKeywordList.stream().forEach(keyword -> mapForFirstPredicate.put(PREFIX_NAME, keyword));
        secondPredicateKeywordList.stream().forEach(keyword -> mapForSecondPredicate.put(PREFIX_NAME, keyword));

        ContainsKeywordsPredicate firstPredicate = new ContainsKeywordsPredicate(mapForFirstPredicate);
        ContainsKeywordsPredicate secondPredicate = new ContainsKeywordsPredicate(mapForSecondPredicate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsKeywordsPredicate firstPredicateCopy = new ContainsKeywordsPredicate(mapForFirstPredicate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {

        // One keyword
        ArgumentMultimap mapForOneKeyword = new ArgumentMultimap();
        mapForOneKeyword.put(PREFIX_NAME, "Alice");
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForOneKeyword);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        List<String> multipleKeywords = Arrays.asList("Alice", "Bob");
        ArgumentMultimap mapForMultipleKeywords = new ArgumentMultimap();
        multipleKeywords.stream().forEach(keyword -> mapForMultipleKeywords.put(PREFIX_NAME, keyword));
        predicate = new ContainsKeywordsPredicate(mapForMultipleKeywords);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        List<String> oneMatchingKeyword = Arrays.asList("Bob", "Carol");
        ArgumentMultimap mapForOneMatchingKeyword = new ArgumentMultimap();
        oneMatchingKeyword.stream().forEach(keyword -> mapForOneMatchingKeyword.put(PREFIX_NAME, keyword));
        predicate = new ContainsKeywordsPredicate(mapForOneMatchingKeyword);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        List<String> mixedCaseKeywords = Arrays.asList("aLIce", "bOB");
        ArgumentMultimap mapForMixedCaseKeywords = new ArgumentMultimap();
        mixedCaseKeywords.stream().forEach(keyword -> mapForMixedCaseKeywords.put(PREFIX_NAME, keyword));
        predicate = new ContainsKeywordsPredicate(mapForMixedCaseKeywords);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(new ArgumentMultimap());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        ArgumentMultimap mapForNonMatchingKeyword = new ArgumentMultimap();
        mapForNonMatchingKeyword.put(PREFIX_NAME, "Carol");
        predicate = new ContainsKeywordsPredicate(mapForNonMatchingKeyword);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        ArgumentMultimap mapForNotMatchingNameKeywords = new ArgumentMultimap();
        mapForNotMatchingNameKeywords.put(PREFIX_PHONE, "12345");
        mapForNotMatchingNameKeywords.put(PREFIX_EMAIL, "alice@email.com");
        mapForNotMatchingNameKeywords.put(PREFIX_ADDRESS, "Main");
        mapForNotMatchingNameKeywords.put(PREFIX_ADDRESS, "Streer");
        predicate = new ContainsKeywordsPredicate(mapForNotMatchingNameKeywords);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod_withName() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ArgumentMultimap mapForKeywords = new ArgumentMultimap();
        keywords.stream().forEach(keyword -> mapForKeywords.put(PREFIX_NAME, keyword));
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForKeywords);

        String expected = ContainsKeywordsPredicate.class.getCanonicalName() + "{n/=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void toStringMethod_withTags() {
        List<String> names = List.of("Alice", "Bob");
        List<String> tags = List.of("friend", "family");

        ArgumentMultimap mapForKeywords = new ArgumentMultimap();
        names.forEach(name -> mapForKeywords.put(PREFIX_NAME, name));
        tags.forEach(tag -> mapForKeywords.put(PREFIX_TAG, tag));

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForKeywords);

        String expected = ContainsKeywordsPredicate.class.getCanonicalName()
                + "{n/=" + names + ", t/=" + tags + "}";

        assertEquals(expected, predicate.toString());
    }

    @Test
    public void toStringMethod_withRole() {
        List<String> roles = List.of("patient", "caregiver");

        ArgumentMultimap mapForKeywords = new ArgumentMultimap();
        roles.forEach(role -> mapForKeywords.put(PREFIX_ROLE, role));

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForKeywords);

        String expected = ContainsKeywordsPredicate.class.getCanonicalName()
                + "{role/=" + roles + "}";

        assertEquals(expected, predicate.toString());
    }

    @Test
    public void test_personWithNoMatchingTags_returnsFalse() {
        ArgumentMultimap mapForNoMatchingTags = new ArgumentMultimap();
        mapForNoMatchingTags.put(PREFIX_TAG, "friend");
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForNoMatchingTags);
        assertFalse(predicate.test(new PersonBuilder().withTags("enemy", "foe").build()));
    }
    @Test
    public void test_personWithNoMatchingNric_returnsFalse() {
        ArgumentMultimap mapForNoMatchingTags = new ArgumentMultimap();
        mapForNoMatchingTags.put(PREFIX_NRIC, "s1234567d");
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForNoMatchingTags);
        assertFalse(predicate.test(new PersonBuilder().withNric("S6283947C").build()));
    }
    @Test
    public void test_personWithNoMatchingRole_returnsFalse() {
        ArgumentMultimap mapForNoMatchingTags = new ArgumentMultimap();
        mapForNoMatchingTags.put(PREFIX_ROLE, "patient");
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForNoMatchingTags);
        assertFalse(predicate.test(new PersonBuilder().withRole("caregiver").build()));
    }

    @Test
    public void test_personWithNoMatchingName_returnsFalse() {
        ArgumentMultimap mapForNoMatchingTags = new ArgumentMultimap();
        mapForNoMatchingTags.put(PREFIX_NAME, "test");
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForNoMatchingTags);
        assertFalse(predicate.test(new PersonBuilder().withName("John").build()));
    }


}
