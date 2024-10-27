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
import seedu.address.model.person.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.WeddingBuilder;

public class DeleteYCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWeddingBook());
    }

    @Test
    public void execute_confirmPersonDeletion_success() throws CommandException {
        // Use an existing person from the typical address book
        Person personToDelete = model.getFilteredPersonList().get(0);
        StaticContext.setPersonToDelete(personToDelete);

        DeleteYCommand deleteYCommand = new DeleteYCommand(personToDelete);
        CommandResult commandResult = deleteYCommand.execute(model);

        String expectedMessage = String.format(DeleteYCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_confirmWeddingDeletion_success() throws CommandException {
        // Use an existing person from the typical address book
        Wedding weddingToDelete = new WeddingBuilder().withWeddingName("Alice Loh & James Koh").build();
        model.addWedding(weddingToDelete);
        Person personInWedding = model.getFilteredPersonList().get(0);
        personInWedding.getTags().add(new Tag(weddingToDelete.getWeddingName().toString()));
        weddingToDelete.getParticipants().add(personInWedding);
        StaticContext.setWeddingToDelete(weddingToDelete);

        DeleteYCommand deleteYCommand = new DeleteYCommand(weddingToDelete);
        CommandResult commandResult = deleteYCommand.execute(model);

        String expectedMessage = String.format(DeleteYCommand.MESSAGE_DELETE_WEDDING_SUCCESS,
                weddingToDelete.getWeddingName());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWeddingBook());

        assertEquals(model, expectedModel);
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

        Wedding weddingToDelete = new WeddingBuilder().withWeddingName("Alice Koh & James Loh").build();
        DeleteYCommand deleteYCommand4 = new DeleteYCommand(weddingToDelete);
        DeleteYCommand deleteYCommand5 = new DeleteYCommand(weddingToDelete);

        // Same object -> returns true
        assertEquals(deleteYCommand4, deleteYCommand4);

        // Same values -> returns true
        assertEquals(deleteYCommand4, deleteYCommand5);

        // Different types -> returns false
        assertNotEquals(deleteYCommand4, 1);

        // Null -> returns false
        assertNotEquals(deleteYCommand4, null);

        // Different person -> returns false
        Wedding differentWedding = new WeddingBuilder().withWeddingName("Charis Ho & Lenny Tan").build();
        DeleteYCommand deleteYCommand6 = new DeleteYCommand(differentWedding);
        assertNotEquals(deleteYCommand4, deleteYCommand6);
    }
}
