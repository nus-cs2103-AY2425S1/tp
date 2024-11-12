package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.criteria.AppointmentSearchCriteria;
import seedu.address.logic.parser.criteria.SearchCriteria;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ContainsKeywordsPredicate;

public class FindAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals_sameObject_returnsTrue() {
        // Setup list with AppointmentSearchCriteria for start and end date and time
        List<SearchCriteria> criteriaList = List.of(
                new AppointmentSearchCriteria(
                        LocalDate.of(2025, 1, 1), LocalTime.of(12, 0),
                        LocalDate.of(2025, 1, 2), LocalTime.of(13, 0))
        );

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(criteriaList);
        FindAppointmentCommand findCommand = new FindAppointmentCommand(predicate);

        // same object -> returns true
        assertTrue(findCommand.equals(findCommand));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        // Setup list with AppointmentSearchCriteria for start and end date and time
        List<SearchCriteria> criteriaList = List.of(
                new AppointmentSearchCriteria(
                        LocalDate.of(2025, 1, 1), LocalTime.of(12, 0),
                        LocalDate.of(2025, 1, 2), LocalTime.of(13, 0))
        );

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(criteriaList);
        FindAppointmentCommand findCommand1 = new FindAppointmentCommand(predicate);
        FindAppointmentCommand findCommand2 = new FindAppointmentCommand(predicate);

        // same values -> returns true
        assertTrue(findCommand1.equals(findCommand2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        // Setup lists with different AppointmentSearchCriteria for date and time
        List<SearchCriteria> criteriaList1 = List.of(
                new AppointmentSearchCriteria(
                        LocalDate.of(2025, 1, 1), LocalTime.of(12, 0),
                        LocalDate.of(2025, 1, 2), LocalTime.of(13, 0))
        );

        List<SearchCriteria> criteriaList2 = List.of(
                new AppointmentSearchCriteria(
                        LocalDate.of(2025, 2, 1), LocalTime.of(14, 0),
                        LocalDate.of(2025, 2, 3), LocalTime.of(15, 0))
        );

        ContainsKeywordsPredicate predicate1 = new ContainsKeywordsPredicate(criteriaList1);
        ContainsKeywordsPredicate predicate2 = new ContainsKeywordsPredicate(criteriaList2);

        FindAppointmentCommand findCommand1 = new FindAppointmentCommand(predicate1);
        FindAppointmentCommand findCommand2 = new FindAppointmentCommand(predicate2);

        // different predicate -> returns false
        assertFalse(findCommand1.equals(findCommand2));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // Setup list with AppointmentSearchCriteria for date and time
        List<SearchCriteria> criteriaList = List.of(
                new AppointmentSearchCriteria(
                        LocalDate.of(2025, 1, 1), LocalTime.of(12, 0),
                        LocalDate.of(2025, 1, 2), LocalTime.of(13, 0))
        );

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(criteriaList);
        FindAppointmentCommand findCommand = new FindAppointmentCommand(predicate);

        // null -> returns false
        assertFalse(findCommand.equals(null));
    }

    @Test
    public void execute_noMatchingAppointments_noAppointmentsFound() {
        // Setup list with non-matching AppointmentSearchCriteria for date and time
        List<SearchCriteria> criteriaList = List.of(
                new AppointmentSearchCriteria(
                        LocalDate.of(2025, 1, 1), LocalTime.of(12, 0),
                        LocalDate.of(2025, 1, 2), LocalTime.of(13, 0))
        );

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(criteriaList);
        FindAppointmentCommand command = new FindAppointmentCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model,
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0), expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_matchingAppointments_multipleAppointmentsFound() {
        // Setup list with matching AppointmentSearchCriteria for date and time
        List<SearchCriteria> criteriaList = List.of(
                new AppointmentSearchCriteria(
                        LocalDate.of(2025, 1, 1), LocalTime.of(12, 0),
                        LocalDate.of(2025, 1, 2), LocalTime.of(13, 0))
        );

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(criteriaList);
        FindAppointmentCommand command = new FindAppointmentCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model,
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size()),
                expectedModel);
        assertEquals(model.getFilteredPersonList().size(), expectedModel.getFilteredPersonList().size());
    }
}

