package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DateAbsentPredicate;

public class ListAbsenteesCommandTest {
    private static final LocalDateTime FIRST_DATE_TIME = LocalDateTime.of(2024, 1, 1, 12, 0);
    private static final LocalDateTime SECOND_DATE_TIME = LocalDateTime.of(2024, 1, 8, 12, 0);
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        DateAbsentPredicate firstPredicate = new DateAbsentPredicate(FIRST_DATE_TIME);
        DateAbsentPredicate secondPredicate = new DateAbsentPredicate(SECOND_DATE_TIME);

        ListAbsenteesCommand firstCommand = new ListAbsenteesCommand(firstPredicate);
        ListAbsenteesCommand secondCommand = new ListAbsenteesCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        ListAbsenteesCommand firstCommandCopy = new ListAbsenteesCommand(firstPredicate);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different dates -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_validKeyword_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        DateAbsentPredicate predicate = new DateAbsentPredicate(FIRST_DATE_TIME);
        ListAbsenteesCommand command = new ListAbsenteesCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        DateAbsentPredicate predicate = new DateAbsentPredicate(FIRST_DATE_TIME);
        ListAbsenteesCommand command = new ListAbsenteesCommand(predicate);
        String expected = ListAbsenteesCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }
}
