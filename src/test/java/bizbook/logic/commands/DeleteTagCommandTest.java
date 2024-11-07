package bizbook.logic.commands;

import static bizbook.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static bizbook.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static bizbook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static bizbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static bizbook.testutil.Assert.assertThrows;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static bizbook.testutil.TypicalIndexes.INDEX_OUTOFBOUND_PERSON;
import static bizbook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static bizbook.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import bizbook.logic.Messages;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.AddressBook;
import bizbook.model.Model;
import bizbook.model.ModelManager;
import bizbook.model.UserPrefs;
import bizbook.model.person.Person;
import bizbook.model.tag.Tag;
import bizbook.testutil.PersonBuilder;

public class DeleteTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteTagCommand_success() throws Exception {
        // Create the person and expected models as in the original
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Define the edited person with updated tag
        Person editedPerson = new PersonBuilder(personToEdit).withTags().build();

        // Define the DeleteTagCommand
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, new Tag(VALID_TAG_FRIENDS));

        // Define the expected message
        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_REMOVE_TAG_SUCCESS,
                Messages.format(editedPerson));

        // Create the expected model
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_OUTOFBOUND_PERSON, new Tag(VALID_TAG_FRIENDS));
        assertThrows(CommandException.class,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> deleteTagCommand.execute(model));
    }

    @Test
    public void execute_nonExistentTag_throwsCommandException() {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, new Tag(VALID_TAG_HUSBAND));
        String expectedMessage = String.format(DeleteTagCommand.TAG_DOES_NOT_EXIST, VALID_TAG_HUSBAND);
        assertThrows(CommandException.class,
                expectedMessage, () -> deleteTagCommand.execute(model));
    }

    @Test
    public void equals() {
        DeleteTagCommand deleteTagFirstCommand = new DeleteTagCommand(INDEX_FIRST_PERSON,
                new Tag(VALID_TAG_HUSBAND));
        DeleteTagCommand deleteTagSecondCommand = new DeleteTagCommand(INDEX_SECOND_PERSON,
                new Tag(VALID_TAG_FRIENDS));

        // same object -> returns true
        assertTrue(deleteTagFirstCommand.equals(deleteTagFirstCommand));

        // same values -> returns true
        DeleteTagCommand deleteTagFirstCommandCopy = new DeleteTagCommand(INDEX_FIRST_PERSON,
                new Tag(VALID_TAG_HUSBAND));

        assertTrue(deleteTagFirstCommand.equals(deleteTagFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteTagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteTagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteTagFirstCommand.equals(deleteTagSecondCommand));
    }
}
