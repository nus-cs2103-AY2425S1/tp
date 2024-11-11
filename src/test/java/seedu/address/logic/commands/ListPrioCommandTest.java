package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.TOM;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Priority;
import seedu.address.model.person.PriorityMatchesPredicate;

public class ListPrioCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Priority noPriority = new Priority();
    private Priority lowPriority = new Priority("LOW");
    private Priority mediumPriority = new Priority("MEDIUM");
    private Priority highPriority = new Priority("HIGH");

    @Test
    public void equals() {
        PriorityMatchesPredicate firstPredicate =
                new PriorityMatchesPredicate(lowPriority);
        PriorityMatchesPredicate secondPredicate =
                new PriorityMatchesPredicate(highPriority);

        ListPrioCommand listPrioFirstCommand = new ListPrioCommand(firstPredicate);
        ListPrioCommand listPrioSecondCommand = new ListPrioCommand(secondPredicate);

        // same object -> returns true
        assertTrue(listPrioFirstCommand.equals(listPrioFirstCommand));

        // same values -> returns true
        ListPrioCommand listPrioCommandCopy = new ListPrioCommand(firstPredicate);
        assertTrue(listPrioFirstCommand.equals(listPrioCommandCopy));

        // different types -> returns false
        assertFalse(listPrioFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listPrioFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(listPrioFirstCommand.equals(listPrioSecondCommand));
    }

    @Test
    public void execute_highPriority_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PriorityMatchesPredicate predicate = new PriorityMatchesPredicate(highPriority);
        ListPrioCommand command = new ListPrioCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_lowPriority_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PriorityMatchesPredicate predicate = new PriorityMatchesPredicate(lowPriority);
        ListPrioCommand command = new ListPrioCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_mediumPriority_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PriorityMatchesPredicate predicate = new PriorityMatchesPredicate(mediumPriority);
        ListPrioCommand command = new ListPrioCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_nonePriority_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PriorityMatchesPredicate predicate = new PriorityMatchesPredicate(noPriority);
        ListPrioCommand command = new ListPrioCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE, TOM), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PriorityMatchesPredicate predicate = new PriorityMatchesPredicate(highPriority);
        ListPrioCommand listPrioCommand = new ListPrioCommand(predicate);
        String expected = ListPrioCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, listPrioCommand.toString());
    }

}
