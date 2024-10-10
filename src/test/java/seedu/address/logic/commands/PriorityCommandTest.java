package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;

public class PriorityCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void equals() {
        Nric firstNric = new Nric(VALID_NRIC_AMY);
        Nric secondNric = new Nric(VALID_NRIC_BOB);
        Priority firstPriority = new Priority(VALID_PRIORITY_AMY);
        Priority secondPriority = new Priority(VALID_PRIORITY_BOB);

        PriorityCommand firstPriorityCommand = new PriorityCommand(firstNric, firstPriority);
        PriorityCommand secondPriorityCommand = new PriorityCommand(secondNric, secondPriority);

        // same object -> returns true
        assertTrue(firstPriorityCommand.equals(firstPriorityCommand));

        // same value -> returns true
        PriorityCommand firstPriorityCommandCopy = new PriorityCommand(firstNric, firstPriority);
        assertTrue(firstPriorityCommand.equals(firstPriorityCommandCopy));

        // different types -> returns false
        assertFalse(firstPriorityCommand.equals(1));

        // null -> returns false
        assertFalse(firstPriorityCommand.equals(null));

        // different person -> returns false
        assertFalse(firstPriorityCommand.equals(secondPriorityCommand));
    }

    @Test
    public void execute_noPersonFound() {
        // ensures CommandException is thrown when provided with Nric that is not in addressbook
        Nric nric = new Nric("T1111111F");
        Priority priority = new Priority("HIGH");
        PriorityCommand command = new PriorityCommand(nric, priority);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_changePriority_success() throws Exception {

        Nric aliceNric = new Nric("S1234567A");
        Priority noPriority = new Priority("NONE");
        Priority lowPriority = new Priority("LOW");
        Priority mediumPriority = new Priority("MEDIUM");
        Priority highPriority = new Priority("HIGH");

        // change priority from default "NONE" to "LOW"
        CommandResult firstCommandResult = new PriorityCommand(aliceNric, lowPriority).execute(model);
        Person lowPriorityAlice = model.fetchPersonIfPresent(new NricMatchesPredicate(ALICE.getNric()))
                .orElse(null);

        // assert that the Alice's priority has been changed to "LOW"
        assertEquals(lowPriority, lowPriorityAlice.getPriority());

        // change priority from "LOW to MEDIUM"
        CommandResult secondCommandResult = new PriorityCommand(aliceNric, mediumPriority).execute(model);
        Person mediumPriorityAlice = model.fetchPersonIfPresent(new NricMatchesPredicate(ALICE.getNric()))
                .orElse(null);

        // assert that Alice's priority has been changed to "MEDIUM"
        assertEquals(mediumPriority, mediumPriorityAlice.getPriority());

        // change priority from "MEDIUM" to "HIGH"
        CommandResult thirdCommandResult = new PriorityCommand(aliceNric, highPriority).execute(model);
        Person highPriorityAlice = model.fetchPersonIfPresent(new NricMatchesPredicate(ALICE.getNric()))
                .orElse(null);

        // assert that Alice's priority has been changed to "HIGH"
        assertEquals(highPriority, highPriorityAlice.getPriority());

        // change priority from "HIGH" to "HIGH"
        CommandResult fourthCommandResult = new PriorityCommand(aliceNric, highPriority).execute(model);
        Person secondHighPriorityAlice = model.fetchPersonIfPresent(new NricMatchesPredicate(ALICE.getNric()))
                .orElse(null);

        // assert that no exception is thrown and Alice's priority is still "HIGH"
        assertEquals(highPriority, highPriorityAlice.getPriority());

        // change priority from "HIGH" to "NONE"
        CommandResult fifthCommandResult = new PriorityCommand(aliceNric, noPriority).execute(model);
        Person noPriorityAlice = model.fetchPersonIfPresent(new NricMatchesPredicate(ALICE.getNric()))
                .orElse(null);

        // assert that Alice's priority has been removed and set to NONE
        assertEquals(noPriority, noPriorityAlice.getPriority());
    }
}
