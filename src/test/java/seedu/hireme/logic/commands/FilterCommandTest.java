package seedu.hireme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.logic.Messages.MESSAGE_INTERNSHIP_APPLICATIONS_LISTED_OVERVIEW;
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
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.hireme.model.Model;
import seedu.hireme.model.ModelManager;
import seedu.hireme.model.UserPrefs;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.StatusPredicate;


/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */

public class FilterCommandTest {
    private Model<InternshipApplication> model =
            new ModelManager<InternshipApplication>(getTypicalAddressBook(), new UserPrefs());
    private Model<InternshipApplication> expectedModel =
            new ModelManager<InternshipApplication>(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        StatusPredicate firstPredicate =
                new StatusPredicate("pending");
        StatusPredicate secondPredicate =
                new StatusPredicate("accepted");

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different commands -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_pendingAccepted_noInternshipApplicationsFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_APPLICATIONS_LISTED_OVERVIEW, 0);
        StatusPredicate predicate = preparePredicate("accepted");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredList());
    }

    @Test
    public void execute_pendingStatus_multipleInternshipApplicationsFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_APPLICATIONS_LISTED_OVERVIEW, 7);
        StatusPredicate predicate = preparePredicate("PENDING");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(APPLE, BOFA, CITIBANK, DELL, EY, FIGMA, YAHOO), model.getFilteredList());
    }

    @Test
    public void toStringMethod() {
        StatusPredicate predicate = new StatusPredicate("pending");
        FilterCommand filterCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{statusPredicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code StatusPredicate}.
     */
    private StatusPredicate preparePredicate(String userInput) {
        return new StatusPredicate(userInput);
    }
}
