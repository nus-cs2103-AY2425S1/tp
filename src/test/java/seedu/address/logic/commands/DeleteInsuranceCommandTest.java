package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.exceptions.ClaimException;
import seedu.address.model.client.exceptions.InsurancePlanException;
import seedu.address.model.client.insurance.InsurancePlan;
import seedu.address.model.client.insurance.InsurancePlansManager;

class DeleteInsuranceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws InsurancePlanException, ClaimException {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Client clientToEdit = model.getFilteredClientList().get(INDEX_THIRD_CLIENT.getZeroBased());
        InsurancePlansManager insurancePlansManager = clientToEdit.getInsurancePlansManager();
        InsurancePlan insurancePlan = insurancePlansManager.getInsurancePlan(0);

        DeleteInsuranceCommand deleteInsuranceCommand = new DeleteInsuranceCommand(INDEX_THIRD_CLIENT, 0);

        InsurancePlansManager updatedInsurancePlansManager = new InsurancePlansManager(insurancePlansManager.toString());
        updatedInsurancePlansManager.deletePlan(insurancePlan);

        Client updatedClient = new Client(clientToEdit.getName(), clientToEdit.getPhone(), clientToEdit.getEmail(),
                clientToEdit.getAddress(), updatedInsurancePlansManager, clientToEdit.getTags());
        String expectedMessage = String.format(DeleteInsuranceCommand.MESSAGE_DELETE_INSURANCE_PLAN_SUCCESS,
                insurancePlan, Messages.format(updatedClient));

        expectedModel.setClient(clientToEdit, updatedClient);

        assertCommandSuccess(deleteInsuranceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIndex_throwsCommandException() {
        // Create a DeleteInsuranceCommand with an invalid index
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        DeleteInsuranceCommand deleteInsuranceCommand = new DeleteInsuranceCommand(outOfBoundsIndex, 0);

        // Expect CommandException due to invalid index
        assertThrows(CommandException.class, () -> deleteInsuranceCommand.execute(model));
    }

    @Test
    public void execute_invalidInsurancePlan_throwsCommandException() {
        // Retrieve a valid client
        Client clientToEdit = model.getFilteredClientList().get(INDEX_THIRD_CLIENT.getZeroBased());

        // Create a DeleteInsuranceCommand with an invalid insurance plan ID
        int invalidInsurancePlanId = -1;  // Invalid plan ID
        DeleteInsuranceCommand deleteInsuranceCommand = new DeleteInsuranceCommand(INDEX_THIRD_CLIENT, invalidInsurancePlanId);

        // Expect CommandException due to invalid insurance plan
        assertThrows(CommandException.class, () -> deleteInsuranceCommand.execute(model));
    }

    @Test
    public void equals() {
        Index indexFirst = Index.fromZeroBased(0);
        Index indexSecond = Index.fromZeroBased(1);

        int insuranceIdFirst = 0;
        int insuranceIdSecond = 1;

        DeleteInsuranceCommand deleteFirstCommand = new DeleteInsuranceCommand(indexFirst, insuranceIdFirst);
        DeleteInsuranceCommand deleteFirstCommandCopy = new DeleteInsuranceCommand(indexFirst, insuranceIdFirst);
        DeleteInsuranceCommand deleteSecondCommand = new DeleteInsuranceCommand(indexSecond, insuranceIdSecond);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different index -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);

        // different insurance ID -> returns false
        DeleteInsuranceCommand deleteDifferentIDCommand = new DeleteInsuranceCommand(indexFirst, insuranceIdSecond);
        assertNotEquals(deleteFirstCommand, deleteDifferentIDCommand);

        // null -> returns false
        assertNotEquals(deleteFirstCommand, null);

        // different type -> returns false
        assertNotEquals(deleteFirstCommand, 1);
    }
}

