package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagPredicateTest {

    @Test
    public void test_tagMatches_returnsTrue() {
        TagPredicate predicate = new TagPredicate(Arrays.asList("TP"));
        assertTrue(predicate.test(new PersonBuilder().withTag("TP").build()));
    }

    @Test
    public void test_tagDoesNotMatch_returnsFalse() {
        TagPredicate predicate = new TagPredicate(Arrays.asList("TP"));
        assertFalse(predicate.test(new PersonBuilder().withTag("N").build()));
    }

    @Test
    public void test_tagDifferentCase_returnsTrue() {
        TagPredicate predicate = new TagPredicate(Arrays.asList("tp"));
        assertTrue(predicate.test(new PersonBuilder().withTag("TP").build()));
    }
}

