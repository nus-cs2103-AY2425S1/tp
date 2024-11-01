package seedu.address.model.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.participation.Participation;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.ParticipationBuilder;

public class SubjectMatchesKeywordsPredicateTest {

    @Test
    public void constructor_nullKeyword_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SubjectMatchesKeywordsPredicate(null));
    }

    @Test
    public void test_exactMatch_returnsTrue() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate("Math");
        Participation participation = new ParticipationBuilder().withTutorial(new Tutorial("Math")).build();
        assertTrue(predicate.test(participation));
    }

    @Test
    public void test_caseInsensitiveMatch_returnsTrue() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate("math");
        Participation participation = new ParticipationBuilder().withTutorial(new Tutorial("Math")).build();
        assertTrue(predicate.test(participation));
    }

    @Test
    public void test_partialMatch_returnsFalse() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate("ath");
        Participation participation = new ParticipationBuilder().withTutorial(new Tutorial("Math")).build();
        assertNotEquals(true, predicate.test(participation));
    }

    @Test
    public void test_nonMatchingKeyword_returnsFalse() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate("Science");
        Participation participation = new ParticipationBuilder().withTutorial(new Tutorial("Math")).build();
        assertFalse(predicate.test(participation));
    }

    @Test
    public void test_extraSpacesInKeywordMatch_returnsTrue() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate(" Math ");
        Participation participation = new ParticipationBuilder().withTutorial(new Tutorial("Math")).build();
        assertTrue(predicate.test(participation));
    }

    @Test
    public void test_emptyKeyword_throwAssertionError() {
        assertThrows(AssertionError.class, () -> new SubjectMatchesKeywordsPredicate(""));
    }

    @Test
    public void test_emptySubject_returnsIllegalArgumentException() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate("Math");
        Participation participation = new ParticipationBuilder().withTutorial(new Tutorial("")).build();
        assertThrows(IllegalArgumentException.class, () -> predicate.test(participation));
    }

    @Test
    public void test_specialCharactersInKeywordMatch_returnsTrue() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate("M@th!");
        Participation participation = new ParticipationBuilder().withTutorial(new Tutorial("M@th!")).build();
        assertTrue(predicate.test(participation));
    }

    @Test
    public void test_specialCharactersMismatch_returnsFalse() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate("M@th!");
        Participation participation = new ParticipationBuilder().withTutorial(new Tutorial("Math")).build();
        assertNotEquals(true, predicate.test(participation));
    }

    @Test
    public void test_numericKeywordMatch_returnsTrue() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate("Math101");
        Participation participation = new ParticipationBuilder().withTutorial(new Tutorial("Math101")).build();
        assertTrue(predicate.test(participation));
    }

    @Test
    public void test_numericMismatch_returnsFalse() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate("101");
        Participation participation = new ParticipationBuilder().withTutorial(new Tutorial("Math101")).build();
        assertNotEquals(true, predicate.test(participation));
    }

    @Test
    public void test_toStringCorrectFormat_returnsExpectedString() {
        String arg = "Math";
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate(arg);
        String expected = SubjectMatchesKeywordsPredicate.class.getCanonicalName() + "{keyword=" + arg + "}";
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void test_toStringDifferentKeyword_returnsDifferentString() {
        String arg = "Science";
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate(arg);
        String expected = SubjectMatchesKeywordsPredicate.class.getCanonicalName() + "{keyword=" + arg + "}";
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate("Math");
        assertEquals(predicate, predicate);
    }

    @Test
    public void equals_differentObjectSameKeyword_returnsTrue() {
        SubjectMatchesKeywordsPredicate predicate1 = new SubjectMatchesKeywordsPredicate("Math");
        SubjectMatchesKeywordsPredicate predicate2 = new SubjectMatchesKeywordsPredicate("Math");
        assertEquals(predicate1, predicate2);
    }

    @Test
    public void equals_differentObjectDifferentKeyword_returnsFalse() {
        SubjectMatchesKeywordsPredicate predicate1 = new SubjectMatchesKeywordsPredicate("Math");
        SubjectMatchesKeywordsPredicate predicate2 = new SubjectMatchesKeywordsPredicate("Science");
        assertNotEquals(predicate1, predicate2);
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate("Math");
        assertNotEquals(predicate, new Object());
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate("Math");
        assertNotEquals(predicate, null);
    }

    @Test
    public void test_caseInsensitiveMatchWithSpaces_returnsTrue() {
        SubjectMatchesKeywordsPredicate predicate = new SubjectMatchesKeywordsPredicate("  MATH  ");
        Participation participation = new ParticipationBuilder().withTutorial(new Tutorial("math")).build();
        assertTrue(predicate.test(participation));
    }
}

