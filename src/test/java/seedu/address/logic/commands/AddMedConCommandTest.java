package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDCON_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDCON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;

public class AddMedConCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void equals() {
        Nric firstNric = new Nric(VALID_NRIC_AMY);
        Nric secondNric = new Nric(VALID_NRIC_BOB);
        MedCon firstMedCon = new MedCon(VALID_MEDCON_AMY);
        MedCon secondMedCon = new MedCon(VALID_MEDCON_BOB);

        AddMedConCommand firstMedConCommand = new AddMedConCommand(firstNric, firstMedCon);
        AddMedConCommand secondMedConCommand = new AddMedConCommand(secondNric, secondMedCon);

        // same object -> returns true
        assertTrue(firstMedConCommand.equals(firstMedConCommand));

        // same value -> returns true
        AddMedConCommand firstMedConCommandCopy = new AddMedConCommand(firstNric, firstMedCon);
        assertTrue(firstMedConCommand.equals(firstMedConCommandCopy));

        // different types -> returns false
        assertFalse(firstMedConCommand.equals(1));

        // null -> returns false
        assertFalse(firstMedConCommand.equals(null));

        // different person -> returns false
        assertFalse(firstMedConCommand.equals(secondMedConCommand));
    }

    @Test
    public void execute_noPersonFound() {
        // ensures CommandException is thrown when provided with Nric that is not in addressbook
        Nric nric = new Nric("T1111111F");
        MedCon medCon = new MedCon("cancer");
        AddMedConCommand command = new AddMedConCommand(nric, medCon);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_addMedCon_success() throws Exception {
        // Setup data
        Nric aliceNric = ALICE.getNric();
        MedCon initialMedCon = new MedCon("Diabetes");
        MedCon emptyMedCon = new MedCon(""); // Empty MedCon to represent optional state
        MedCon updatedMedCon = new MedCon("Hypertension");

        // Create and execute the first command to add an initial medical condition
        AddMedConCommand addMedConCommand = new AddMedConCommand(aliceNric, initialMedCon);
        CommandResult commandResult = addMedConCommand.execute(model);

        // Fetch the updated person and verify that the medical condition was added
        Person updatedAlice = model.fetchPersonIfPresent(new NricMatchesPredicate(aliceNric))
                .orElseThrow(() -> new AssertionError("Person not found"));
        assertEquals(initialMedCon, updatedAlice.getMedCon());

        // Create and execute a command to add an empty medical condition (representing optional)
        AddMedConCommand addEmptyMedConCommand = new AddMedConCommand(aliceNric, emptyMedCon);
        CommandResult emptyCommandResult = addEmptyMedConCommand.execute(model);

        // Fetch the updated person again and verify that the medical condition is now empty
        Person aliceWithoutMedCon = model.fetchPersonIfPresent(new NricMatchesPredicate(aliceNric))
                .orElseThrow(() -> new AssertionError("Person not found"));
        assertEquals(emptyMedCon, aliceWithoutMedCon.getMedCon());

        // Create and execute a command to update the medical condition to a new value
        AddMedConCommand updateMedConCommand = new AddMedConCommand(aliceNric, updatedMedCon);
        CommandResult updatedCommandResult = updateMedConCommand.execute(model);

        // Fetch the updated person again and verify that the medical condition was updated
        Person secondUpdatedAlice = model.fetchPersonIfPresent(new NricMatchesPredicate(aliceNric))
                .orElseThrow(() -> new AssertionError("Person not found"));
        assertEquals(updatedMedCon, secondUpdatedAlice.getMedCon());
    }

}
