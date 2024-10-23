package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.predicates.PersonContainsTagsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;


class PersonContainsTagsPredicateTest {

    @Test
    public void equals() {
        Set<Tag> firstPredicateTags = Set.of(new Tag("class"));
        Set<Tag> secondPredicateTags = Set.of(new Tag("class"), new Tag("Name"));

        PersonContainsTagsPredicate firstPredicate = new PersonContainsTagsPredicate(firstPredicateTags);
        PersonContainsTagsPredicate firstPredicateCopy = new PersonContainsTagsPredicate(firstPredicateTags);
        PersonContainsTagsPredicate thirdPredicate = new PersonContainsTagsPredicate(secondPredicateTags);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));

    }

    @Test
    public void test_personContainsTags_returnTrue() {
        Set<Tag> firstPredicateTags = Set.of(new Tag("class"));
        Set<Tag> secondPredicateTags = Set.of(new Tag("class"), new Tag("Name"));

        PersonContainsTagsPredicate firstPredicate = new PersonContainsTagsPredicate(firstPredicateTags);
        PersonContainsTagsPredicate secondPredicate = new PersonContainsTagsPredicate(secondPredicateTags);


        // Person with  matching tags
        assertTrue(firstPredicate.test(new PersonBuilder()
                .withTags("class")
                .build()));
        assertTrue(secondPredicate.test(new PersonBuilder()
                .withTags("class", "Name")
                .build()));
        // Switching tag order does not matter
        assertTrue(secondPredicate.test(new PersonBuilder()
                .withTags("Name", "class")
                .build()));
    }

    @Test
    public void test_personDoesNotContainsTags_returnsFalse() {
        Set<Tag> firstPredicateTags = Set.of(new Tag("class"));
        Set<Tag> secondPredicateTags = Set.of(new Tag("class"), new Tag("Name"));

        PersonContainsTagsPredicate firstPredicate = new PersonContainsTagsPredicate(firstPredicateTags);
        PersonContainsTagsPredicate secondPredicate = new PersonContainsTagsPredicate(secondPredicateTags);

        // Person with no tags
        Person personWithNoTags = new PersonBuilder()
                .build();
        assertFalse(firstPredicate.test(personWithNoTags));
        assertFalse(secondPredicate.test(personWithNoTags));

        // Person with no matching tags
        Person personWithNoMatchingTags = new PersonBuilder()
                .withTags("NoClass")
                .build();
        assertFalse(firstPredicate.test(personWithNoMatchingTags));
        assertFalse(secondPredicate.test(personWithNoMatchingTags));

        // Person with partial matching tag
        Person personOneWithPartialMatchingTags = new PersonBuilder()
                .withTags("class", "NoName")
                .build();
        Person personTwoWithPartialMatchingTags = new PersonBuilder()
                .withTags("name", "NoClass")
                .build();
        assertFalse(secondPredicate.test(personOneWithPartialMatchingTags));
        assertFalse(secondPredicate.test(personTwoWithPartialMatchingTags));
    }

    @Test
    public void toStringMethod() {
        Set<Tag> tags = Set.of(new Tag("class"));
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(tags);

        String expected = PersonContainsTagsPredicate.class.getCanonicalName() + "{tags=" + tags + "}";
        assertEquals(expected, predicate.toString());
    }
}
