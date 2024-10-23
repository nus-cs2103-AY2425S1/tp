package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.getTypicalWeddingBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.StaticContext;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

public class DeleteCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWeddingBook());
    }

    @Test
    public void execute_validName_success() throws CommandException {
        Person personToDelete = model.getFilteredPersonList().get(0);
        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getName().fullName);

        CommandResult commandResult = deleteCommand.execute(model);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_CONFIRMATION_PROMPT,
                personToDelete.getName().fullName,
                personToDelete.getPhone().value,
                personToDelete.getEmail().value,
                personToDelete.getAddress().value,
                personToDelete.getJob().value);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(personToDelete, StaticContext.getPersonToDelete());
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        String invalidName = "NonExistentName";
        DeleteCommand deleteCommand = new DeleteCommand(invalidName);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void execute_missingName_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand("");

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void equals() {

        final DeleteCommand standardCommand = new DeleteCommand(VALID_NAME_AMY);

        // same values -> returns true
        Name stubName = new Name(VALID_NAME_AMY);
        DeleteCommand commandWithSameValues = new DeleteCommand(stubName.toString());
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        DeleteCommand commandWithDifferentValues = new DeleteCommand(VALID_NAME_BOB);
        assertFalse(standardCommand.equals(commandWithDifferentValues));
    }
}
