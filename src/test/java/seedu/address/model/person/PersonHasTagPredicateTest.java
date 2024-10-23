package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HIGH_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOW_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MEDIUM_RISK;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class PersonHasTagPredicateTest {
    private PersonHasFeaturePredicate highTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), null);
    private PersonHasFeaturePredicate lowTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_LOW_RISK), null);

    private PersonHasFeaturePredicate mediumTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_MEDIUM_RISK), null);

    private PersonHasFeaturePredicate phoneAndTagPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), new Phone(ALICE.getPhone().value));

    @Test
    public void equals() {

        // same object -> returns true
        assertTrue(highTagOnlyPredicate.equals(highTagOnlyPredicate));

        // same values -> returns true
        PersonHasFeaturePredicate firstPredicateCopy =
              new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), null);
        assertTrue(highTagOnlyPredicate.equals(firstPredicateCopy));

        //same values for tag and phone
        assertTrue(phoneAndTagPredicate.equals(phoneAndTagPredicate));

        // different types -> returns false
        assertFalse(mediumTagOnlyPredicate.equals(1));

        // null -> returns false
        assertFalse(lowTagOnlyPredicate.equals(null));

        // different person -> returns false
        assertFalse(highTagOnlyPredicate.equals(lowTagOnlyPredicate));
    }

    @Test
    public void test_personHasTags_returnsTrue() {
        // One tag
        assertTrue(highTagOnlyPredicate.test(ALICE));


        PersonHasFeaturePredicate phoneAndTagPredicate =
              new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), new Phone(ALICE.getPhone().value));
        assertTrue(phoneAndTagPredicate.test(ALICE));



    }

}
