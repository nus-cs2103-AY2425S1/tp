package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds an InsurancePlan to an existing person in the address book.
 */
public class AddInsuranceCommand extends Command {
    public static final String COMMAND_WORD = "addInsurance";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Add Insurance command not implemented yet";

    public static final String MESSAGE_ARGUMENTS = "Client Name: %1$s, InsuranceID: %2$d";

    private final String clientName;
    private final int insuranceID;

    /**
     * @param clientName of the person in the filtered person list to edit the remark
     * @param insuranceID of the person to be updated to
     */
    public AddInsuranceCommand(String clientName, int insuranceID) {
        requireAllNonNull(clientName, insuranceID);

        this.clientName = clientName;
        this.insuranceID = insuranceID;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, clientName, insuranceID));
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

        return clientName.equals(e.clientName) && insuranceID == e.insuranceID;
    }
}
