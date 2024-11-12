package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.TAG_SET_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.TAG_SET_FRIENDS_OWESMONEY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_OWESMONEY;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTaggedWithPredicateTest {
    @Test
    public void test_personTaggedWith_returnsTrue() {
        // One tag
        PersonTaggedWithPredicate predicateOneTag = new PersonTaggedWithPredicate(TAG_SET_FRIENDS);
        assertTrue(predicateOneTag.test(new PersonBuilder().withTags(VALID_TAG_FRIENDS).build()));

        // Multiple tags
        PersonTaggedWithPredicate predicateTwoTags = new PersonTaggedWithPredicate(TAG_SET_FRIENDS_OWESMONEY);
        assertTrue(predicateTwoTags.test(new PersonBuilder().withTags(VALID_TAG_FRIENDS, VALID_TAG_OWESMONEY).build()));
    }

    @Test
    public void test_personTaggedWith_returnsFalse() {
        // Person has zero tags
        PersonTaggedWithPredicate predicatePersonNoTags = new PersonTaggedWithPredicate(TAG_SET_FRIENDS);
        assertFalse(predicatePersonNoTags.test(new PersonBuilder().build()));

        // Non-matching tag
        PersonTaggedWithPredicate predicateDifferentTag = new PersonTaggedWithPredicate(TAG_SET_FRIENDS);
        assertFalse(predicateDifferentTag.test(new PersonBuilder().withTags(VALID_TAG_OWESMONEY).build()));
    }
}
