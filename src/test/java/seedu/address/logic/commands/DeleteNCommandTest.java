// src/test/java/seedu/address/logic/commands/DeleteNCommandTest.java
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.getTypicalWeddingBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.StaticContext;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.WeddingBuilder;

public class DeleteNCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWeddingBook());
    }

    @Test
    public void execute_cancelPersonDeletion_success() throws CommandException {
        Person personToDelete = new PersonBuilder().build();
        StaticContext.setPersonToDelete(personToDelete);

        DeleteNCommand deleteNCommand = new DeleteNCommand();
        CommandResult commandResult = deleteNCommand.execute(model);

        String expectedMessage = DeleteNCommand.MESSAGE_CANCEL_DELETE;

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertNull(StaticContext.getPersonToDelete());
    }

    @Test
    public void execute_cancelWeddingDeletion_success() throws CommandException {
        Wedding weddingToDelete = new WeddingBuilder().build();
        StaticContext.setWeddingToDelete(weddingToDelete);

        DeleteNCommand deleteNCommand = new DeleteNCommand();
        CommandResult commandResult = deleteNCommand.execute(model);

        String expectedMessage = DeleteNCommand.MESSAGE_CANCEL_DELETE;

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertNull(StaticContext.getWeddingToDelete());
    }

    @Test
    public void execute_cancelClearAddressBook_success() throws CommandException {
        StaticContext.setClearAddressBookPending(true);

        DeleteNCommand deleteNCommand = new DeleteNCommand();
        CommandResult commandResult = deleteNCommand.execute(model);
        String expectedMessage = DeleteNCommand.MESSAGE_CANCEL_DELETE;

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(false, StaticContext.isClearAddressBookPending());
    }

    @Test
    public void execute_cancelClearWeddingBook_success() throws CommandException {
        StaticContext.setClearWeddingBookPending(true);

        DeleteNCommand deleteNCommand = new DeleteNCommand();
        CommandResult commandResult = deleteNCommand.execute(model);

        String expectedMessage = DeleteNCommand.MESSAGE_CANCEL_DELETE;

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(false, StaticContext.isClearWeddingBookPending());
    }

    @Test
    public void equals() {
        DeleteNCommand deleteNCommand1 = new DeleteNCommand();
        DeleteNCommand deleteNCommand2 = new DeleteNCommand();

        // Same object -> returns true
        assertEquals(deleteNCommand1, deleteNCommand1);

        // Same values -> returns true
        assertEquals(deleteNCommand1, deleteNCommand2);

        // Different types -> returns false
        assertNotEquals(deleteNCommand1, 1);

        // Null -> returns false
        assertNotEquals(deleteNCommand1, null);
    }
}
