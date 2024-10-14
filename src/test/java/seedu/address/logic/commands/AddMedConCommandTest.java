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

import java.util.HashSet;
import java.util.Set;

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
        Set<MedCon> firstMedConSet = new HashSet<>();
        firstMedConSet.add(new MedCon(VALID_MEDCON_AMY));
        Set<MedCon> secondMedConSet = new HashSet<>();
        secondMedConSet.add(new MedCon(VALID_MEDCON_BOB));

        AddMedConCommand firstMedConCommand = new AddMedConCommand(firstNric, firstMedConSet);
        AddMedConCommand secondMedConCommand = new AddMedConCommand(secondNric, secondMedConSet);


        // same object -> returns true
        assertTrue(firstMedConCommand.equals(firstMedConCommand));

        // same value -> returns true
        AddMedConCommand firstMedConCommandCopy = new AddMedConCommand(firstNric, firstMedConSet);
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
        Set<MedCon> medConSet = new HashSet<>();
        medConSet.add(new MedCon("Cancer"));
        AddMedConCommand command = new AddMedConCommand(nric, medConSet);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_addMedCon_success() throws Exception {
        // Setup data
        Nric aliceNric = ALICE.getNric();
        Set<MedCon> initialMedConSet = new HashSet<>();
        initialMedConSet.add(new MedCon(VALID_MEDCON_AMY));

        Set<MedCon> updatedMedConSet = new HashSet<>();
        updatedMedConSet.add(new MedCon(VALID_MEDCON_BOB));

        // Create and execute the first command to add an initial medical condition
        AddMedConCommand addMedConCommand = new AddMedConCommand(aliceNric, initialMedConSet);
        CommandResult commandResult = addMedConCommand.execute(model);

        // Fetch the updated person and verify that the medical condition was added
        Person updatedAlice = model.fetchPersonIfPresent(new NricMatchesPredicate(aliceNric))
                .orElseThrow(() -> new AssertionError("Person not found"));
        assertEquals(initialMedConSet, updatedAlice.getMedCons());
    }

    @Test
    public void execute_duplicateMedCon_throwsCommandException() {
        // Setup data
        Nric aliceNric = ALICE.getNric();
        Set<MedCon> initialMedConSet = new HashSet<>();
        initialMedConSet.add(new MedCon(VALID_MEDCON_AMY));

        // Add initial medical condition
        AddMedConCommand addMedConCommand = new AddMedConCommand(aliceNric, initialMedConSet);
        try {
            addMedConCommand.execute(model);
        } catch (CommandException e) {
            throw new AssertionError("Initial addMedConCommand should succeed.", e);
        }

        // Attempt to add the same medical condition again to trigger duplicate check
        Set<MedCon> duplicateMedConSet = new HashSet<>();
        duplicateMedConSet.add(new MedCon(VALID_MEDCON_AMY));
        AddMedConCommand addDuplicateMedConCommand = new AddMedConCommand(aliceNric, duplicateMedConSet);

        // Ensure CommandException is thrown for duplicate medical condition
        assertThrows(CommandException.class, () -> addDuplicateMedConCommand.execute(model),
                "Expected CommandException due to duplicate medical condition.");
    }

}
