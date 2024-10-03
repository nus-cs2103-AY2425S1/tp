package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the remark of an existing person in the address book.
 */
public class EmergencyContactCommand extends Command {

    public static final String COMMAND_WORD = "emergency";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the emergency contact details of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NAME + "[EMERGENCY CONTACT NAME]"
            + PREFIX_PHONE + "[EMERGENCY CONTACT NUMBER\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Richard Ng "
            + PREFIX_PHONE + "82943718";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Emergency contact command not implemented yet";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Emergency Contact Name: %2$s, "
            + "Emergency Contact Number: %3$s";

    private final Index index;
    private final String emergencyContactName;
    private final String emergencyContactNumber;

    /**
     * @param index of the person in the filtered person list to edit the emergency contact details
     * @param emergencyContactName of the person to be updated to
     * @param emergencyContactNumber of the person to be updated to
     */
    public EmergencyContactCommand(Index index, String emergencyContactName, String emergencyContactNumber) {
        requireAllNonNull(index, emergencyContactName, emergencyContactNumber);
        this.index = index;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactNumber = emergencyContactNumber;
    }

    @Override
    public CommandResult execute (Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), emergencyContactName,
                emergencyContactNumber));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof EmergencyContactCommand)) {
            return false;
        }
        // state check
        EmergencyContactCommand e = (EmergencyContactCommand) other;
        return index.equals(e.index)
                && emergencyContactName.equals(e.emergencyContactName)
                && emergencyContactNumber.equals(e.emergencyContactNumber);
    }
}
