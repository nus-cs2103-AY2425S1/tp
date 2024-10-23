package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertInsuranceCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.exceptions.ClaimException;
import seedu.address.model.client.exceptions.InsurancePlanException;
import seedu.address.model.client.insurance.InsurancePlan;
import seedu.address.model.client.insurance.InsurancePlansManager;
import seedu.address.model.client.insurance.InvalidInsurancePlan;
import seedu.address.model.client.insurance.claim.Claim;
import seedu.address.model.client.insurance.claim.ClaimStub;

class CloseClaimCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_validIndexUnfilteredList_success() throws CommandException,
            InsurancePlanException, ClaimException {
        Client clientToEdit = model.getFilteredClientList().get(INDEX_THIRD_CLIENT.getZeroBased());
        InsurancePlansManager originalInsurancePlansManager = clientToEdit.getInsurancePlansManager();

        InsurancePlan insurancePlan = clientToEdit.getInsurancePlansManager().getInsurancePlan(0);
        Claim claim = clientToEdit.getInsurancePlansManager().getInsurancePlan(0).getClaim("A1001");

        CloseClaimCommand closeClaimCommand =
                new CloseClaimCommand(INDEX_THIRD_CLIENT, 0, claim.getClaimId());

        // make sure all the messages only have client name inside the actual classes
        // because when the message is created here before modification there can be issues with
        // the string comparison of the client after deletion and before deletion.
        String expectedMessage = String.format(CloseClaimCommand.MESSAGE_CLOSE_CLAIM_SUCCESS,
                clientToEdit.getName().toString(), insurancePlan, claim.getClaimId());

        InsurancePlansManager updatedInsurancePlansManager = originalInsurancePlansManager.createCopy();
        updatedInsurancePlansManager.closeClaim(insurancePlan, claim);

        assertInsuranceCommandSuccess(closeClaimCommand, model, expectedMessage,
                originalInsurancePlansManager, updatedInsurancePlansManager);
    }

    @Test
    void execute_invalidClaim_throwsCommandException() {

        Client clientToEdit = model.getFilteredClientList().get(INDEX_THIRD_CLIENT.getZeroBased());
        try {
            InsurancePlan insurancePlan = clientToEdit.getInsurancePlansManager().getInsurancePlan(0);
        } catch (InsurancePlanException e) {
            fail();
        }

        Claim fakeClaim = new ClaimStub();
        CloseClaimCommand failingCloseClaimCommand =
                new CloseClaimCommand(INDEX_THIRD_CLIENT, 0, fakeClaim.getClaimId());

        assertThrows(CommandException.class, () -> failingCloseClaimCommand.execute(model));
    }

    @Test
    void execute_invalidInsurancePlan_throwsCommandException() {
        Client clientToEdit = model.getFilteredClientList().get(INDEX_THIRD_CLIENT.getZeroBased());

        InsurancePlan fakeInsurancePlan = new InvalidInsurancePlan();

        try {
            Claim validClaim = clientToEdit.getInsurancePlansManager().getInsurancePlan(0).getClaim("A1001");
            CloseClaimCommand failingCloseClaimCommand = new CloseClaimCommand(INDEX_THIRD_CLIENT,
                    fakeInsurancePlan.getInsurancePlanId(), validClaim.getClaimId());

            assertThrows(CommandException.class, () -> failingCloseClaimCommand.execute(model));
        } catch (ClaimException | InsurancePlanException e) {
            fail();
        }
    }
}
