package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;

public class DelAllergyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Nric firstNric = new Nric(VALID_NRIC_AMY);
        Nric secondNric = new Nric(VALID_NRIC_BOB);
        Set<Allergy> firstAllergySet = new HashSet<>();
        firstAllergySet.add(new Allergy(VALID_ALLERGY_AMY));
        Set<Allergy> secondAllergySet = new HashSet<>();
        secondAllergySet.add(new Allergy(VALID_ALLERGY_BOB));

        DelAllergyCommand firstAllergyCommand = new DelAllergyCommand(firstNric, firstAllergySet);
        DelAllergyCommand secondAllergyCommand = new DelAllergyCommand(secondNric, secondAllergySet);

        // same object -> returns true
        assertTrue(firstAllergyCommand.equals(firstAllergyCommand));

        // same value -> returns true
        DelAllergyCommand firstAllergyCommandCopy = new DelAllergyCommand(firstNric, firstAllergySet);
        assertTrue(firstAllergyCommand.equals(firstAllergyCommandCopy));

        // different types -> returns false
        assertFalse(firstAllergyCommand.equals(1));

        // null -> returns false
        assertFalse(firstAllergyCommand.equals(null));

        // different person -> returns false
        assertFalse(firstAllergyCommand.equals(secondAllergyCommand));
    }

    @Test
    public void execute_noPersonFound() {
        // ensures CommandException is thrown when provided with Nric that is not in address book
        Nric nric = new Nric("T1111111F");
        Set<Allergy> allergySet = new HashSet<>();
        allergySet.add(new Allergy("Peanuts"));
        DelAllergyCommand command = new DelAllergyCommand(nric, allergySet);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_deleteAllergy_success() throws Exception {
        // Setup data
        Nric aliceNric = ALICE.getNric();
        Set<Allergy> initialAllergySet = new HashSet<>();
        initialAllergySet.add(new Allergy(VALID_ALLERGY_AMY));

        // Add initial allergy
        Person updatedAlice = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getNric(), ALICE.getAddress(), ALICE.getDateOfBirth(),
                ALICE.getGender(), initialAllergySet, ALICE.getPriority(), ALICE.getAppointments(),
                ALICE.getMedCons());
        model.setPerson(ALICE, updatedAlice);

        // Create and execute delete command
        Set<Allergy> allergyToDelete = new HashSet<>();
        allergyToDelete.add(new Allergy(VALID_ALLERGY_AMY));
        DelAllergyCommand delAllergyCommand = new DelAllergyCommand(aliceNric, allergyToDelete);
        CommandResult commandResult = delAllergyCommand.execute(model);

        // Verify result
        Person aliceWithoutAllergy = model.fetchPersonIfPresent(new NricMatchesPredicate(aliceNric))
                .orElseThrow(() -> new AssertionError("Person not found"));
        assertEquals(Collections.emptySet(), aliceWithoutAllergy.getAllergies());
    }

    @Test
    public void execute_allergyNotFound_throwsCommandException() {
        // Setup data
        Nric aliceNric = ALICE.getNric();
        Set<Allergy> initialAllergySet = new HashSet<>();
        initialAllergySet.add(new Allergy(VALID_ALLERGY_AMY));

        // Add initial allergy
        Person updatedAlice = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getNric(), ALICE.getAddress(), ALICE.getDateOfBirth(),
                ALICE.getGender(), initialAllergySet, ALICE.getPriority(), ALICE.getAppointments(),
                ALICE.getMedCons());
        model.setPerson(ALICE, updatedAlice);

        // Create delete command with a non-existent allergy
        Set<Allergy> allergyToDelete = new HashSet<>();
        allergyToDelete.add(new Allergy("NonExistentAllergy"));
        DelAllergyCommand delAllergyCommand = new DelAllergyCommand(aliceNric, allergyToDelete);

        // Expect CommandException due to non-existent allergy
        assertThrows(CommandException.class, () -> delAllergyCommand.execute(model),
                "Expected CommandException due to non-existent allergy.");
    }

}
