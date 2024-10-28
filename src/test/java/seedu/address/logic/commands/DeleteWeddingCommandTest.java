package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_NAME_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_NAME_TWO;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.getTypicalWeddingBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.StaticContext;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

public class DeleteWeddingCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWeddingBook());
    }

    @Test
    public void execute_validWeddingName_success() throws CommandException {
        Wedding weddingToDelete = model.getFilteredWeddingList().get(0);
        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(
                weddingToDelete.getWeddingName().toString());

        CommandResult commandResult = deleteWeddingCommand.execute(model);

        String expectedMessage = String.format(DeleteWeddingCommand.MESSAGE_CONFIRMATION_PROMPT,
                weddingToDelete.getWeddingName(),
                weddingToDelete.getVenue(),
                weddingToDelete.getDate());

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(weddingToDelete, StaticContext.getWeddingToDelete());
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        String invalidWeddingName = "NonExistentName";
        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(invalidWeddingName);

        assertThrows(CommandException.class, () -> deleteWeddingCommand.execute(model));
    }

    @Test
    public void execute_missingName_throwsCommandException() {
        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand("");

        assertThrows(CommandException.class, () -> deleteWeddingCommand.execute(model));
    }

    @Test
    public void equals() {

        final DeleteWeddingCommand standardWeddingCommand = new DeleteWeddingCommand(VALID_WEDDING_NAME_ONE);

        // same values -> returns true
        WeddingName stubName = new WeddingName(VALID_WEDDING_NAME_ONE);
        DeleteWeddingCommand commandWithSameValues = new DeleteWeddingCommand(stubName.toString());
        assertTrue(standardWeddingCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardWeddingCommand.equals(standardWeddingCommand));

        // null -> returns false
        assertFalse(standardWeddingCommand.equals(null));

        // different types -> returns false
        assertFalse(standardWeddingCommand.equals(new ListCommand()));

        // different name -> returns false
        DeleteWeddingCommand commandWithDifferentValues = new DeleteWeddingCommand(VALID_WEDDING_NAME_TWO);
        assertFalse(standardWeddingCommand.equals(commandWithDifferentValues));
    }
}
