package seedu.address.logic.commands;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Schedule;


public class SearchCommandTest {
    private Model model;
    private Model expectedModel;
    private LocalDateTime begin;
    private LocalDateTime end;
    @BeforeEach
    void setUp() {
        begin = LocalDateTime.of(2024, 10, 21, 11, 0);
        end = LocalDateTime.of(2024, 10, 22, 13, 0);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    void execute_searchBeforeEndTime_updatesFilteredPersonList() {
        SearchCommand searchCommand = new SearchCommand(null, end);
        expectedModel.updateFilteredPersonList(person -> {
            Schedule schedule = person.getSchedule();
            return schedule.getDateTime() != null && schedule.getDateTime().isBefore(end);
        });
        assertCommandSuccess(searchCommand, model, SearchCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_searchAfterBeginTime_updatesFilteredPersonList() {
        SearchCommand searchCommand = new SearchCommand(begin, null);
        expectedModel.updateFilteredPersonList(person -> {
            Schedule schedule = person.getSchedule();
            return schedule.getDateTime() != null && schedule.getDateTime().isAfter(begin);
        });
        assertCommandSuccess(searchCommand, model, SearchCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_searchBetweenBeginAndEndTime_updatesFilteredPersonList() {
        SearchCommand searchCommand = new SearchCommand(begin, end);
        expectedModel.updateFilteredPersonList(person -> {
            Schedule schedule = person.getSchedule();
            return schedule.getDateTime() != null &&
                    schedule.getDateTime().isAfter(begin) && schedule.getDateTime().isBefore(end);
        });
        assertCommandSuccess(searchCommand, model, SearchCommand.MESSAGE_SUCCESS, expectedModel);
    }
    @Test
    public void equals() {
        SearchCommand searchCommand = new SearchCommand(begin, end);
        SearchCommand searchCopyCommand = new SearchCommand(begin, end);
        SearchCommand searchBeginCommand = new SearchCommand(begin, null);
        SearchCommand searchEndCommand = new SearchCommand(null, end);
        SearchCommand searchSecondCommand = new SearchCommand(LocalDateTime.of(2024, 11, 21, 11, 0), LocalDateTime.of(2024, 12, 21, 11, 0));
        // same object -> returns true
        assertTrue(searchCommand.equals(searchCommand));
        assertTrue(searchBeginCommand.equals(searchBeginCommand));
        assertTrue(searchEndCommand.equals(searchEndCommand));
        // same values -> returns true
        assertTrue(searchCommand.equals(searchCopyCommand));
        // different values -> returns false
        assertFalse(searchCommand.equals(searchBeginCommand));
        assertFalse(searchCommand.equals(searchEndCommand));
        assertFalse(searchCommand.equals(searchSecondCommand));
        // different types -> returns false
        assertFalse(searchCommand.equals(1));
        // null -> returns false
        assertFalse(searchCommand.equals(null));
    }

}
