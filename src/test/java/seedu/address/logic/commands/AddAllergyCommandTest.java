package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AddAllergyCommand.MESSAGE_DUPLICATE_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;

public class AddAllergyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullNricAndNullAllergies_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAllergyCommand(null, null));
    }

    @Test
    public void execute_noNricFound() throws Exception {
        // ensures CommandException is thrown when provided with Nric that is not in addressbook
        Nric nric = new Nric("T1111111F");
        Set<Allergy> allergies = ALICE.getAllergies();
        AddAllergyCommand command = new AddAllergyCommand(nric, allergies);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_addAllergy_success() throws Exception {
        // ensures CommandException is thrown when provided with Nric that is not in addressbook
        Nric nric = ALICE.getNric();
        String allergy = "Peanut allergy";
        Set<Allergy> allergies = new HashSet<>();
        allergies.add(new Allergy(allergy));

        Set<Allergy> expectedTags = new HashSet<>();
        expectedTags.add(new Allergy(allergy));
        expectedTags.addAll(ALICE.getAllergies());

        AddAllergyCommand command = new AddAllergyCommand(nric, allergies);
        command.execute(model);
        Person alice = model.fetchPersonIfPresent(new NricMatchesPredicate(ALICE.getNric()))
                .orElse(null);
        assertEquals(expectedTags, alice.getAllergies());
    }

    @Test
    public void execute_addAllergy_failDuplicate() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(ALICE);
        Nric nric = ALICE.getNric();

        Set<Allergy> duplicateAllergies = new HashSet<>(ALICE.getAllergies());
        AddAllergyCommand command = new AddAllergyCommand(nric, duplicateAllergies);

        Allergy firstAllergy = ALICE.getAllergies().iterator().next();
        String expectedMessage = String.format(MESSAGE_DUPLICATE_ALLERGY, firstAllergy.allergyName);

        assertCommandFailure(command, model, expectedMessage);
    }


    @Test
    public void equals() {
        Nric aliceNric = ALICE.getNric();
        Set<Allergy> aliceTags = ALICE.getAllergies();
        Nric bobNric = BOB.getNric();
        Set<Allergy> bobTags = BOB.getAllergies();
        AddAllergyCommand addAliceAllergyCommand = new AddAllergyCommand(aliceNric, aliceTags);
        AddAllergyCommand addBobCommand = new AddAllergyCommand(bobNric, bobTags);
        // same object -> returns true
        assertTrue(addAliceAllergyCommand.equals(addAliceAllergyCommand));

        // same values -> returns true
        AddAllergyCommand addAliceAllergyCommandCopy = new AddAllergyCommand(aliceNric, aliceTags);
        assertTrue(addAliceAllergyCommand.equals(addAliceAllergyCommandCopy));

        // different types -> returns false
        assertFalse(addAliceAllergyCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceAllergyCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceAllergyCommand.equals(addBobCommand));
    }
}
