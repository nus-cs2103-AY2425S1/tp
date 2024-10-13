package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.insurance.InsurancePlan;
import seedu.address.model.person.insurance.InsurancePlanFactory;
import seedu.address.model.person.insurance.InsurancePlansManager;

/**
 * Adds an InsurancePlan to an existing person in the address book.
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
    private final int insuranceID;

    /**
     * Constructs an AddInsuranceCommand Object with an Index Object and an integer for insuranceId.
     *
     * @param index       of the person in the filtered person list to add the insurance plan to.
     * @param insuranceID of insurance plan to be added to the person.
     */
    public AddInsuranceCommand(Index index, int insuranceID) {
        requireAllNonNull(index, insuranceID);

        this.index = index;
        this.insuranceID = insuranceID;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        try {
            InsurancePlan planToBeAdded = InsurancePlanFactory.createInsurancePlan(insuranceID);

            InsurancePlansManager personToEditInsurancePlansManager = personToEdit.getInsurancePlansManager();
            personToEditInsurancePlansManager.checkIfPlanNotOwned(planToBeAdded);
            personToEditInsurancePlansManager.addPlan(planToBeAdded);

            Person personWithAddedInsurancePlan = lastShownList.get(index.getZeroBased());
            model.setPerson(personToEdit, personWithAddedInsurancePlan);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(String.format(MESSAGE_ADD_INSURANCE_PLAN_SUCCESS,
                    planToBeAdded, Messages.format(personWithAddedInsurancePlan)));
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
        if (!(other instanceof AddInsuranceCommand e)) {
            return false;
        }

        return index.equals(e.index) && insuranceID == e.insuranceID;
    }
}
