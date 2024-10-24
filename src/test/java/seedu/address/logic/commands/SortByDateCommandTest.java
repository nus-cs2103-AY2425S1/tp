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

/**
 * Contains integration tests (interaction with the Model) for {@code SortByDateCommand}.
 */
public class SortByDateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ReminderAddressBook());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ReminderAddressBook());

    @Test
    public void equals() {
        DateDistantToRecentComparator distantToRecentComparator = new DateDistantToRecentComparator();
        DateRecentToDistantComparator recentToDistantComparator = new DateRecentToDistantComparator();

        SortByDateCommand sortFirstCommand = new SortByDateCommand(distantToRecentComparator);
        SortByDateCommand sortSecondCommand = new SortByDateCommand(recentToDistantComparator);

        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        SortByDateCommand sortFirstCommandCopy = new SortByDateCommand(distantToRecentComparator);
        assertTrue(sortFirstCommand.equals(sortFirstCommandCopy));

        assertFalse(sortFirstCommand.equals(sortSecondCommand));

        assertFalse(sortFirstCommand.equals(1));

        assertFalse(sortFirstCommand.equals(null));
    }

    @Test
    public void execute_distantToRecent() {
        String expectedMessage = SortByDateCommand.MESSAGE_SUCCESS;
        SortByDateCommand command = new SortByDateCommand(new DateDistantToRecentComparator());
        expectedModel.updateSortedPersonList(new DateDistantToRecentComparator());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL, ELLE, FIONA, GEORGE, ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_recentToDistant() {
        String expectedMessage = SortByDateCommand.MESSAGE_SUCCESS;
        SortByDateCommand command = new SortByDateCommand(new DateRecentToDistantComparator());
        expectedModel.updateSortedPersonList(new DateRecentToDistantComparator());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ALICE, GEORGE, FIONA, ELLE, DANIEL, CARL), model.getFilteredPersonList());
    }
}
