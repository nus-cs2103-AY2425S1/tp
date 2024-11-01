package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertInsuranceCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CLIENT;

import org.junit.jupiter.api.Test;

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
import seedu.address.model.client.insurance.claim.Claim;

class DeleteClaimCommandTest {

    public static final int VALID_INSURANCE_ID_FIRST = 0;
    public static final int VALID_INSURANCE_ID_SECOND = 1;
    public static final String VALID_CLAIM_ID_FIRST = "A1001";
    public static final String VALID_CLAIM_ID_SECOND = "B1234";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException,
            InsurancePlanException, ClaimException {
        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Client clientToEdit = model.getFilteredClientList().get(INDEX_THIRD_CLIENT.getZeroBased());
        InsurancePlansManager originalInsurancePlansManager = clientToEdit.getInsurancePlansManager();
        InsurancePlan insurancePlan = originalInsurancePlansManager.getInsurancePlan(0);
        Claim claim = originalInsurancePlansManager.getInsurancePlan(0).getClaim("A1001");

        DeleteClaimCommand deleteClaimCommand =
                new DeleteClaimCommand(INDEX_THIRD_CLIENT, 0, claim.getClaimId());

        // make sure all the messages only have client name inside the actual classes
        // because when the message is created here before deletion there can be issues with
        // the string comparison of the client after deletion and before deletion.
        String expectedMessage = String.format(DeleteClaimCommand.MESSAGE_DELETE_CLAIM_SUCCESS,
                clientToEdit.getName().toString(), insurancePlan, claim.getClaimId());

        // rebuilds a copy of the claims from string to prevent same-object manipulation
        InsurancePlansManager updatedInsurancePlansManager = originalInsurancePlansManager.createCopy();
        updatedInsurancePlansManager.deleteClaimFromInsurancePlan(insurancePlan, claim);

        Client updatedClient = new Client(clientToEdit.getName(), clientToEdit.getPhone(), clientToEdit.getEmail(),
                clientToEdit.getAddress(), updatedInsurancePlansManager, clientToEdit.getTags());

        expectedModel.setClient(clientToEdit, updatedClient);

        assertInsuranceCommandSuccess(deleteClaimCommand, model, expectedMessage,
                clientToEdit.getInsurancePlansManager(), updatedInsurancePlansManager);
    }

    @Test
    public void equals() {
        DeleteClaimCommand deleteClaimFirstCommand =
                new DeleteClaimCommand(INDEX_THIRD_CLIENT, VALID_INSURANCE_ID_FIRST, VALID_CLAIM_ID_FIRST);
        DeleteClaimCommand deleteClaimSecondCommand =
                new DeleteClaimCommand(INDEX_THIRD_CLIENT, VALID_INSURANCE_ID_SECOND, VALID_CLAIM_ID_SECOND);

        // same object -> returns true
        assertEquals(deleteClaimFirstCommand, deleteClaimFirstCommand);

        // same values -> returns true
        DeleteClaimCommand deleteClaimFirstCommandCopy =
                new DeleteClaimCommand(INDEX_THIRD_CLIENT, VALID_INSURANCE_ID_FIRST, VALID_CLAIM_ID_FIRST);
        assertEquals(deleteClaimFirstCommand, deleteClaimFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteClaimFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteClaimFirstCommand);

        // different insurance/claim -> returns false
        assertNotEquals(deleteClaimFirstCommand, deleteClaimSecondCommand);
    }
}

