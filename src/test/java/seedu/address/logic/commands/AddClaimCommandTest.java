package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertInsuranceCommandSuccess;
import static seedu.address.model.client.insurance.InsurancePlanFactory.INVALID_PLAN_ID_MESSAGE;
import static seedu.address.model.client.insurance.InsurancePlansManager.DUPLICATE_PLAN_DETECTED_MESSAGE;
import static seedu.address.testutil.TypicalClients.BENSON;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.insurance.InsurancePlan;
import seedu.address.model.client.insurance.InsurancePlanFactory;
import seedu.address.model.client.insurance.InsurancePlansManager;
import seedu.address.model.client.insurance.claim.Claim;
import seedu.address.testutil.ClientBuilder;

class AddClaimCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests the execution of {@code AddClaimCommand} with a valid index, valid insurance ID and valid claim ID.
     * This test verifies that when a valid client index, valid insurance plan and valid claim is provided,
     * the valid insurance claim is successfully added to the insurance plan of the specified client.
     */
    @Test
    public void execute_validIndexValidInsuranceIdValidClaimID_success() throws Exception {
        // JUSTIN (Sixth client) has the Basic Insurance Plan, and no claims
        Client originalClient = model.getFilteredClientList().get(INDEX_SIXTH_CLIENT.getZeroBased());
        InsurancePlansManager originalInsurancePlansManager = originalClient.getInsurancePlansManager();

        // Assume valid insurance plan
        int validInsuranceId = 0;
        InsurancePlan validInsurancePlan = originalInsurancePlansManager.getInsurancePlan(validInsuranceId);

        String validClaimId = "C2103";
        int validClaimAmount = 10015;
        Claim validClaim = new Claim(validClaimId, validClaimAmount);

        // Create a copy of the client with the valid claim added
        Client updatedClient = new ClientBuilder(originalClient)
                .withClaims(validClaim.toString())
                .build();

        AddClaimCommand addClaimCommand = new AddClaimCommand(INDEX_SIXTH_CLIENT, validInsuranceId,
                validClaimId, validClaimAmount);

        String expectedMessage = String.format(AddClaimCommand.MESSAGE_SUCCESS, Messages.format(updatedClient), validInsurancePlan,
                validClaimId, validClaimAmount);

        InsurancePlansManager updatedInsurancePlansManager = originalInsurancePlansManager.createCopy();
        updatedInsurancePlansManager.addClaimToInsurancePlan(validInsurancePlan, validClaim);

        assertInsuranceCommandSuccess(addClaimCommand, model, expectedMessage,
                originalInsurancePlansManager, updatedInsurancePlansManager);
    }

    /**
     * Tests the scenario of adding multiple insurance plans to a client.
     * This test verifies that after adding an insurance plan to a client, a different insurance plan can be added to
     * the same client successfully. This test ensures that a client can own multiple insurance plans.
     */
    @Test
    public void execute_multipleInsurancePlans_success() throws Exception {
        // ELLE (fifth client) does not have any insurance plans
        Client originalClient = model.getFilteredClientList().get(INDEX_FIFTH_CLIENT.getZeroBased());
        InsurancePlansManager originalInsurancePlansManager = originalClient.getInsurancePlansManager();

        // Add first insurance plan (basic insurance plan)
        int firstInsuranceId = 0;
        InsurancePlan firstPlan = InsurancePlanFactory.createInsurancePlan(firstInsuranceId);
        AddInsuranceCommand firstAddCommand = new AddInsuranceCommand(INDEX_FIFTH_CLIENT, firstInsuranceId);

        // Create intermediate client with first insurance plan added
        Client clientWithFirstPlan = new ClientBuilder(originalClient)
                .withInsurancePlansManager(firstPlan.toString())
                .build();

        String firstExpectedMessage = String.format(AddInsuranceCommand.MESSAGE_ADD_INSURANCE_PLAN_SUCCESS,
                firstPlan, Messages.format(clientWithFirstPlan));

        InsurancePlansManager updatedInsurancePlansManager = originalInsurancePlansManager.createCopy();
        updatedInsurancePlansManager.addPlan(firstPlan);

        assertInsuranceCommandSuccess(firstAddCommand, model, firstExpectedMessage,
                originalInsurancePlansManager, updatedInsurancePlansManager);

        // Second insurance plan (travel insurance plan)
        int secondInsuranceId = 1;
        InsurancePlan secondPlan = InsurancePlanFactory.createInsurancePlan(secondInsuranceId);
        AddInsuranceCommand secondAddCommand = new AddInsuranceCommand(INDEX_FIFTH_CLIENT, secondInsuranceId);

        // Create final client with both insurance plans
        Client clientWithBothPlans = new ClientBuilder(clientWithFirstPlan)
                .withInsurancePlansManager(firstPlan + ", " + secondPlan)
                .build();

        String secondExpectedMessage = String.format(AddInsuranceCommand.MESSAGE_ADD_INSURANCE_PLAN_SUCCESS,
                secondPlan, Messages.format(clientWithBothPlans));

        updatedInsurancePlansManager.addPlan(secondPlan);

        assertInsuranceCommandSuccess(secondAddCommand, model, secondExpectedMessage,
                originalInsurancePlansManager, updatedInsurancePlansManager);
    }

    /**
     * Tests the execution of {@code AddInsuranceCommand} with an invalid index.
     * This test verifies that when an invalid client index is provided, the command throws a {@code CommandException}.
     */
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // Index provided is greater than total number of clients
        Index indexOutOfBounds = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        AddInsuranceCommand addInsuranceCommand =
                new AddInsuranceCommand(indexOutOfBounds, 0);

        assertCommandFailure(addInsuranceCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    /**
     * Tests the execution of {@code AddInsuranceCommand} with a valid insurance ID
     * that has already been added to the client's list of insurance plans.
     * This test verifies that when the specified insurance plan has already been added to the client's
     * list of insurance plans, the command throws a {@code CommandException}.
     */
    @Test
    public void execute_duplicateInsurancePlan_throwsCommandException() {
        // BENSON (second client) already has both insurance plans from TypicalClients
        int basicInsuranceId = 0;
        AddInsuranceCommand addInsuranceCommand = new AddInsuranceCommand(INDEX_SECOND_CLIENT, basicInsuranceId);

        assertCommandFailure(addInsuranceCommand, model,
                String.format(DUPLICATE_PLAN_DETECTED_MESSAGE,
                        basicInsuranceId, Messages.format(BENSON)));
    }

    /**
     * Tests the execution of {@code AddInsuranceCommand} with an invalid insurance ID.
     * This test verifies that when the specified insurance plan does not exist,
     * the command throws a {@code CommandException}.
     */
    @Test
    public void execute_invalidInsuranceId_throwsCommandException() {
        int invalidInsuranceId = -1;
        AddInsuranceCommand addInsuranceCommand = new AddInsuranceCommand(INDEX_FIRST_CLIENT, invalidInsuranceId);

        assertCommandFailure(addInsuranceCommand, model, String.format(INVALID_PLAN_ID_MESSAGE));
    }

    @Test
    public void equals() {
        AddInsuranceCommand addInsuranceFirstCommand = new AddInsuranceCommand(INDEX_FIRST_CLIENT, 0);
        AddInsuranceCommand addInsuranceSecondCommand = new AddInsuranceCommand(INDEX_SECOND_CLIENT, 1);

        // same object -> returns true
        assertEquals(addInsuranceFirstCommand, addInsuranceFirstCommand);

        // same values -> returns true
        AddInsuranceCommand addInsuranceFirstCommandCopy = new AddInsuranceCommand(INDEX_FIRST_CLIENT, 0);
        assertEquals(addInsuranceFirstCommand, addInsuranceFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addInsuranceFirstCommand);

        // null -> returns false
        assertNotEquals(null, addInsuranceFirstCommand);

        // different client/insurance -> returns false
        assertNotEquals(addInsuranceFirstCommand, addInsuranceSecondCommand);
    }
}
