package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate("Alice", null, null, null, null, null, null);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate("Bob", null, null, null, null, null, null);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate("Alice", null, null, null, null, null, null);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsKeywords_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("Alice", null, null, null, null, null, null);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new PersonContainsKeywordsPredicate(null, null,
                null, null, "cs", null, null);
        assertTrue(predicate.test(new PersonBuilder().withMajor("cs").build()));

        predicate = new PersonContainsKeywordsPredicate(null, null,
                null, "brUdder", null, null, null);
        assertTrue(predicate.test(new PersonBuilder().withRole("brUdder").build()));
    }

    @Test
    public void test_personDoesNotContainKeywords_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("Carol", null, null, null, null, null, null);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new PersonContainsKeywordsPredicate(null, null, null, null, "bza", null, null);
        assertFalse(predicate.test(new PersonBuilder().withMajor("cs").build()));

        predicate = new PersonContainsKeywordsPredicate(null, null, null, "mUdder", null, null, null);
        assertFalse(predicate.test(new PersonBuilder().withRole("brUdder").build()));
    }

    @Test
    public void test_personMatchesSomeFields_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("Alice", null, null, null, "bza", null, null);
        assertTrue(predicate.test(
                new PersonBuilder().withName("Alice").withMajor("bza").withAddress("Queenstown").build()));
    }

    @Test
    public void test_personMatchesAllFields_returnsTrue() {
        List<String> expectedTags = List.of("friends");
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("Alice Pauline", "94351253", "alice@example.com", "mUdder", "bza",
                        "123, Jurong West Ave 6, #08-111", expectedTags);
        assertTrue(predicate.test(TypicalPersons.ALICE));
    }

    @Test
    public void test_personMatchesPartiallyInNameField_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("Ali", null, null, null, null, null, null);
        assertTrue(predicate.test(TypicalPersons.ALICE));
    }

    @Test
    public void test_personMatchesPartiallyInPhoneField_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, "9435", null, null, null, null, null);
        assertTrue(predicate.test(TypicalPersons.ALICE));
    }

    @Test
    public void test_personMatchesPartiallyInEmailField_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, "alice@example.co", null, null, null, null);
        assertTrue(predicate.test(TypicalPersons.ALICE));
    }

    @Test
    public void test_personMatchesFullyInRoleField_returnsTrue() {
        // A partial match is not possible for Role, since parser validation prevents partial roles
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, "brUdder", null, null, null);
        assertTrue(predicate.test(TypicalPersons.BENSON));
    }

    @Test
    public void test_personMatchesFullyInMajorField_returnsTrue() {
        // A partial match is not possible for Major, since parser validation prevents partial majors
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, null, "cs", null, null);
        assertTrue(predicate.test(TypicalPersons.BENSON));
    }

    @Test
    public void test_personMatchesPartiallyInAddressField_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(null, null, null, null, null,
                "10th", null);
        assertTrue(predicate.test(TypicalPersons.DANIEL));
    }

    @Test
    public void test_personMatchesFullyInAddressField_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(null, null, null, null, null,
                "10th street", null);
        assertTrue(predicate.test(TypicalPersons.DANIEL));
    }

    @Test
    public void test_personMatchesFullyInTagsField_returnsTrue() {
        List<String> expectedTags = List.of("friends", "owesMoney");
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, null, null, null, expectedTags);
        assertTrue(predicate.test(TypicalPersons.BENSON));
    }

    @Test
    public void test_personMatchesInOneTag_returnsTrue() {
        List<String> expectedTags = List.of("friends");
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, null, null, null, expectedTags);
        assertTrue(predicate.test(TypicalPersons.ALICE));
        assertTrue(predicate.test(TypicalPersons.BENSON));
        assertTrue(predicate.test(TypicalPersons.DANIEL));
    }

    @Test
    public void test_personMatchManyFields_returnsTrue() {
        List<String> expectedTags = List.of("friends");
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, "brUdder", null, null, expectedTags);
        assertTrue(predicate.test(TypicalPersons.BENSON));
    }

    @Test
    public void toStringMethod() {
        List<String> tags = List.of("friends", "owesMoney");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("Alice", "98765432",
                "alice@example.com", "brUdder", "cs", "123 Street", tags);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName()
                + "{name=Alice, phone=98765432, email=alice@example.com, role=brUdder,"
                + " major=cs, address=123 Street, tags=" + tags + "}";
        assertEquals(expected, predicate.toString());
    }
}
