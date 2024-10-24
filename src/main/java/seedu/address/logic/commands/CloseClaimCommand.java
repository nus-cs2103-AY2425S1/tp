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
import seedu.address.model.client.insurance.InsurancePlansManager;
import seedu.address.model.client.insurance.claim.Claim;

/**
 * Marks a claim tagged to a Client's insurance plan as "closed"
 */
public class CloseClaimCommand extends Command {
    public static final String COMMAND_WORD = "closeClaim";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a claim tagged to an insurance plan of a client as [closed] \n"
            + "Parameters: INDEX (must be a positive integer) "
            + " INSURANCE_PLAN_ID (must be a valid ID), "
            + " ClAIM_ID (must be a valid ID) \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_INSURANCE_ID + " 0 "
            + PREFIX_CLAIM_ID + " B1234";

    public static final String MESSAGE_CLOSE_CLAIM_SUCCESS =
            "Claim from Client: %1$s, under Insurance plan %2$s, with Claim ID: %3$s marked as closed";

    public final Index index;
    private final int insuranceId;
    private final String claimId;

    /**
     * Constructs a CloseClaimCommand object with the values passed in by the user.
     *
     * @param index       of the client in the filtered client list whose claim to be marked as closed.
     * @param insuranceId of insurance plan that contains the claim is to be marked as closed.
     * @param claimId     the claimID received when a claim is created through official channels.
     */
    public CloseClaimCommand(Index index, int insuranceId, final String claimId) {
        requireAllNonNull(index, insuranceId, claimId);

        this.index = index;
        this.insuranceId = insuranceId;
        this.claimId = claimId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToCloseClaim = lastShownList.get(index.getZeroBased());

        try {
            InsurancePlansManager clientToEditInsurancePlansManager = clientToCloseClaim.getInsurancePlansManager();
            InsurancePlan planToBeUsed = clientToEditInsurancePlansManager.getInsurancePlan(insuranceId);
            clientToEditInsurancePlansManager.checkIfPlanOwned(planToBeUsed);

            Claim claimToBeMarkedAsClosed = planToBeUsed.getClaim(claimId);
            clientToEditInsurancePlansManager.closeClaim(planToBeUsed, claimToBeMarkedAsClosed);

            Client clientWithClosedClaim = lastShownList.get(index.getZeroBased());
            model.setClient(clientToCloseClaim, clientWithClosedClaim);
            model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);

            return new CommandResult(String.format(MESSAGE_CLOSE_CLAIM_SUCCESS, clientToCloseClaim.getName().toString(),
                    planToBeUsed, claimId));
        } catch (ClaimException e) {
            throw new CommandException(String.format(e.getMessage(), claimId, Messages.format(clientToCloseClaim)));
        } catch (InsurancePlanException e) {
            throw new CommandException(String.format(e.getMessage(), insuranceId, Messages.format(clientToCloseClaim)));
        }
    }
}
