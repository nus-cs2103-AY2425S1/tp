package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TempPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TempPredicate firstPredicate = new TempPredicate(firstPredicateKeywordList);
        TempPredicate secondPredicate = new TempPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TempPredicate firstPredicateCopy = new TempPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_anyPerson_returnsFalse() {
        // Person with name
        TempPredicate predicate = new TempPredicate(Collections.singletonList("Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Person with address
        predicate = new TempPredicate(Arrays.asList("Street"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TempPredicate predicate = new TempPredicate(keywords);

        String expected = TempPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
