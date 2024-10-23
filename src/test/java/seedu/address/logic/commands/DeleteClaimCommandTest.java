package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException,
            InsurancePlanException, ClaimException {
        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Client clientToEdit = model.getFilteredClientList().get(INDEX_THIRD_CLIENT.getZeroBased());
        InsurancePlan insurancePlan = clientToEdit.getInsurancePlansManager().getInsurancePlan(0);
        Claim claim = clientToEdit.getInsurancePlansManager().getInsurancePlan(0).getClaim("A1001");

        DeleteClaimCommand deleteClaimCommand =
                new DeleteClaimCommand(INDEX_THIRD_CLIENT, 0, claim.getClaimId());

        // make sure all the messages only have client name inside the actual classes
        // because when the message is created here before deletion there can be issues with
        // the string comparison of the client after deletion and before deletion.
        String expectedMessage = String.format(DeleteClaimCommand.MESSAGE_DELETE_CLAIM_SUCCESS,
                clientToEdit.getName().toString(), insurancePlan, claim.getClaimId());

        // rebuilds a copy of the claims from string to prevent same-object manipulation
        String insurancePlanString = insurancePlan.toString();
        String claimsString = clientToEdit.getInsurancePlansManager().convertClaimsToJson();
        InsurancePlansManager updatedInsurancePlanManager = new InsurancePlansManager(insurancePlanString);
        updatedInsurancePlanManager.addAllClaimsFromJson(claimsString);
        updatedInsurancePlanManager.deleteClaimFromInsurancePlan(insurancePlan, claim);

        Client updatedClient = new Client(clientToEdit.getName(), clientToEdit.getPhone(), clientToEdit.getEmail(),
                clientToEdit.getAddress(), updatedInsurancePlanManager, clientToEdit.getTags());

        expectedModel.setClient(clientToEdit, updatedClient);

        assertCommandSuccess(deleteClaimCommand, model, expectedMessage, expectedModel);
    }
}
