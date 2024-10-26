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
import seedu.address.model.person.PriorityHighToLowComparator;
import seedu.address.model.person.PriorityLowToHighComparator;

/**
 * Contains integration tests (interaction with the Model) for {@code SortByPriorityCommand}.
 */
public class SortByPriorityCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ReminderAddressBook());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ReminderAddressBook());

    @Test
    public void equals() {
        PriorityLowToHighComparator lowToHighComparator = new PriorityLowToHighComparator();
        PriorityHighToLowComparator highToLowComparator = new PriorityHighToLowComparator();

        SortByPriorityCommand sortFirstCommand = new SortByPriorityCommand(lowToHighComparator);
        SortByPriorityCommand sortSecondCommand = new SortByPriorityCommand(highToLowComparator);

        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        SortByPriorityCommand sortFirstCommandCopy = new SortByPriorityCommand(lowToHighComparator);
        assertTrue(sortFirstCommand.equals(sortFirstCommandCopy));

        assertFalse(sortFirstCommand.equals(sortSecondCommand));

        assertFalse(sortFirstCommand.equals(1));

        assertFalse(sortFirstCommand.equals(null));
    }

    @Test
    public void execute_lowToHigh() {
        String expectedMessage = SortByPriorityCommand.MESSAGE_SUCCESS;
        SortByPriorityCommand command = new SortByPriorityCommand(new PriorityLowToHighComparator());
        expectedModel.updateSortedPersonList(new PriorityLowToHighComparator());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_highToLow() {
        String expectedMessage = SortByPriorityCommand.MESSAGE_SUCCESS;
        SortByPriorityCommand command = new SortByPriorityCommand(new PriorityHighToLowComparator());
        expectedModel.updateSortedPersonList(new PriorityHighToLowComparator());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }
}
