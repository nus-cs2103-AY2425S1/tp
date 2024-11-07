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
import seedu.address.model.person.Remark;

public class AddRemarksCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validPatientId_success() {
        Person patientToEdit = model.getFilteredPersonList().get(0);
        int validPatientId = patientToEdit.getId();
        String newNotes = "New notes added.";
        Remark newRemarks = new Remark(newNotes);

        AddRemarksCommand addRemarksCommand = new AddRemarksCommand(validPatientId, newRemarks);

        String expectedMessage = String.format(AddRemarksCommand.MESSAGE_ADD_REMARKS_SUCCESS,
                newRemarks, validPatientId);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Person expectedPatient = expectedModel.getFilteredPersonList().get(0);
        expectedPatient.addRemarks(newNotes);

        assertCommandSuccess(addRemarksCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPatientId_throwsCommandException() {
        int invalidPatientId = 9999;
        AddRemarksCommand addRemarksCommand = new AddRemarksCommand(invalidPatientId, new Remark("Some notes"));
        String expectedMessage = "Unable to add remarks! Check the id entered!";

        assertCommandFailure(addRemarksCommand, model, expectedMessage);
    }

    @Test
    public void execute_nullNotes_throwsCommandException() {
        Person patientToEdit = model.getFilteredPersonList().get(0);
        int validPatientId = patientToEdit.getId();

        assertThrows(NullPointerException.class, () -> new AddRemarksCommand(validPatientId, null));
    }
}
