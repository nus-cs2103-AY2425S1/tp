package seedu.hireme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.logic.Messages.MESSAGE_INTERNSHIP_APPLICATIONS_SORTED_OVERVIEW;
import static seedu.hireme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hireme.testutil.TypicalInternshipApplications.APPLE;
import static seedu.hireme.testutil.TypicalInternshipApplications.BOFA;
import static seedu.hireme.testutil.TypicalInternshipApplications.CITIBANK;
import static seedu.hireme.testutil.TypicalInternshipApplications.DELL;
import static seedu.hireme.testutil.TypicalInternshipApplications.EY;
import static seedu.hireme.testutil.TypicalInternshipApplications.FIGMA;
import static seedu.hireme.testutil.TypicalInternshipApplications.YAHOO;
import static seedu.hireme.testutil.TypicalInternshipApplications.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.hireme.model.Model;
import seedu.hireme.model.ModelManager;
import seedu.hireme.model.UserPrefs;
import seedu.hireme.model.internshipapplication.DateComparator;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */

public class SortCommandTest {
    private Model<InternshipApplication> model =
            new ModelManager<InternshipApplication>(getTypicalAddressBook(), new UserPrefs());
    private Model<InternshipApplication> expectedModel =
            new ModelManager<InternshipApplication>(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        DateComparator firstComparator = new DateComparator(true);
        DateComparator secondComparator = new DateComparator(false);

        SortCommand sortFirstCommand = new SortCommand(firstComparator);
        SortCommand sortSecondCommand = new SortCommand(secondComparator);

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // same values -> returns true
        SortCommand sortFirstCommandCopy = new SortCommand(firstComparator);
        assertTrue(sortFirstCommand.equals(sortFirstCommandCopy));

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));
    }

    @Test
    public void execute_sortEarliestFirstOrder_success() {
        DateComparator comparator = prepareComparator(true);
        SortCommand command = new SortCommand(comparator);
        expectedModel.sortFilteredList(comparator);
        assertCommandSuccess(command, model, MESSAGE_INTERNSHIP_APPLICATIONS_SORTED_OVERVIEW, expectedModel);
        assertEquals(Arrays.asList(CITIBANK, DELL, EY, FIGMA, YAHOO, APPLE, BOFA), model.getFilteredList());
    }

    @Test
    public void execute_sortLatestFirstOrder_success() {
        DateComparator comparator = prepareComparator(false);
        SortCommand command = new SortCommand(comparator);
        expectedModel.sortFilteredList(comparator);
        assertCommandSuccess(command, model, MESSAGE_INTERNSHIP_APPLICATIONS_SORTED_OVERVIEW, expectedModel);
        assertEquals(Arrays.asList(BOFA, APPLE, YAHOO, FIGMA, EY, DELL, CITIBANK), model.getFilteredList());
    }

    @Test
    public void toStringMethod() {
        DateComparator comparator1 = new DateComparator(true);
        SortCommand sortCommand1 = new SortCommand(comparator1);
        String expected1 = SortCommand.class.getCanonicalName() + "{comparator=" + comparator1 + "}";
        assertEquals(expected1, sortCommand1.toString());

        DateComparator comparator2 = new DateComparator(false);
        SortCommand sortCommand2 = new SortCommand(comparator2);
        String expected2 = SortCommand.class.getCanonicalName() + "{comparator=" + comparator2 + "}";
        assertEquals(expected2, sortCommand2.toString());
    }

    /**
     * Parses {@code isEarliestOrder} into a {@code DateComparator}.
     */
    private DateComparator prepareComparator(boolean isEarliestOrder) {
        return new DateComparator(isEarliestOrder);
    }
}
