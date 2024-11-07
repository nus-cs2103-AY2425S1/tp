package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateCommand.
 */
public class UpdateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());

    @Test
    public void execute_startDateBeforeEndDate_failure() {
        LocalDate sd = LocalDate.of(2024, 01, 02);
        LocalDate ed = LocalDate.of(2024, 01, 01);
        UpdateCommand cmd = new UpdateCommand("", sd, ed,
                null, null, null, INDEX_FIRST_EVENT);
        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_DATES);
    }
}
