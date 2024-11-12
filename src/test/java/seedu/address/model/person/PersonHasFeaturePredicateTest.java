package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HIGH_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOW_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MEDIUM_RISK;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class PersonHasFeaturePredicateTest {

    private PersonHasFeaturePredicate highTagOnlyPredicate =
            new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), null, null, null, null);
    private PersonHasFeaturePredicate lowTagOnlyPredicate =
            new PersonHasFeaturePredicate(new Tag(VALID_TAG_LOW_RISK), null, null, null, null);

    private PersonHasFeaturePredicate mediumTagOnlyPredicate =
            new PersonHasFeaturePredicate(new Tag(VALID_TAG_MEDIUM_RISK), null, null, null, null);

    private PersonHasFeaturePredicate phoneAndTagPredicate =
            new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), new Phone(ALICE.getPhone().value),
                    null, null, null);

    private PersonHasFeaturePredicate emailAndAddressPredicate =
            new PersonHasFeaturePredicate(null, null, ALICE.getEmail(), ALICE.getAddress(), null);



    @Test
    public void test_allergiesFilterIsNull() {
        // Arrange: Person with allergies.
        Allergy allergy1 = new Allergy("Peanut");
        Set<Allergy> personAllergies = new HashSet<>();
        personAllergies.add(allergy1);

        // Act: Method call with allergies filter set to null.
        PersonHasFeaturePredicate predicate = new PersonHasFeaturePredicate(null, null, null, null, null);

        // Assert: The method should return true because allergies filter is null.
        boolean result = predicate.isAllergyTrue(new ArrayList<>(personAllergies));
        assertTrue(result, "Method should return true when allergies filter is null.");
    }

    @Test
    public void test_allergiesFilterIsEmpty() {
        // Arrange: Person with allergies.
        Allergy allergy1 = new Allergy("Peanut");
        Set<Allergy> personAllergies = new HashSet<>();
        personAllergies.add(allergy1);

        // Act: Method call with allergies filter set to an empty list.
        PersonHasFeaturePredicate predicate = new PersonHasFeaturePredicate(null, null, null, null, new ArrayList<>());

        // Assert: The method should return true because allergies filter is empty.
        boolean result = predicate.isAllergyTrue(new ArrayList<>(personAllergies));
        assertTrue(result, "Method should return true when allergies filter is empty.");
    }

    @Test
    public void test_allergiesFilterMatchesPersonAllergies() {
        // Arrange: Person with allergies.
        Allergy allergy1 = new Allergy("Peanut");
        Allergy allergy2 = new Allergy("Dairy");
        Set<Allergy> personAllergies = new HashSet<>();
        personAllergies.add(allergy1);
        personAllergies.add(allergy2);

        // Create filter that matches "Peanut"
        List<Allergy> allergiesFilter = new ArrayList<>();
        allergiesFilter.add(new Allergy("Peanut"));

        // Act: Method call with allergies filter that matches the person's allergy.
        PersonHasFeaturePredicate predicate = new PersonHasFeaturePredicate(null, null, null, null, allergiesFilter);

        // Assert: The method should return true because "Peanut" matches one of the person's allergies.
        boolean result = predicate.isAllergyTrue(new ArrayList<>(personAllergies));
        assertTrue(result, "Method should return true when allergies filter matches person's allergies.");
    }

    @Test
    public void test_allergiesFilterDoesNotMatchPersonAllergies() {
        // Arrange: Person with allergies.
        Allergy allergy1 = new Allergy("Peanut");
        Allergy allergy2 = new Allergy("Dairy");
        Set<Allergy> personAllergies = new HashSet<>();
        personAllergies.add(allergy1);
        personAllergies.add(allergy2);

        // Create filter that does not match any of the person's allergies.
        List<Allergy> allergiesFilter = new ArrayList<>();
        allergiesFilter.add(new Allergy("Shellfish"));

        // Act: Method call with allergies filter that does not match person's allergies.
        PersonHasFeaturePredicate predicate = new PersonHasFeaturePredicate(null, null, null, null, allergiesFilter);

        // Assert: The method should return false because none of the allergies match.
        boolean result = predicate.isAllergyTrue(new ArrayList<>(personAllergies));
        assertFalse(result, "Method should return false when allergies filter does not match person's allergies.");
    }

    @Test
    public void test_personHasNoAllergies() {
        // Arrange: Person with no allergies.
        Set<Allergy> personAllergies = new HashSet<>();

        // Create an allergy filter that contains "Peanut".
        List<Allergy> allergiesFilter = new ArrayList<>();
        allergiesFilter.add(new Allergy("Peanut"));

        // Act: Method call with an allergy filter, but the person has no allergies.
        PersonHasFeaturePredicate predicate = new PersonHasFeaturePredicate(null, null, null, null, allergiesFilter);

        // Assert: The method should return false because the person has no allergies.
        boolean result = predicate.isAllergyTrue(new ArrayList<>(personAllergies));
        assertFalse(result, "Method should return false when the person has no allergies.");
    }


    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(highTagOnlyPredicate.equals(highTagOnlyPredicate));

        // same values -> returns true
        PersonHasFeaturePredicate firstPredicateCopy =
              new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), null, null, null, null);
        //assertTrue(highTagOnlyPredicate.equals(firstPredicateCopy));

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
                      new Phone(ALICE.getPhone().value), null, null, null);
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
                        null, new Email("wrongemail@xyz.com"), new Address("wrong address"), null);
        assertFalse(wrongEmailPredicate.test(ALICE));

        PersonHasFeaturePredicate wrongAddressPredicate =
                new PersonHasFeaturePredicate(null, null, ALICE.getEmail(), new Address("wrong address"), null);
        assertFalse(wrongAddressPredicate.test(ALICE));

        PersonHasFeaturePredicate wrongEmailAndAddressPredicate =
                new PersonHasFeaturePredicate(null, null, new Email("wrongemail@xyz.com"), ALICE.getAddress(), null);
        assertFalse(wrongEmailAndAddressPredicate.test(ALICE));
    }

    @Test
    public void equals_withEmailAndAddress() {
        // Same values -> returns true
        PersonHasFeaturePredicate emailAndAddressCopy =
                new PersonHasFeaturePredicate(null, null, ALICE.getEmail(), ALICE.getAddress(), null);
        assertTrue(emailAndAddressPredicate.equals(emailAndAddressCopy));

        // Different email -> returns false
        PersonHasFeaturePredicate differentEmailPredicate =
                new PersonHasFeaturePredicate(null, null, new Email("wrongemail@xyz.com"), ALICE.getAddress(), null);
        assertFalse(emailAndAddressPredicate.equals(differentEmailPredicate));

        // Different address -> returns false
        PersonHasFeaturePredicate differentAddressPredicate =
                new PersonHasFeaturePredicate(null, null, ALICE.getEmail(), new Address("wrong address"), null);
        assertFalse(emailAndAddressPredicate.equals(differentAddressPredicate));

        // Different email and address -> returns false
        PersonHasFeaturePredicate differentEmailAndAddressPredicate =
                new PersonHasFeaturePredicate(null,
                        null, new Email("wrongemail@xyz.com"), new Address("wrong address"), null);
        assertFalse(emailAndAddressPredicate.equals(differentEmailAndAddressPredicate));
    }

}
