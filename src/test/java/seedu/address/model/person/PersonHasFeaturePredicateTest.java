package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HIGH_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOW_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MEDIUM_RISK;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class PersonHasFeaturePredicateTest {
    private PersonHasFeaturePredicate highTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), null, null, null);
    private PersonHasFeaturePredicate lowTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_LOW_RISK), null, null, null);

    private PersonHasFeaturePredicate mediumTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_MEDIUM_RISK), null, null, null);

    private PersonHasFeaturePredicate phoneAndTagPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), new Phone(ALICE.getPhone().value), null, null);

    private PersonHasFeaturePredicate emailAndAddressPredicate =
            new PersonHasFeaturePredicate(null, null, ALICE.getEmail(), ALICE.getAddress());

    @Test
    public void equals() {

        // same object -> returns true
        assertTrue(highTagOnlyPredicate.equals(highTagOnlyPredicate));

        // same values -> returns true
        PersonHasFeaturePredicate firstPredicateCopy =
              new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), null, null, null);
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
              new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK),
                      new Phone(ALICE.getPhone().value), null, null);
        assertTrue(highTagOnlyPredicate.test(ALICE));

    }

    @Test
    public void test_personHasEmailAndAddress_returnsTrue() {
        // Both email and address match
        assertTrue(emailAndAddressPredicate.test(ALICE));
    }

    @Test
    public void test_personHasEmailAndAddress_returnsFalse() {
        // Different email, same address
        PersonHasFeaturePredicate wrongEmailPredicate =
                new PersonHasFeaturePredicate(null,
                        null, new Email("wrongemail@xyz.com"), new Address("wrong address"));
        assertFalse(wrongEmailPredicate.test(ALICE));

        PersonHasFeaturePredicate wrongAddressPredicate =
                new PersonHasFeaturePredicate(null, null, ALICE.getEmail(), new Address("wrong address"));
        assertFalse(wrongAddressPredicate.test(ALICE));

        PersonHasFeaturePredicate wrongEmailAndAddressPredicate =
                new PersonHasFeaturePredicate(null, null, new Email("wrongemail@xyz.com"), ALICE.getAddress());
        assertFalse(wrongEmailAndAddressPredicate.test(ALICE));
    }

    @Test
    public void equals_withEmailAndAddress() {
        // Same values -> returns true
        PersonHasFeaturePredicate emailAndAddressCopy =
                new PersonHasFeaturePredicate(null, null, ALICE.getEmail(), ALICE.getAddress());
        assertTrue(emailAndAddressPredicate.equals(emailAndAddressCopy));

        // Different email -> returns false
        PersonHasFeaturePredicate differentEmailPredicate =
                new PersonHasFeaturePredicate(null, null, new Email("wrongemail@xyz.com"), ALICE.getAddress());
        assertFalse(emailAndAddressPredicate.equals(differentEmailPredicate));

        // Different address -> returns false
        PersonHasFeaturePredicate differentAddressPredicate =
                new PersonHasFeaturePredicate(null, null, ALICE.getEmail(), new Address("wrong address"));
        assertFalse(emailAndAddressPredicate.equals(differentAddressPredicate));

        // Different email and address -> returns false
        PersonHasFeaturePredicate differentEmailAndAddressPredicate =
                new PersonHasFeaturePredicate(null,
                        null, new Email("wrongemail@xyz.com"), new Address("wrong address"));
        assertFalse(emailAndAddressPredicate.equals(differentEmailAndAddressPredicate));
    }

}
