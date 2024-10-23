package seedu.ddd.model.contact.common.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.tag.Tag;
import seedu.ddd.testutil.ClientBuilder;


public class ContactContainsTagPredicateTest {

    @Test
    public void equals() {
        Set<Tag> firstTagSet = new HashSet<>(Collections.singletonList(new Tag("first")));
        Set<Tag> secondTagSet = new HashSet<>(Arrays.asList(new Tag("first"), new Tag("second")));

        ContactContainsTagPredicate firstPredicate = new ContactContainsTagPredicate(firstTagSet);
        ContactContainsTagPredicate secondPredicate = new ContactContainsTagPredicate(secondTagSet);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContactContainsTagPredicate firstPredicateCopy = new ContactContainsTagPredicate(firstTagSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tag sets -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagSetContainsTags_returnsTrue() {
        // one tag
        Set<Tag> tagSet = new HashSet<>(Collections.singletonList(new Tag("first")));
        ContactContainsTagPredicate predicate = new ContactContainsTagPredicate(tagSet);
        assertTrue(predicate.test(new ClientBuilder().withTags("first").build()));

        // multiple tags
        tagSet = new HashSet<>(Arrays.asList(new Tag("first"), new Tag("second")));
        predicate = new ContactContainsTagPredicate(tagSet);
        assertTrue(predicate.test(new ClientBuilder().withTags("first", "second").build()));

        // Only one matching tag
        tagSet = new HashSet<>(Arrays.asList(new Tag("second"), new Tag("third")));
        predicate = new ContactContainsTagPredicate(tagSet);
        assertTrue(predicate.test(new ClientBuilder().withTags("first", "second").build()));

        // Mixed-case tags
        tagSet = new HashSet<>(Arrays.asList(new Tag("sECoNd"), new Tag("ThIrd")));
        predicate = new ContactContainsTagPredicate(tagSet);
        assertTrue(predicate.test(new ClientBuilder().withTags("second", "third").build()));
    }

    @Test
    public void test_tagSetDoesNotContainTags_returnsFalse() {
        // Zero Tags
        Set<Tag> tagSet = new HashSet<>(Collections.emptyList());
        ContactContainsTagPredicate predicate = new ContactContainsTagPredicate(tagSet);
        assertFalse(predicate.test(new ClientBuilder().withTags("first").build()));

        // none-matching tags
        tagSet = new HashSet<>(Arrays.asList(new Tag("first")));
        predicate = new ContactContainsTagPredicate(tagSet);
        assertFalse(predicate.test(new ClientBuilder().withTags("second", "third").build()));
    }

    @Test
    public void toStringMethod() {
        Set<Tag> tagSet = new HashSet<>(Arrays.asList(new Tag("first"), new Tag("second")));
        ContactContainsTagPredicate predicate = new ContactContainsTagPredicate(tagSet);

        String expected = ContactContainsTagPredicate.class.getCanonicalName() + "{tagSet=" + tagSet + "}";
        assertEquals(expected, predicate.toString());
    }
}
