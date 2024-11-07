package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.PredefinedAssignmentsData;
import seedu.address.model.person.comparator.ComparatorManager;
import seedu.address.model.person.comparator.PersonComparator;
import seedu.address.model.person.comparator.SortField;
import seedu.address.model.person.comparator.SortOrder;

public class SortCommandTest {
    private Model model = new ModelManager(
            getTypicalAddressBook(),
            new UserPrefs(),
            new PredefinedAssignmentsData());
    private Model expectedModel = new ModelManager(
            getTypicalAddressBook(),
            new UserPrefs(),
            new PredefinedAssignmentsData());

    private ComparatorManager comparatorManager = new ComparatorManager();

    @Test
    public void equals() {
        PersonComparator firstComparator = comparatorManager.getComparator(SortField.NAME, SortOrder.ASC);
        PersonComparator secondComparator = comparatorManager.getComparator(SortField.GITHUB, SortOrder.ASC);

        SortCommand firstCommand = new SortCommand(firstComparator);
        SortCommand secondCommand = new SortCommand(secondComparator);

        SortCommand firstResetCommand = new SortCommand(null);
        SortCommand secondResetCommand = new SortCommand(null);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // sane value -> returns true
        SortCommand firstCommandCopy = new SortCommand(firstComparator);
        assertTrue(firstCommand.equals(firstCommandCopy));
        assertTrue(firstResetCommand.equals(secondResetCommand));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different comparator -> returns false
        assertFalse(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(firstResetCommand));
        assertFalse(firstResetCommand.equals(firstCommand));
    }

    @Test
    public void toStringMethod() {
        PersonComparator comparator = comparatorManager.getComparator(SortField.NAME, SortOrder.ASC);
        SortCommand sortCommand = new SortCommand(comparator);
        String expected = SortCommand.class.getCanonicalName() + "{comparator=" + comparator + "}";
        assertEquals(expected, sortCommand.toString());

        SortCommand resetCommand = new SortCommand(null);
        String expectedReset = SortCommand.class.getCanonicalName() + "{comparator=null}";
        assertEquals(expectedReset, resetCommand.toString());
    }

    @Test
    public void execute_unfilteredList_success() {
        PersonComparator comparator = comparatorManager.getComparator(SortField.NAME, SortOrder.DESC);
        String expectedMessage =
                String.format(SortCommand.MESSAGE_SUCCESS, comparator.getSortField(), comparator.getSortOrder());
        SortCommand command = new SortCommand(comparator);

        expectedModel.updateSortedPersonList(comparator);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        SortCommand resetCommand = new SortCommand(null);
        expectedModel.updateSortedPersonList(null);
        assertCommandSuccess(resetCommand, model, SortCommand.RESET_MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        PersonComparator comparator = comparatorManager.getComparator(SortField.GITHUB, SortOrder.ASC);
        String expectedMessage =
                String.format(SortCommand.MESSAGE_SUCCESS, comparator.getSortField(), comparator.getSortOrder());
        SortCommand command = new SortCommand(comparator);

        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.updateSortedPersonList(comparator);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        SortCommand resetCommand = new SortCommand(null);
        expectedModel.updateSortedPersonList(null);
        assertCommandSuccess(resetCommand, model, SortCommand.RESET_MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyList_success() {
        Model model = new ModelManager(
                new AddressBook(),
                new UserPrefs(),
                new PredefinedAssignmentsData());
        Model expectedModel = new ModelManager(
                new AddressBook(),
                new UserPrefs(),
                new PredefinedAssignmentsData());

        PersonComparator comparator = comparatorManager.getComparator(SortField.GITHUB, SortOrder.ASC);
        String expectedMessage =
                String.format(SortCommand.MESSAGE_SUCCESS, comparator.getSortField(), comparator.getSortOrder());
        SortCommand command = new SortCommand(comparator);

        expectedModel.updateSortedPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        SortCommand resetCommand = new SortCommand(null);
        String expectedResetMessage = SortCommand.RESET_MESSAGE_SUCCESS;

        assertCommandSuccess(resetCommand, model, expectedResetMessage, expectedModel);

    }


}
