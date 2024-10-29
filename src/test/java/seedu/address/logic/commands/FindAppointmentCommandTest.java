package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindAppointmentCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ContainsKeywordsPredicate;

public class FindAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals_sameObject_returnsTrue() {
        // Setup ArgumentMultimap with date and time
        ArgumentMultimap mapForPredicate = new ArgumentMultimap();
        mapForPredicate.put(PREFIX_START_DATE, "01/01/2025");
        mapForPredicate.put(PREFIX_START_TIME, "12:00");

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForPredicate);
        FindAppointmentCommand findCommand = new FindAppointmentCommand(predicate);

        // same object -> returns true
        assertTrue(findCommand.equals(findCommand));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        // Setup ArgumentMultimap with date and time
        ArgumentMultimap mapForPredicate = new ArgumentMultimap();
        mapForPredicate.put(PREFIX_START_DATE, "01/01/2025");
        mapForPredicate.put(PREFIX_START_TIME, "12:00");

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForPredicate);
        FindAppointmentCommand findCommand1 = new FindAppointmentCommand(predicate);
        FindAppointmentCommand findCommand2 = new FindAppointmentCommand(predicate);

        // same values -> returns true
        assertTrue(findCommand1.equals(findCommand2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        // Setup ArgumentMultimap with different date and time
        ArgumentMultimap mapForPredicate1 = new ArgumentMultimap();
        mapForPredicate1.put(PREFIX_START_DATE, "01/01/2025");
        mapForPredicate1.put(PREFIX_START_TIME, "12:00");

        ArgumentMultimap mapForPredicate2 = new ArgumentMultimap();
        mapForPredicate2.put(PREFIX_START_DATE, "02/01/2025");
        mapForPredicate2.put(PREFIX_START_TIME, "13:00");

        ContainsKeywordsPredicate predicate1 = new ContainsKeywordsPredicate(mapForPredicate1);
        ContainsKeywordsPredicate predicate2 = new ContainsKeywordsPredicate(mapForPredicate2);

        FindAppointmentCommand findCommand1 = new FindAppointmentCommand(predicate1);
        FindAppointmentCommand findCommand2 = new FindAppointmentCommand(predicate2);

        // different predicate -> returns false
        assertFalse(findCommand1.equals(findCommand2));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // Setup ArgumentMultimap with date and time
        ArgumentMultimap mapForPredicate = new ArgumentMultimap();
        mapForPredicate.put(PREFIX_START_DATE, "01/01/2025");
        mapForPredicate.put(PREFIX_START_TIME, "12:00");

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForPredicate);
        FindAppointmentCommand findCommand = new FindAppointmentCommand(predicate);

        // null -> returns false
        assertFalse(findCommand.equals(null));
    }

    @Test
    public void execute_noMatchingAppointments_noAppointmentsFound() {
        // Setup ArgumentMultimap with non-matching date and time
        ArgumentMultimap mapForPredicate = new ArgumentMultimap();
        mapForPredicate.put(PREFIX_START_DATE, "01/01/2025");
        mapForPredicate.put(PREFIX_START_TIME, "12:00");
        mapForPredicate.put(PREFIX_END_DATE, "02/01/2025");
        mapForPredicate.put(PREFIX_END_TIME, "13:00");

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForPredicate);
        FindAppointmentCommand command = new FindAppointmentCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model,
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0), expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_matchingAppointments_multipleAppointmentsFound() {
        // Setup ArgumentMultimap with matching date and time
        ArgumentMultimap mapForPredicate = new ArgumentMultimap();
        mapForPredicate.put(PREFIX_START_DATE, "01/01/2025");
        mapForPredicate.put(PREFIX_START_TIME, "12:00");
        mapForPredicate.put(PREFIX_END_DATE, "02/01/2025");
        mapForPredicate.put(PREFIX_END_TIME, "13:00");

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForPredicate);
        FindAppointmentCommand command = new FindAppointmentCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model,
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                expectedModel);
        assertEquals(model.getFilteredPersonList().size(), expectedModel.getFilteredPersonList().size());
    }
}
