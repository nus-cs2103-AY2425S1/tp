package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertInsuranceCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CLIENT;

import org.junit.jupiter.api.Test;

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
import seedu.address.model.client.insurance.InvalidInsurancePlan;
import seedu.address.model.client.insurance.claim.Claim;

class AddClaimCommandTest {

    public static final int VALID_INSURANCE_ID_FIRST = 0;
    public static final int VALID_INSURANCE_ID_SECOND = 1;
    public static final String VALID_CLAIM_ID_FIRST = "A1001";
    public static final String VALID_CLAIM_ID_SECOND = "B1234";
    public static final int VALID_CLAIM_AMOUNT_FIRST = 10000;
    public static final int VALID_CLAIM_AMOUNT_SECOND = 20000;
    public static final int INVALID_CLAIM_AMOUNT = -1;

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests the execution of {@code AddClaimCommand} with a valid index, valid insurance ID and valid Claim
     * This test verifies that when a valid client index, valid insurance plan and valid Claim are provided,
     * the valid Claim is successfully added to the specified client.
     */
    @Test
    public void execute_validIndexUnfilteredList_success() throws
            InsurancePlanException, ClaimException {
        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // Using Benny, who has basic insurance plan and travel insurance plan but no claims
        Client clientToEdit = model.getFilteredClientList().get(INDEX_SECOND_CLIENT.getZeroBased());
        InsurancePlansManager originalInsurancePlansManager = clientToEdit.getInsurancePlansManager();
        InsurancePlan insurancePlan = originalInsurancePlansManager.getInsurancePlan(VALID_INSURANCE_ID_FIRST);

        // rebuilds a copy of the claims from string to prevent same-object manipulation
        Claim claim = new Claim(VALID_CLAIM_ID_FIRST, VALID_CLAIM_AMOUNT_FIRST);
        InsurancePlansManager updatedInsurancePlansManager = originalInsurancePlansManager.createCopy();
        updatedInsurancePlansManager.addClaimToInsurancePlan(insurancePlan, claim);

        AddClaimCommand addClaimCommand =
                new AddClaimCommand(INDEX_SECOND_CLIENT, VALID_INSURANCE_ID_FIRST,
                        claim.getClaimId(), claim.getClaimAmount());

        String expectedMessage = String.format(AddClaimCommand.MESSAGE_SUCCESS,
                clientToEdit.getName().toString(), insurancePlan, claim.getClaimId(),
                Messages.formatClaimAmount(claim.getClaimAmount()));

        Client updatedClient = new Client(clientToEdit.getName(), clientToEdit.getPhone(), clientToEdit.getEmail(),
                clientToEdit.getAddress(), updatedInsurancePlansManager, clientToEdit.getTags());

        expectedModel.setClient(clientToEdit, updatedClient);

        assertInsuranceCommandSuccess(addClaimCommand, model, expectedMessage,
                clientToEdit.getInsurancePlansManager(), updatedInsurancePlansManager);
    }

    /**
     * Tests the execution of {@code AddClaimCommand} with a valid index, valid insurance ID and valid Claim
     * that the client already has. This test verifies that when a duplicate claim is provided,
     * a {@code CommandException} will be thrown.
     */
    @Test
    public void execute_duplicateClaim_throwsCommandException() {
        // Carl is the client used for testing, and already has plan
        // "A1001" with claim amount 10000
        String carlClaimId = "A1001";
        int carlClaimAmount = 10000;

        AddClaimCommand failingAddClaimCommand =
                new AddClaimCommand(INDEX_THIRD_CLIENT, 0,
                        carlClaimId, carlClaimAmount);

        assertThrows(CommandException.class, () -> failingAddClaimCommand.execute(model));
    }

    /**
     * Tests the execution of {@code AddClaimCommand} with a valid index, valid insurance ID and
     * invalid Claim (invalid Claim amount). This test verifies that when an invalid claim is provided,
     * a {@code CommandException} will be thrown.
     */
    @Test
    public void execute_invalidClaimAmount_throwsCommandException() {
        AddClaimCommand failingAddClaimCommand =
                new AddClaimCommand(INDEX_THIRD_CLIENT, 0,
                        VALID_CLAIM_ID_FIRST, INVALID_CLAIM_AMOUNT);

        assertThrows(CommandException.class, () -> failingAddClaimCommand.execute(model));
    }

    /**
     * Tests the execution of {@code AddClaimCommand} with a valid index, invalid insurance ID and
     * valid Claim. This test verifies that when an invalid insurance ID is provided,
     * a {@code CommandException} will be thrown.
     */
    @Test
    public void execute_invalidInsurancePlan_throwsCommandException() {
        InsurancePlan fakeInsurancePlan = new InvalidInsurancePlan();

        Claim validClaim = new Claim(VALID_CLAIM_ID_FIRST, VALID_CLAIM_AMOUNT_FIRST);
        AddClaimCommand failingAddClaimCommand = new AddClaimCommand(INDEX_THIRD_CLIENT,
                fakeInsurancePlan.getInsurancePlanId(), validClaim.getClaimId(),
                validClaim.getClaimAmount());

        assertThrows(CommandException.class, () -> failingAddClaimCommand.execute(model));
    }

    /**
     * Tests the {@code equals} method to determine that it produces the right output
     */
    @Test
    public void equals() {
        AddClaimCommand addClaimFirstCommand =
                new AddClaimCommand(INDEX_SECOND_CLIENT, VALID_INSURANCE_ID_FIRST,
                        VALID_CLAIM_ID_FIRST, VALID_CLAIM_AMOUNT_FIRST);
        AddClaimCommand addClaimSecondCommand =
                new AddClaimCommand(INDEX_SECOND_CLIENT, VALID_INSURANCE_ID_SECOND,
                        VALID_CLAIM_ID_SECOND, VALID_CLAIM_AMOUNT_FIRST);
        AddClaimCommand addClaimThirdCommand =
                new AddClaimCommand(INDEX_SECOND_CLIENT, VALID_INSURANCE_ID_SECOND,
                        VALID_CLAIM_ID_SECOND, VALID_CLAIM_AMOUNT_SECOND);

        // same object -> returns true
        assertEquals(addClaimFirstCommand, addClaimFirstCommand);

        // same values -> returns true
        AddClaimCommand addClaimFirstCommandCopy =
                new AddClaimCommand(INDEX_SECOND_CLIENT, VALID_INSURANCE_ID_FIRST,
                        VALID_CLAIM_ID_FIRST, VALID_CLAIM_AMOUNT_FIRST);
        assertEquals(addClaimFirstCommand, addClaimFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addClaimFirstCommand);

        // null -> returns false
        assertNotEquals(null, addClaimFirstCommand);

        // different insurance/claim id -> returns false
        assertNotEquals(addClaimFirstCommand, addClaimSecondCommand);

        // same claim id, different claim amount -> returns false
        assertNotEquals(addClaimSecondCommand, addClaimThirdCommand);
    }
}

