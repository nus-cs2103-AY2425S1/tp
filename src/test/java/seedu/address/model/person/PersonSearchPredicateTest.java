package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.util.FieldQuery;
import seedu.address.logic.commands.util.SearchField;
import seedu.address.testutil.PersonBuilder;





public class PersonSearchPredicateTest {
    @Test
    public void equals() {
        List<FieldQuery> firstPredicateKeywordList = List.of(
                new FieldQuery(SearchField.NAME, "name"));
        List<FieldQuery> secondPredicateKeywordList = List.of(
                new FieldQuery(SearchField.NAME, "name"),
                new FieldQuery(SearchField.LOCATION, "location"));

        PersonSearchPredicate firstPredicate = new PersonSearchPredicate(firstPredicateKeywordList);
        PersonSearchPredicate secondPredicate = new PersonSearchPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonSearchPredicate firstPredicateCopy = new PersonSearchPredicate(firstPredicateKeywordList);
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
        PersonSearchPredicate predicate = new PersonSearchPredicate(
                List.of(
                        new FieldQuery(SearchField.NAME, "name"))
        );
        assertTrue(predicate.test(new PersonBuilder().withName("name").build()));

        // Multiple fields and keywords
        predicate = new PersonSearchPredicate(
                List.of(
                        new FieldQuery(SearchField.NAME, "name"),
                        new FieldQuery(SearchField.LOCATION, "location"))
        );
        assertTrue(predicate.test(new PersonBuilder().withName("name").withAddress("location").build()));

        // Only one matching keyword
        predicate = new PersonSearchPredicate(
                List.of(
                        new FieldQuery(SearchField.NAME, "name"),
                        new FieldQuery(SearchField.LOCATION, "location"))
        );
        assertFalse(predicate.test(new PersonBuilder().withName("name").build()));

        // Mixed-case keywords
        predicate = new PersonSearchPredicate(
                List.of(
                        new FieldQuery(SearchField.NAME, "nAme"),
                        new FieldQuery(SearchField.LOCATION, "locaTiOn"))
        );
        assertTrue(predicate.test(new PersonBuilder().withName("name").withAddress("location").build()));

    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords: should be true as fields function as a filter
        // Therefore this means there are no filters
        PersonSearchPredicate predicate = new PersonSearchPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonSearchPredicate(
                List.of(
                        new FieldQuery(SearchField.NAME, "name")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new PersonSearchPredicate(
                List.of(
                        new FieldQuery(SearchField.NAME, "name"),
                        new FieldQuery(SearchField.PHONE, "12345"),
                        new FieldQuery(SearchField.EMAIL, "alice@email.com"),
                        new FieldQuery(SearchField.LOCATION, "Main Street"))
        );
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        PersonSearchPredicate predicate = new PersonSearchPredicate(
                List.of(
                        new FieldQuery(SearchField.NAME, "name"),
                        new FieldQuery(SearchField.PHONE, "12345"),
                        new FieldQuery(SearchField.EMAIL, "alice@email.com"),
                        new FieldQuery(SearchField.LOCATION, "Main Street"))
        );

        String expected = "NAME: name PHONE: 12345 EMAIL: alice@email.com LOCATION: Main Street";
        assertEquals(expected, predicate.toString());
    }

}
