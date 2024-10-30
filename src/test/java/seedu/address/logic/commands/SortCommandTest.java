package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReminderAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DateDistantToRecentComparator;
import seedu.address.model.person.DateRecentToDistantComparator;
import seedu.address.model.person.PriorityHighToLowComparator;
import seedu.address.model.person.PriorityLowToHighComparator;

/**
 * Contains integration tests (interaction with the Model) for {@code SortByDateCommand}.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ReminderAddressBook());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ReminderAddressBook());

    @Test
    public void equals() {
        DateDistantToRecentComparator distantToRecentComparator = new DateDistantToRecentComparator();
        DateRecentToDistantComparator recentToDistantComparator = new DateRecentToDistantComparator();
        PriorityLowToHighComparator lowToHighComparator = new PriorityLowToHighComparator();
        PriorityHighToLowComparator highToLowComparator = new PriorityHighToLowComparator();

        SortCommand sortFirstCommand = new SortCommand(distantToRecentComparator);
        SortCommand sortSecondCommand = new SortCommand(recentToDistantComparator);

        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        SortCommand sortFirstCommandCopy = new SortCommand(distantToRecentComparator);
        assertTrue(sortFirstCommand.equals(sortFirstCommandCopy));

        assertFalse(sortFirstCommand.equals(sortSecondCommand));

        assertFalse(sortFirstCommand.equals(1));

        assertFalse(sortFirstCommand.equals(null));

        SortCommand sortThirdCommand = new SortCommand(lowToHighComparator);
        SortCommand sortFourthCommand = new SortCommand(highToLowComparator);

        assertTrue(sortThirdCommand.equals(sortThirdCommand));

        SortCommand sortThirdCommandCopy = new SortCommand(lowToHighComparator);
        assertTrue(sortThirdCommand.equals(sortThirdCommandCopy));

        assertFalse(sortThirdCommand.equals(sortFourthCommand));

        assertFalse(sortThirdCommand.equals(1));

        assertFalse(sortThirdCommand.equals(null));
    }

    @Test
    public void execute_distantToRecent() {
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        SortCommand command = new SortCommand(new DateDistantToRecentComparator());
        expectedModel.updateSortedPersonList(new DateDistantToRecentComparator());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL, ELLE, FIONA, GEORGE, ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_recentToDistant() {
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        SortCommand command = new SortCommand(new DateRecentToDistantComparator());
        expectedModel.updateSortedPersonList(new DateRecentToDistantComparator());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ALICE, GEORGE, FIONA, ELLE, DANIEL, CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_lowToHigh() {
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        SortCommand command = new SortCommand(new PriorityLowToHighComparator());
        expectedModel.updateSortedPersonList(new PriorityLowToHighComparator());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_highToLow() {
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        SortCommand command = new SortCommand(new PriorityHighToLowComparator());
        expectedModel.updateSortedPersonList(new PriorityHighToLowComparator());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }
}
