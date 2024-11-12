package seedu.internbuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.testutil.TypicalApplications.SWE_APPLICATION;
import static seedu.internbuddy.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.logic.commands.exceptions.CommandException;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.ModelManager;
import seedu.internbuddy.model.UserPrefs;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.name.Name;
import seedu.internbuddy.testutil.ApplicationBuilder;

public class ApplyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() throws Exception {
        Application validApplication = new ApplicationBuilder().build();
        ApplyCommand applyCommand = new ApplyCommand(INDEX_FIRST_COMPANY, validApplication);
        CommandResult result = applyCommand.execute(model);

        Name firstCompanyName = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased()).getName();
        assertEquals(String.format(ApplyCommand.MESSAGE_SUCCESS, firstCompanyName, Messages.format(validApplication)),
                result.getFeedbackToUser());
    }
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Application validApplication = new ApplicationBuilder().build();
        ApplyCommand applyCommand = new ApplyCommand(Index.fromOneBased(999), validApplication);
        String expectedMessage = String.format(Messages.MESSAGE_INDEX_EXCEEDS_SIZE,
                model.getFilteredCompanyList().size());
        assertThrows(CommandException.class, () -> applyCommand.execute(model), expectedMessage);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ApplyCommand applyCommand = new ApplyCommand(INDEX_FIRST_COMPANY, SWE_APPLICATION);

        // same object -> returns true
        assertTrue(applyCommand.equals(applyCommand));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        Application validApplication = SWE_APPLICATION;
        ApplyCommand applyCommand1 = new ApplyCommand(INDEX_FIRST_COMPANY, validApplication);
        ApplyCommand applyCommand2 = new ApplyCommand(INDEX_SECOND_COMPANY, validApplication);

        // different objects -> returns false
        assertNotEquals(applyCommand1, applyCommand2);
    }
}
