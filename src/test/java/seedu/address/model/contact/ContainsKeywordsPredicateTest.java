//@@author LowXiSi
package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalContacts.ALICE;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactBuilder;
import seedu.address.testutil.ContainsKeywordsPredicateBuilder;

public class ContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        ContainsKeywordsPredicate firstPredicate =
                new ContainsKeywordsPredicateBuilder().withNameKeywords("first").withTelegramHandleKeywords("first")
                        .withEmailKeywords("first").withStudentStatusKeywords("first").withRoleKeywords("first")
                        .withNicknameKeywords("first").build();
        ContainsKeywordsPredicate secondPredicate =
                new ContainsKeywordsPredicateBuilder().withNameKeywords("first", "second")
                        .withTelegramHandleKeywords("first", "second").withEmailKeywords("first", "second")
                        .withStudentStatusKeywords("first", "second").withRoleKeywords("first", "second")
                        .withNicknameKeywords("first", "second").build();

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsKeywordsPredicate firstPredicateCopy =
                new ContainsKeywordsPredicateBuilder().withNameKeywords("first").withTelegramHandleKeywords("first")
                        .withEmailKeywords("first").withStudentStatusKeywords("first").withRoleKeywords("first")
                        .withNicknameKeywords("first").build();
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // differs in only one field -> returns false
        ContainsKeywordsPredicate differentNameKeywordsFirstPredicate =
                new ContainsKeywordsPredicateBuilder().withNameKeywords("first", "second")
                        .withTelegramHandleKeywords("first").withEmailKeywords("first")
                        .withStudentStatusKeywords("first").withRoleKeywords("first")
                        .withNicknameKeywords("first").build();
        assertFalse(firstPredicate.equals(differentNameKeywordsFirstPredicate));

        // different number of fields -> returns false
        ContainsKeywordsPredicate noNameKeywordsFirstPredicate =
                new ContainsKeywordsPredicateBuilder().withTelegramHandleKeywords("first").withEmailKeywords("first")
                        .withStudentStatusKeywords("first").withRoleKeywords("first")
                        .withNicknameKeywords("first").build();
        assertFalse(firstPredicate.equals(noNameKeywordsFirstPredicate));

        // different ContainsKeywordsPredicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One name keyword
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicateBuilder().withNameKeywords("Alice").build();
        assertTrue(predicate.test(new ContactBuilder().withName("Alice Bob").build()));

        // Multiple name keywords
        predicate = new ContainsKeywordsPredicateBuilder().withNameKeywords("Alice", "Bob").build();
        assertTrue(predicate.test(new ContactBuilder().withName("Alice Bob").build()));

        // Mixed-case name keywords
        predicate = new ContainsKeywordsPredicateBuilder().withNameKeywords("aLIce", "bOB").build();
        assertTrue(predicate.test(new ContactBuilder().withName("Alice Bob").build()));

        // Substring name keywords
        predicate = new ContainsKeywordsPredicateBuilder().withNameKeywords("Lic", "OB").build();
        assertTrue(predicate.test(new ContactBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching name keyword
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicateBuilder().withNameKeywords("Carol").build();
        assertFalse(predicate.test(new ContactBuilder().withName("Alice Bob").build()));

        // Only one matching name keyword
        predicate = new ContainsKeywordsPredicateBuilder().withNameKeywords("Bob", "Carol").build();
        assertFalse(predicate.test(new ContactBuilder().withName("Alice Carol").build()));

        // name keywords match phone, email and address, but does not match name
        predicate = new ContainsKeywordsPredicateBuilder()
                .withNameKeywords("12345", "alice@email.com", "Main", "Street").build();
        assertFalse(predicate.test(new ContactBuilder().withName("Alice").withTelegramHandle("12345")
                .withEmail("alice@email.com").withStudentStatus("undergraduate 1").build()));
    }

    @Test
    public void test_multipleFieldsAllMatch_returnsTrue() {
        // Two matching fields
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicateBuilder().withNameKeywords("Alice")
                .withRoleKeywords("Admin").build();
        assertTrue(predicate.test(ALICE));

        // All matching fields
        predicate = new ContainsKeywordsPredicateBuilder().withNameKeywords("Alice")
                .withTelegramHandleKeywords("94351253")
                .withEmailKeywords("alice").withStudentStatusKeywords("undergrad").withRoleKeywords("Admin")
                .withNicknameKeywords("nickname").build();
        assertTrue(predicate.test(new ContactBuilder(ALICE).withNickname("nickname").build()));
    }

    @Test
    public void test_multipleFieldsOnlySomeMatch_returnsFalse() {
        // Name matches but role does not match
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicateBuilder().withNameKeywords("Alice")
                .withRoleKeywords("President").build();
        assertFalse(predicate.test(ALICE));

        // All fields match except for name
        predicate = new ContainsKeywordsPredicateBuilder().withNameKeywords("Alice", "Bob")
                .withTelegramHandleKeywords("94351253")
                .withEmailKeywords("alice").withStudentStatusKeywords("undergrad").withRoleKeywords("Admin")
                .withNicknameKeywords("nickname").build();
        assertFalse(predicate.test(new ContactBuilder(ALICE).withNickname("nickname").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ContainsKeywordsPredicate predicate =
                new ContainsKeywordsPredicateBuilder().withNameKeywords(keywords).build();

        String expected = ContainsKeywordsPredicate.class.getCanonicalName() + "{nameKeywords=" + keywords + ", "
                + "telegramHandleKeywords=[], emailKeywords=[], studentStatusKeywords=[], roleKeywords=[], "
                + "nicknameKeywords=[]}";
        assertEquals(expected, predicate.toString());
    }
}
