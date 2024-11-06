package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLIENTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.exceptions.InsurancePlanException;
import seedu.address.model.client.insurance.InsurancePlan;
import seedu.address.model.client.insurance.InsurancePlanFactory;
import seedu.address.model.client.insurance.InsurancePlansManager;

/**
 * Removes an InsurancePlan from an existing client in the address book.
 */
public class DeleteInsuranceCommand extends Command {
    public static final String COMMAND_WORD = "deleteInsurance";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an insurance plan from a client identified "
            + "by their client id. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + ", INSURANCE_PLAN_ID (must be a valid ID) \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_INSURANCE_ID + " 0";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, InsuranceID: %2$s";
    public static final String MESSAGE_DELETE_INSURANCE_PLAN_SUCCESS =
            "Deleted Insurance Plan: %1$s, from Client: %2$s";

    private final Index index;
    private final int insuranceID;

    /**
     * @param index       of the client in the filtered client list remove the insurance plan from
     * @param insuranceID of the client to be updated to
     */
    public DeleteInsuranceCommand(Index index, int insuranceID) {
        requireAllNonNull(index, insuranceID);

        this.index = index;
        this.insuranceID = insuranceID;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Client> lastShownList = model.getFilteredClientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToEdit = lastShownList.get(index.getZeroBased());

        try {
            InsurancePlan planToBeDeleted = InsurancePlanFactory.createInsurancePlan(insuranceID);

            InsurancePlansManager clientToEditInsurancePlansManager = clientToEdit.getInsurancePlansManager();
            clientToEditInsurancePlansManager.checkIfPlanOwned(planToBeDeleted);
            clientToEditInsurancePlansManager.deletePlan(planToBeDeleted);

            Client clientWithDeletedInsurancePlan = lastShownList.get(index.getZeroBased());

            model.setClient(clientToEdit, clientWithDeletedInsurancePlan);
            model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);

            return new CommandResult(String.format(MESSAGE_DELETE_INSURANCE_PLAN_SUCCESS,
                    planToBeDeleted, clientWithDeletedInsurancePlan.getName().toString()));
        } catch (InsurancePlanException e) {
            throw new CommandException(
                    String.format(e.getMessage(), insuranceID, clientToEdit.getName().toString()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteInsuranceCommand e)) {
            return false;
        }

        return index.equals(e.index) && insuranceID == e.insuranceID;
    }
}
