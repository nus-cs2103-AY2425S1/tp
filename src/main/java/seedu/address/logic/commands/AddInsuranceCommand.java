package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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
 * Adds an InsurancePlan to an existing client in the address book.
 */
public class AddInsuranceCommand extends Command {
    public static final String COMMAND_WORD = "addInsurance";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an insurance plan to the client identified "
            + "by their client id. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + ", INSURANCE_PLAN_ID (must be a valid ID) \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_INSURANCE_ID + " 0";

    public static final String MESSAGE_ADD_INSURANCE_PLAN_SUCCESS = "Added Insurance Plan: %1$s, to Client: %2$s";

    private final Index index;
    private final int insuranceId;

    /**
     * Constructs an AddInsuranceCommand Object with an Index Object and an integer for insuranceId.
     *
     * @param index       of the client in the filtered client list to add the insurance plan to.
     * @param insuranceId of insurance plan to be added to the client.
     */
    public AddInsuranceCommand(Index index, int insuranceId) {
        requireAllNonNull(index, insuranceId);

        this.index = index;
        this.insuranceId = insuranceId;
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
            InsurancePlan planToBeAdded = InsurancePlanFactory.createInsurancePlan(insuranceId);

            InsurancePlansManager clientToEditInsurancePlansManager = clientToEdit.getInsurancePlansManager();
            clientToEditInsurancePlansManager.checkIfPlanNotOwned(planToBeAdded);
            clientToEditInsurancePlansManager.addPlan(planToBeAdded);

            Client clientWithAddedInsurancePlan = lastShownList.get(index.getZeroBased());
            model.setClient(clientToEdit, clientWithAddedInsurancePlan);
            model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);

            return new CommandResult(String.format(MESSAGE_ADD_INSURANCE_PLAN_SUCCESS,
                    planToBeAdded, clientWithAddedInsurancePlan.getName().toString()));
        } catch (InsurancePlanException e) {
            throw new CommandException(
                    String.format(e.getMessage(), insuranceId, clientToEdit.getName().toString()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddInsuranceCommand e)) {
            return false;
        }

        return index.equals(e.index) && insuranceId == e.insuranceId;
    }
}
