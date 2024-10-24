package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.ApplicationStatusCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_OFFER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_ONGOING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;


public class ApplicationStatusCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute() {
        final String status = "Some status";

        assertCommandFailure(new ApplicationStatusCommand(INDEX_FIRST_COMPANY, status), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_COMPANY.getOneBased(), status));
    }

    @Test
    public void equals() {
        final ApplicationStatusCommand standardCommand = new ApplicationStatusCommand(INDEX_FIRST_COMPANY,
                VALID_STATUS_OFFER);

        ApplicationStatusCommand commandWithSameValues = new ApplicationStatusCommand(INDEX_FIRST_COMPANY,
                VALID_STATUS_OFFER);
        // same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new ApplicationStatusCommand(INDEX_SECOND_COMPANY, VALID_STATUS_OFFER)));
        // different status -> returns false
        assertFalse(standardCommand.equals(new ApplicationStatusCommand(INDEX_FIRST_COMPANY, VALID_STATUS_ONGOING)));
    }
}
