package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HIGH_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOW_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MEDIUM_RISK;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;



public class PersonHasTagPredicateTest {
    private PersonHasTagPredicate highPredicate =
          new PersonHasTagPredicate(Collections.singletonList(new Tag(VALID_TAG_HIGH_RISK)));
    private PersonHasTagPredicate lowPredicate =
          new PersonHasTagPredicate(Collections.singletonList(new Tag(VALID_TAG_LOW_RISK)));

    private PersonHasTagPredicate mediumPredicate =
          new PersonHasTagPredicate(Collections.singletonList(new Tag(VALID_TAG_MEDIUM_RISK)));

    @Test
    public void equals() {

        // same object -> returns true
        assertTrue(highPredicate.equals(highPredicate));

        // same values -> returns true
        PersonHasTagPredicate firstPredicateCopy =
              new PersonHasTagPredicate(Collections.singletonList(new Tag(VALID_TAG_HIGH_RISK)));
        assertTrue(highPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(mediumPredicate.equals(1));

        // null -> returns false
        assertFalse(lowPredicate.equals(null));

        // different person -> returns false
        assertFalse(highPredicate.equals(lowPredicate));
    }

    @Test
    public void test_personHasTags_returnsTrue() {
        // One tag
        assertTrue(highPredicate.test(ALICE));


        // Multiple TAGS
        PersonHasTagPredicate multipleTagsPredicate =
              new PersonHasTagPredicate(List.of(new Tag(VALID_TAG_LOW_RISK), new Tag(VALID_TAG_HIGH_RISK)));
        assertTrue(highPredicate.test(ALICE));
        assertTrue(highPredicate.test(BENSON));


    }

}
