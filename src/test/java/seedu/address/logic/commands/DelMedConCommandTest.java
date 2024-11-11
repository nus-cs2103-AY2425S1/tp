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

import java.util.Collections;
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

public class DelMedConCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Nric firstNric = new Nric(VALID_NRIC_AMY);
        Nric secondNric = new Nric(VALID_NRIC_BOB);
        Set<MedCon> firstMedConSet = new HashSet<>();
        firstMedConSet.add(new MedCon(VALID_MEDCON_AMY));
        Set<MedCon> secondMedConSet = new HashSet<>();
        secondMedConSet.add(new MedCon(VALID_MEDCON_BOB));

        DelMedConCommand firstMedConCommand = new DelMedConCommand(firstNric, firstMedConSet);
        DelMedConCommand secondMedConCommand = new DelMedConCommand(secondNric, secondMedConSet);

        // same object -> returns true
        assertTrue(firstMedConCommand.equals(firstMedConCommand));

        // same value -> returns true
        DelMedConCommand firstMedConCommandCopy = new DelMedConCommand(firstNric, firstMedConSet);
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
        DelMedConCommand command = new DelMedConCommand(nric, medConSet);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_deleteMedCon_success() throws Exception {
        // Setup data
        Nric aliceNric = ALICE.getNric();
        Set<MedCon> initialMedConSet = new HashSet<>();
        initialMedConSet.add(new MedCon(VALID_MEDCON_AMY));

        // Add initial medical condition
        Person updatedAlice = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getNric(), ALICE.getAddress(), ALICE.getDateOfBirth(),
                ALICE.getGender(), ALICE.getAllergies(), ALICE.getPriority(), ALICE.getAppointments(),
                initialMedConSet);
        model.setPerson(ALICE, updatedAlice);

        // Create and execute delete command
        Set<MedCon> medConToDelete = new HashSet<>();
        medConToDelete.add(new MedCon(VALID_MEDCON_AMY));
        DelMedConCommand delMedConCommand = new DelMedConCommand(aliceNric, medConToDelete);
        CommandResult commandResult = delMedConCommand.execute(model);

        // Verify result
        Person aliceWithoutMedCon = model.fetchPersonIfPresent(new NricMatchesPredicate(aliceNric))
                .orElseThrow(() -> new AssertionError("Person not found"));
        assertEquals(Collections.emptySet(), aliceWithoutMedCon.getMedCons());
    }

    @Test
    public void execute_medConNotFound_throwsCommandException() {
        // Setup data
        Nric aliceNric = ALICE.getNric();
        Set<MedCon> initialMedConSet = new HashSet<>();
        initialMedConSet.add(new MedCon(VALID_MEDCON_AMY));

        // Add initial medical condition
        Person updatedAlice = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getNric(), ALICE.getAddress(), ALICE.getDateOfBirth(),
                ALICE.getGender(), ALICE.getAllergies(), ALICE.getPriority(), ALICE.getAppointments(),
                initialMedConSet);
        model.setPerson(ALICE, updatedAlice);

        // Create delete command with a non-existent medical condition
        Set<MedCon> medConToDelete = new HashSet<>();
        medConToDelete.add(new MedCon("NonExistentCondition"));
        DelMedConCommand delMedConCommand = new DelMedConCommand(aliceNric, medConToDelete);

        // Expect CommandException due to non-existent medical condition
        assertThrows(CommandException.class, () -> delMedConCommand.execute(model),
                "Expected CommandException due to non-existent medical condition.");
    }

}
