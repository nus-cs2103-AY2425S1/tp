package bizbook.logic.commands;

import static bizbook.logic.Messages.MESSAGE_INVALID_NOTED_INDEX;
import static bizbook.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static bizbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static bizbook.logic.commands.EditNotesCommand.DUPLICATE_MESSAGE_CONSTRAINTS;
import static bizbook.testutil.Assert.assertThrows;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_NOTE;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static bizbook.testutil.TypicalIndexes.INDEX_OUTOFBOUND_NOTE;
import static bizbook.testutil.TypicalIndexes.INDEX_OUTOFBOUND_PERSON;
import static bizbook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static bizbook.testutil.TypicalNotes.DUPLICATE_NOTE;
import static bizbook.testutil.TypicalNotes.TYPICAL_NOTE;
import static bizbook.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import bizbook.logic.Messages;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.AddressBook;
import bizbook.model.Model;
import bizbook.model.ModelManager;
import bizbook.model.UserPrefs;
import bizbook.model.person.Person;
import bizbook.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * EditCommand.
 */
public class EditNotesCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_editNotesCommand_success() throws Exception {
        // Create the person and expected models as in the original
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Define the edited person with updated notes
        Person editedPerson = new PersonBuilder(personToEdit).withNotes(
                TYPICAL_NOTE.getNote(), "Likes dumplings").build();

        // Define the EditNotesCommand
        EditNotesCommand editNotesCommand = new EditNotesCommand(INDEX_FIRST_PERSON, INDEX_FIRST_NOTE, TYPICAL_NOTE);

        // Define the expected message
        String expectedMessage = String.format(EditNotesCommand.MESSAGE_EDIT_NOTES_SUCCESS,
                Messages.format(editedPerson));

        // Create the expected model
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        expectedModel.getFocusedPerson().set(editedPerson);
        model.setPerson(personToEdit, personToEdit);

        assertCommandSuccess(editNotesCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_duplicateNote_throwsCommandException() {
        EditNotesCommand editNotesCommand = new EditNotesCommand(INDEX_SECOND_PERSON, INDEX_FIRST_NOTE, DUPLICATE_NOTE);
        assertThrows(CommandException.class, DUPLICATE_MESSAGE_CONSTRAINTS, () -> editNotesCommand.execute(model));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        EditNotesCommand editNotesCommand = new EditNotesCommand(INDEX_OUTOFBOUND_PERSON, INDEX_FIRST_NOTE,
                DUPLICATE_NOTE);
        assertThrows(CommandException.class, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                editNotesCommand.execute(model));
    }

    @Test
    public void execute_invalidNoteIndex_throwsCommandException() {
        EditNotesCommand editNotesCommand = new EditNotesCommand(INDEX_SECOND_PERSON, INDEX_OUTOFBOUND_NOTE,
                TYPICAL_NOTE);
        assertThrows(CommandException.class, MESSAGE_INVALID_NOTED_INDEX, () -> editNotesCommand.execute(model));
    }
}
