package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.commands.DateCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DateCommand.
 */
public class DateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute() {
        final Date date = new Date("Some date");
        assertCommandFailure(new DateCommand(INDEX_FIRST_PERSON, date), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), date));
    }
    @Test
    public void equals() {
        final DateCommand standardCommand = new DateCommand(INDEX_FIRST_PERSON, new Date(VALID_DATE_AMY));
        // same values -> returns true
        DateCommand commandWithSameValues = new DateCommand(INDEX_FIRST_PERSON, new Date(VALID_DATE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new DateCommand(INDEX_SECOND_PERSON, new Date(VALID_DATE_AMY))));
        // different date -> returns false
        assertFalse(standardCommand.equals(new DateCommand(INDEX_FIRST_PERSON, new Date(VALID_DATE_BOB))));
    }
}
