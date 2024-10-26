package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonComparator;

public class SortCommandTest {
    private Model modelAscendingName = new ModelManager(getTypicalAddressBook(PersonComparator
            .NAME, true), new UserPrefs());
    private Model modelDescendingName = new ModelManager(getTypicalAddressBook(PersonComparator
            .NAME, false), new UserPrefs());
    private Model modelAscendingDateOfLastVisit = new ModelManager(getTypicalAddressBook(PersonComparator
            .DATE_OF_LAST_VISIT, true), new UserPrefs());
    private Model modelDescendingDateOfLastVisit = new ModelManager(getTypicalAddressBook(PersonComparator
            .DATE_OF_LAST_VISIT, false), new UserPrefs());

    @Test
    public void execute_nameAscendingOrder_success() {
        executeSuccessfulSortTest(PersonComparator.NAME, true, modelAscendingDateOfLastVisit);
    }

    @Test
    public void execute_nameDescendingOrder_success() {
        executeSuccessfulSortTest(PersonComparator.NAME, false, modelDescendingDateOfLastVisit);
    }

    @Test
    public void execute_dateOfLastVisitAscending_success() {
        executeSuccessfulSortTest(PersonComparator.DATE_OF_LAST_VISIT, true, modelDescendingName);
    }

    @Test
    public void execute_dateOfLastVisitDescending_success() {
        executeSuccessfulSortTest(PersonComparator.DATE_OF_LAST_VISIT, false, modelAscendingName);
    }

    private void executeSuccessfulSortTest(String sortParameter, boolean isAscending, Model model) {
        SortCommand sortCommand = new SortCommand(sortParameter, isAscending);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS,
                sortParameter, isAscending ? "ascending" : "descending");
        Model expectedModel = new ModelManager(getTypicalAddressBook(sortParameter, isAscending), new UserPrefs());
        assertCommandSuccess(sortCommand, modelDescendingName, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final SortCommand standardCommand = new SortCommand(PersonComparator.NAME, true);

        // same values -> returns true
        SortCommand commandWithSameValues = new SortCommand(PersonComparator.NAME, true);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different sorting order -> returns false
        assertFalse(standardCommand.equals(new SortCommand(PersonComparator.NAME, false)));

        // different sort parameter -> returns false
        assertFalse(standardCommand.equals(new SortCommand(PersonComparator.DATE_OF_LAST_VISIT, true)));
    }
}
