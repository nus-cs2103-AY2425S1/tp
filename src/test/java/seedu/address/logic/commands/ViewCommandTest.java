package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_NO_PERSON_FOUND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicates.NricMatchesPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewCommand}.
 */
public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NricMatchesPredicate firstPredicate = new NricMatchesPredicate("S1234567A");
        NricMatchesPredicate secondPredicate = new NricMatchesPredicate("S1234567B");

        ViewCommand viewFirstCommand = new ViewCommand("S1234567A");
        ViewCommand viewSecondCommand = new ViewCommand("S1234567B");

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand("S1234567A");
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void execute_invalidNric_noPersonFound() {
        NricMatchesPredicate predicate = new NricMatchesPredicate("S0000000X");
        ViewCommand viewCommand = new ViewCommand("S0000000X");
        model.updateFilteredPersonList(predicate); // Apply the predicate to ensure no matching persons

        String expectedMessage = MESSAGE_NO_PERSON_FOUND;

        // Expect CommandException due to no matching person
        assertCommandFailure(viewCommand, model, MESSAGE_NO_PERSON_FOUND);
    }

    @Test
    public void toStringMethod() {
        NricMatchesPredicate predicate = new NricMatchesPredicate("S1234567A");
        ViewCommand viewCommand = new ViewCommand("S1234567A");

        String expectedString = ViewCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";

        assertEquals(expectedString, viewCommand.toString());
    }

}
