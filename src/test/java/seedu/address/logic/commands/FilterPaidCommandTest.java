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
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.predicate.StudentHasPaidPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterPaidCommand}.
 */
public class FilterPaidCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void equals() {
        StudentHasPaidPredicate truePredicate = new StudentHasPaidPredicate(true);
        StudentHasPaidPredicate falsePredicate = new StudentHasPaidPredicate(false);

        FilterPaidCommand trueFilterPaidCommand = new FilterPaidCommand(truePredicate);
        FilterPaidCommand falseFilterPaidCommand = new FilterPaidCommand(falsePredicate);

        // same object -> returns true
        assertTrue(trueFilterPaidCommand.equals(trueFilterPaidCommand));

        // same values -> returns true
        FilterPaidCommand trueFilterPaidCommandCopy = new FilterPaidCommand(truePredicate);
        assertTrue(trueFilterPaidCommand.equals(trueFilterPaidCommandCopy));

        // different types -> returns false
        assertFalse(trueFilterPaidCommand.equals(1));

        // null -> returns false
        assertFalse(trueFilterPaidCommand.equals(null));

        // different predicate value -> returns false
        assertFalse(trueFilterPaidCommand.equals(falseFilterPaidCommand));
    }

    @Test
    public void execute_hasNotPaidFilter() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        StudentHasPaidPredicate predicate = new StudentHasPaidPredicate(false);
        FilterPaidCommand command = new FilterPaidCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_hasPaidFilter() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        StudentHasPaidPredicate predicate = new StudentHasPaidPredicate(true);
        FilterPaidCommand command = new FilterPaidCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        StudentHasPaidPredicate predicate = new StudentHasPaidPredicate(true);
        FilterPaidCommand filterPaidCommand = new FilterPaidCommand(predicate);
        String expected = FilterPaidCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterPaidCommand.toString());
    }
}
