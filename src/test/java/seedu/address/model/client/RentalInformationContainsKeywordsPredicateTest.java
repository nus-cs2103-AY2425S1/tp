package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.testutil.PersonBuilder;

public class RentalInformationContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RentalInformationContainsKeywordsPredicate firstPredicate = new RentalInformationContainsKeywordsPredicate(
                firstPredicateKeywordList);
        RentalInformationContainsKeywordsPredicate secondPredicate = new RentalInformationContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RentalInformationContainsKeywordsPredicate firstPredicateCopy = new RentalInformationContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_rentalInformationContainsKeywords_returnsTrue() {
        RentalInformation rentalInformation1 = new RentalInformation(
                "address1",
                "01/11/2024",
                "30/11/2024",
                "1",
                "1000",
                "3000",
                "Alice");

        RentalInformation rentalInformation2 = new RentalInformation(
                "address2",
                "01/12/2024",
                "30/12/2024",
                "1",
                "1500",
                "1500",
                "Bob;Carol");

        RentalInformation rentalInformation3 = new RentalInformation("address3",
                "01/11/2024",
                "30/11/2025",
                "1",
                "500",
                "2000",
                "Carol");

        // One keyword
        RentalInformationContainsKeywordsPredicate predicate = new RentalInformationContainsKeywordsPredicate(
                Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withRentalInformation(rentalInformation1).build()));

        // Multiple keywords
        predicate = new RentalInformationContainsKeywordsPredicate(
                Arrays.asList("Alice", "01/11/2024", "3000"));
        assertTrue(predicate.test(new PersonBuilder().withRentalInformation(rentalInformation1).build()));

        // Multiple RentalInformation
        predicate = new RentalInformationContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withRentalInformation(
                rentalInformation1, rentalInformation2).build()));

        // Mixed-case keywords
        predicate = new RentalInformationContainsKeywordsPredicate(Arrays.asList("cARol", "AddReSS3"));
        assertTrue(predicate.test(new PersonBuilder().withRentalInformation(rentalInformation3).build()));
    }

    @Test
    public void test_rentalInformationDoesNotContainKeywords_returnsFalse() {
        RentalInformation rentalInformation1 = new RentalInformation(
                "address1",
                "01/11/2024",
                "30/11/2024",
                "1",
                "1000",
                "3000",
                "Alice");

        RentalInformation rentalInformation2 = new RentalInformation(
                "address2",
                "01/12/2024",
                "30/12/2024",
                "1",
                "1500",
                "1500",
                "Bob;Carol");

        RentalInformation rentalInformation3 = new RentalInformation("address3",
                "01/11/2024",
                "30/11/2025",
                "1",
                "500",
                "2000",
                "Carol");

        // Zero keywords
        RentalInformationContainsKeywordsPredicate predicate = new RentalInformationContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withRentalInformation(rentalInformation1).build()));

        // Non-matching keyword
        predicate = new RentalInformationContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withRentalInformation(rentalInformation1).build()));

        // Keywords match name, phone and email, but does not match RentalInformation
        predicate = new RentalInformationContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withRentalInformation(rentalInformation3).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        RentalInformationContainsKeywordsPredicate predicate = new RentalInformationContainsKeywordsPredicate(
                keywords);

        String expected = RentalInformationContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
