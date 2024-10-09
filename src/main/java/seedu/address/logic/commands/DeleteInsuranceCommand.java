package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.insurance.InsurancePlan;
import seedu.address.model.person.insurance.InsurancePlanFactory;
import seedu.address.model.person.insurance.InsurancePlansManager;

import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;

/**
 * Removes an InsurancePlan from an existing person in the address book.
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
    public static final String MESSAGE_DELETE_INSURANCE_PLAN_SUCCESS = "Deleted Insurance Plan: %1$s, from Client: %2$s";

    private final Index index;
    private final int insuranceID;

    /**
     * @param index of the person in the filtered person list remove the insurance plan from
     * @param insuranceID of the person to be updated to
     */
    public DeleteInsuranceCommand(Index index, int insuranceID) {
        requireAllNonNull(index, insuranceID);

        this.index = index;
        this.insuranceID = insuranceID;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        try {
            InsurancePlansManager personToEditInsurancePlansManager = personToEdit.getInsurancePlansManager();

            InsurancePlan planToBeDeleted = InsurancePlanFactory.createInsurancePlan(insuranceID);

            personToEditInsurancePlansManager.checkIfPlanOwned(planToBeDeleted);

            personToEditInsurancePlansManager.deletePlan(planToBeDeleted);

            return new CommandResult(String.format(MESSAGE_DELETE_INSURANCE_PLAN_SUCCESS,
                    personToEditInsurancePlansManager, Messages.format(personToEdit)));
        } catch (ParseException e) {
            throw new CommandException(
                    String.format(e.getMessage(), insuranceID, Messages.format(personToEdit)));
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
