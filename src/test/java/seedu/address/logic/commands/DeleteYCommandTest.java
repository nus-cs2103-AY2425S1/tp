// src/test/java/seedu/address/logic/commands/DeleteYCommandTest.java
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import seedu.address.testutil.PersonBuilder;

public class DeleteYCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWeddingBook());
    }

    @Test
    public void execute_confirmDeletion_success() throws CommandException {
        // Use an existing person from the typical address book
        Person personToDelete = model.getFilteredPersonList().get(0);
        StaticContext.setPersonToDelete(personToDelete);

        DeleteYCommand deleteYCommand = new DeleteYCommand(personToDelete);
        CommandResult commandResult = deleteYCommand.execute(model);

        String expectedMessage = String.format(DeleteYCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Person personToDelete = new PersonBuilder().withName("Alice Pauline").build();
        DeleteYCommand deleteYCommand1 = new DeleteYCommand(personToDelete);
        DeleteYCommand deleteYCommand2 = new DeleteYCommand(personToDelete);

        // Same object -> returns true
        assertEquals(deleteYCommand1, deleteYCommand1);

        // Same values -> returns true
        assertEquals(deleteYCommand1, deleteYCommand2);

        // Different types -> returns false
        assertNotEquals(deleteYCommand1, 1);

        // Null -> returns false
        assertNotEquals(deleteYCommand1, null);

        // Different person -> returns false
        Person differentPerson = new PersonBuilder().withName("Bob").build();
        DeleteYCommand deleteYCommand3 = new DeleteYCommand(differentPerson);
        assertNotEquals(deleteYCommand1, deleteYCommand3);
    }
}
