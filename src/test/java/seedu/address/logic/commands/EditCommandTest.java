package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DESC_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_TO_REMOVE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalClinicConnectSystem;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.ShowPatientInfoCommandResult;
import seedu.address.model.ClinicConnectSystem;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.EditPatientDescriptorBuilder;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.TypicalPatients;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());

    private Patient targetPatient = TypicalPatients.KEANU;

    @Test
    public void execute_allFieldsSpecified_success() {
        Patient editedPatient = new PatientBuilder().build();
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(editedPatient).build();
        EditCommand editCommand = new EditCommand(targetPatient.getNric(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient.getName());
        CommandResult expectedCommandResult =
                new ShowPatientInfoCommandResult(expectedMessage, editedPatient, true);

        Model expectedModel = new ModelManager(
                new ClinicConnectSystem(model.getClinicConnectSystem()), new UserPrefs());
        expectedModel.setPatient(targetPatient, editedPatient);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Patient editedPatient = new PatientBuilder().withName("Keanu Reeves Tan").withNric("S9712385J").withSex("M")
                .withBirthdate("1997-11-30").withPhone("86526969")
                .withEmail("keanureeves@example.com").withAddress("Blk 512 Ang Mo Kio Ave 2").withBloodType("O+")
                .withNokName("Mila Kunis").withNokPhone("84126990").withAllergies("peanuts, cake").withHealthRisk("LOW")
                .withExistingCondition("diabetes").withNote("Patient has previous gunshot wound to chest").build();

        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName("Keanu Reeves Tan")
                .withNric("S9712385J").withAllergiesToAdd("peanuts, cake")
                .build();
        EditCommand editCommand = new EditCommand(targetPatient.getNric(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient.getName());
        CommandResult expectedCommandResult = new ShowPatientInfoCommandResult(expectedMessage, editedPatient, true);

        Model expectedModel = new ModelManager(
                new ClinicConnectSystem(model.getClinicConnectSystem()), new UserPrefs());
        expectedModel.setPatient(targetPatient, editedPatient);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicatePatient_failure() {
        EditCommand editCommand = new EditCommand(TypicalPatients.ALICE.getNric(), DESC_KEANU);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_invalidPatient_failure() {
        Nric invalidNric = new Nric("T0895432A");
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(invalidNric, descriptor);

        assertCommandFailure(editCommand, model, String.format(Messages.MESSAGE_INVALID_PATIENT_NRIC, invalidNric));
    }

    @Test
    public void execute_invalidAllergiesToAdd_failure() {
        // Allergies to add already exists in patient's list of allergies
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withAllergiesToAdd(VALID_ALLERGIES_KEANU).build();
        EditCommand editCommand = new EditCommand(TypicalPatients.KEANU.getNric(), descriptor);
        assertCommandFailure(editCommand, model,
                String.format(Messages.MESSAGE_INVALID_ALLERGY_TO_ADD, VALID_ALLERGIES_KEANU));
    }

    @Test
    public void execute_invalidAllergiesToRemove_failure() {
        // Allergies to remove does not exist in patient's list of allergies
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withAllergiesToRemove(VALID_ALLERGIES_TO_REMOVE_AMY).build();
        EditCommand editCommand = new EditCommand(TypicalPatients.KEANU.getNric(), descriptor);
        assertCommandFailure(editCommand, model,
                String.format(Messages.MESSAGE_INVALID_ALLERGY_TO_DELETE, VALID_ALLERGIES_TO_REMOVE_AMY));
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(DESC_AMY.getNric().get(), DESC_AMY);

        // same values -> returns true
        EditPatientDescriptor copyDescriptor = new EditPatientDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(DESC_AMY.getNric().get(), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(DESC_BOB.getNric().get(), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(DESC_AMY.getNric().get(), DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        EditPatientDescriptor editPatientDescriptor = new EditPatientDescriptor();
        EditCommand editCommand = new EditCommand(targetPatient.getNric(), editPatientDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{NRIC=" + targetPatient.getNric()
                + ", editPatientDescriptor=" + editPatientDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
