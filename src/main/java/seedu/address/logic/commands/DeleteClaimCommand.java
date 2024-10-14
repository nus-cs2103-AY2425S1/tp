package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLIENTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.exceptions.ClaimException;
import seedu.address.model.client.exceptions.InsurancePlanException;
import seedu.address.model.client.insurance.InsurancePlan;
import seedu.address.model.client.insurance.InsurancePlanFactory;
import seedu.address.model.client.insurance.InsurancePlansManager;
import seedu.address.model.client.insurance.claim.Claim;

/**
 * Removes a claim from an existing client with existing Insurance Plan in the address book.
 */
public class DeleteClaimCommand extends Command {
    public static final String COMMAND_WORD = "deleteClaim";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a claim from the insurance plan of the client identified "
            + "by their client id. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + " INSURANCE_PLAN_ID (must be a valid ID), "
            + " Claim_ID (must be a valid ID), "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_INSURANCE_ID + " 0"
            + PREFIX_CLAIM_ID + " B1234 ";

    public static final String MESSAGE_DELETE_CLAIM_SUCCESS =
            "Claim deleted from Client: %1$s, under Insurance plan %2$s, with Claim ID: %3$s";

    public final Index index;
    private final int insuranceId;
    private final String claimID;

    /**
     * Constructs a DeleteClaimCommand object with the values passed in by the user.
     *
     * @param index       of the client in the filtered client list to add the claim to.
     * @param insuranceId of insurance plan the claim is to be added to.
     * @param claimID     the claimID received when a claim is created through official channels.
     */
    public DeleteClaimCommand(Index index, int insuranceId, final String claimID) {
        requireAllNonNull(index, insuranceId, claimID);

        this.index = index;
        this.insuranceId = insuranceId;
        this.claimID = claimID;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToEdit = lastShownList.get(index.getZeroBased());

        try {
            InsurancePlan planToBeUsed = InsurancePlanFactory.createInsurancePlan(insuranceId);

            InsurancePlansManager clientToEditInsurancePlansManager = clientToEdit.getInsurancePlansManager();
            clientToEditInsurancePlansManager.checkIfPlanOwned(planToBeUsed);

            Claim claimToBeDeleted = new Claim(claimID);
            clientToEditInsurancePlansManager.checkIfClaimOwned(planToBeUsed, claimToBeDeleted);
            clientToEditInsurancePlansManager.deleteClaimFromInsurancePlan(planToBeUsed, claimToBeDeleted);

            Client clientWithDeletedClaim = lastShownList.get(index.getZeroBased());
            model.setClient(clientToEdit, clientWithDeletedClaim);
            model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);

            return new CommandResult(String.format(MESSAGE_DELETE_CLAIM_SUCCESS, Messages.format(clientToEdit),
                    planToBeUsed, claimID));
        } catch (ClaimException e) {
            throw new CommandException(String.format(e.getMessage(), claimID, Messages.format(clientToEdit)));
        } catch (InsurancePlanException e) {
            throw new CommandException(String.format(e.getMessage(), insuranceId, Messages.format(clientToEdit)));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteClaimCommand e)) {
            return false;
        }

        return index.equals(e.index) && insuranceId == e.insuranceId && claimID.equals(e.claimID);
    }
}
