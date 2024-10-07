package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds an InsurancePlan to an existing person in the address book.
 */
public class AddInsuranceCommand extends Command {
    public static final String COMMAND_WORD = "addInsurance";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an insurance plan to the client identified "
            + "by their name. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + ", INSURANCE_PLAN_ID (must be a valid ID) \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_INSURANCE_ID + " 0";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, InsuranceID: %2$s";

    private final Index index;
    private final int insuranceID;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param insuranceID of the person to be updated to
     */
    public AddInsuranceCommand(Index index, int insuranceID) {
        requireAllNonNull(index, insuranceID);

        this.index = index;
        this.insuranceID = insuranceID;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, index.getOneBased(), insuranceID));
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
