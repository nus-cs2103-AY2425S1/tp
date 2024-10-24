package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class AddNotesCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validPatientId_success() {
        Person patientToEdit = model.getFilteredPersonList().get(0);
        int validPatientId = patientToEdit.getId();
        String newNotes = "New notes added.";

        AddNotesCommand addNotesCommand = new AddNotesCommand(validPatientId, newNotes);

        String expectedMessage = String.format(AddNotesCommand.MESSAGE_ADD_NOTES_SUCCESS, newNotes, validPatientId);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Person expectedPatient = expectedModel.getFilteredPersonList().get(0);
        expectedPatient.addNotes(newNotes);

        assertCommandSuccess(addNotesCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPatientId_throwsCommandException() {
        int invalidPatientId = 9999;
        AddNotesCommand addNotesCommand = new AddNotesCommand(invalidPatientId, "Some notes");
        String expectedMessage = "Unable to add notes! Check the id entered!";

        assertCommandFailure(addNotesCommand, model, expectedMessage);
    }

    @Test
    public void execute_nullNotes_throwsCommandException() {
        Person patientToEdit = model.getFilteredPersonList().get(0);
        int validPatientId = patientToEdit.getId();

        assertThrows(NullPointerException.class, () -> new AddNotesCommand(validPatientId, null));
    }
}
