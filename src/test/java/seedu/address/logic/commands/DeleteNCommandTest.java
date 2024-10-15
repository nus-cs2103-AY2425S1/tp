// src/test/java/seedu/address/logic/commands/DeleteNCommandTest.java
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.StaticContext;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteNCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_cancelDeletion_success() throws CommandException {
        Person personToDelete = new PersonBuilder().build();
        StaticContext.setPersonToDelete(personToDelete);

        DeleteNCommand deleteNCommand = new DeleteNCommand();
        CommandResult commandResult = deleteNCommand.execute(model);

        assertCommandSuccess(deleteNCommand, model, DeleteNCommand.MESSAGE_CANCEL_DELETE, model);
        assertNull(StaticContext.getPersonToDelete());
    }
}