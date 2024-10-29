package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindAppointmentCommand}.
 */
public class FindAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ArgumentMultimap mapForFirstPredicate = new ArgumentMultimap();
        ArgumentMultimap mapForSecondPredicate = new ArgumentMultimap();

        mapForFirstPredicate.put(PREFIX_START_TIME, "08:00");
        mapForSecondPredicate.put(PREFIX_START_TIME, "12:00");

        ContainsKeywordsPredicate firstPredicate =
                new ContainsKeywordsPredicate(mapForFirstPredicate);
        ContainsKeywordsPredicate secondPredicate =
                new ContainsKeywordsPredicate(mapForSecondPredicate);

        FindAppointmentCommand findFirstCommand = new FindAppointmentCommand(firstPredicate);
        FindAppointmentCommand findSecondCommand = new FindAppointmentCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindAppointmentCommand findFirstCommandCopy = new FindAppointmentCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noAppointmentFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        ContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindAppointmentCommand command = new FindAppointmentCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, false, false, false, true);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multipleAppointmentsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ContainsKeywordsPredicate predicate = preparePredicate("12:00 13:00");
        FindAppointmentCommand command = new FindAppointmentCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, false, false, false, true);
        // Ensure that the filtered list has appointments that match the criteria
    }

    @Test
    public void toStringMethod() {
        ArgumentMultimap mapForPredicate = new ArgumentMultimap();
        mapForPredicate.put(PREFIX_START_TIME, "08:00");
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(mapForPredicate);
        FindAppointmentCommand findCommand = new FindAppointmentCommand(predicate);
        String expected = FindAppointmentCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code ContainsKeywordsPredicate}.
     */
    private ContainsKeywordsPredicate preparePredicate(String userInput) {
        String[] timeArgs = userInput.split("\\s+");
        ArgumentMultimap mapForTimeArgs = new ArgumentMultimap();
        Arrays.stream(timeArgs).forEach(arg -> mapForTimeArgs.put(PREFIX_START_TIME, arg));
        return new ContainsKeywordsPredicate(mapForTimeArgs);
    }
}
