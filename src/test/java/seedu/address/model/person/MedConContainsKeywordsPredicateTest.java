package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;



public class MedConContainsKeywordsPredicateTest {
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MedConContainsKeywordsPredicate firstPredicate =
                new MedConContainsKeywordsPredicate(firstPredicateKeywordList);
        MedConContainsKeywordsPredicate secondPredicate =
                new MedConContainsKeywordsPredicate(secondPredicateKeywordList);

        //same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        //same values -> returns true
        MedConContainsKeywordsPredicate firstPredicateCopy =
                new MedConContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        //different types -> returns false
        assertFalse(firstPredicate.equals(1));

        //null -> returns false
        assertFalse(firstPredicate.equals(null));

        //different medical conditions -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_medConContainsKeywords_returnsTrue() {
        Person firstPersonToTest = new PersonBuilder().withMedCons("diabetes", "lung cancer").build();
        Person secondPersonToTest = new PersonBuilder().withMedCons("lung cancer").build();
        //One keyword
        MedConContainsKeywordsPredicate predicate =
                new MedConContainsKeywordsPredicate(Collections.singletonList("diabetes"));
        assertTrue(predicate.test(firstPersonToTest));

        //Multiple keywords
        predicate = new MedConContainsKeywordsPredicate(Arrays.asList("diabetes", "cancer"));
        assertTrue(predicate.test(secondPersonToTest));

        //Only one matching keyword
        predicate = new MedConContainsKeywordsPredicate(Arrays.asList("diabetes", "hypertension"));
        assertTrue(predicate.test(firstPersonToTest));

        //Mixed-case keywords
        predicate = new MedConContainsKeywordsPredicate(Arrays.asList("DiAbEtEs", "CaNcEr"));
        assertTrue(predicate.test(firstPersonToTest));
    }

    @Test
    public void test_medConDoesNotContainKeywords_returnsFalse() {
        Person firstPersonToTest = new PersonBuilder().withMedCons("diabetes", "hypertension").build();
        Person secondPersonToTest = new PersonBuilder().withName("Raj").withPhone("92345678")
                .withNric("S2345678A").withEmail("raj@email.com")
                .withAddress("123, Jurong West Ave 6, #08-111").withMedCons("insomnia", "diabetes").build();
        //Zero keywords
        MedConContainsKeywordsPredicate predicate =
                new MedConContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(firstPersonToTest));

        //Non-matching keyword
        predicate = new MedConContainsKeywordsPredicate(Arrays.asList("cancer"));
        assertFalse(predicate.test(firstPersonToTest));

        //Keywords match name, nric, phone, email, but does not match medical conditions
        predicate = new MedConContainsKeywordsPredicate(Arrays.asList("Raj", "92345678", "raj@email.com",
                "S2345678A"));
        assertFalse(predicate.test(secondPersonToTest));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("diabetes", "hypertension");
        MedConContainsKeywordsPredicate predicate = new MedConContainsKeywordsPredicate(keywords);
        String expectedString = MedConContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expectedString, predicate.toString());
    }
}
