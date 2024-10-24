package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_AMOUNT;
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
 * Adds a claim to an existing client with existing Insurance Plan in the address book.
 */
public class AddClaimCommand extends Command {
    public static final String COMMAND_WORD = "addClaim";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an open claim to the insurance plan of the client identified "
            + "by their client id. \n"
            + "Parameters: INDEX (must be a positive integer), "
            + " INSURANCE_PLAN_ID (must be a valid ID), "
            + " Claim_ID (must be a valid ID), "
            + " Claim amount (must be a valid monetary value without the dollar sign) \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_INSURANCE_ID + " 0 "
            + PREFIX_CLAIM_ID + " B1234 "
            + PREFIX_CLAIM_AMOUNT + " 100.00";

    public static final String MESSAGE_SUCCESS =
            "New claim added to Client: %1$s, under Insurance plan: %2$s, with Claim ID: %3$s, Claim Amount: %4$s";

    public final Index index;
    private final int insuranceId;
    private final String claimId;
    private final int claimAmount;

    /**
     * Constructs an AddClaimCommand object with the values passed in by the user.
     *
     * @param index       of the client in the filtered client list to add the insurance plan to.
     * @param insuranceId of insurance plan the claim is to be added to.
     * @param claimId     the claimID received when a claim is created through official channels.
     * @param claimAmount the amount that is being claimed through this claim in cents.
     */
    public AddClaimCommand(Index index, int insuranceId, final String claimId, final int claimAmount) {
        this.index = index;
        this.insuranceId = insuranceId;
        this.claimId = claimId;
        this.claimAmount = claimAmount;
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

            Claim claimToBeAdded = new Claim(claimId, claimAmount);
            clientToEditInsurancePlansManager.addClaimToInsurancePlan(planToBeUsed, claimToBeAdded);

            Client clientWithAddedClaim = lastShownList.get(index.getZeroBased());
            model.setClient(clientToEdit, clientWithAddedClaim);
            model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);

            return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(clientToEdit), planToBeUsed,
                    claimId, Messages.formatClaimAmount(claimAmount)));
        } catch (ClaimException e) {
            throw new CommandException(String.format(e.getMessage(), claimId, Messages.format(clientToEdit)));
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
        if (!(other instanceof AddClaimCommand)) {
            return false;
        }

        AddClaimCommand otherAddClaimCommand = (AddClaimCommand) other;
        return index.equals(otherAddClaimCommand.index)
                && insuranceId == otherAddClaimCommand.insuranceId
                && claimId.equals(otherAddClaimCommand.claimId)
                && claimAmount == otherAddClaimCommand.claimAmount;
    }
}
