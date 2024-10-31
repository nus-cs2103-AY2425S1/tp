package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLOSINGHOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPENINGHOURS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.OperatingHours;

/**
 * Updates operating hours in addressbook
 */
public class UpdateOperatingHoursCommand extends Command {

    public static final String COMMAND_WORD = "hours";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates operating hours in the address book.\n"
            + "Parameters: " + "[" + PREFIX_OPENINGHOURS + "08:30] [" + PREFIX_CLOSINGHOURS + "18:30] \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_OPENINGHOURS + "08:30 " + PREFIX_CLOSINGHOURS + "18:30";
  
    public static final String MESSAGE_SUCCESS = "Operating Hours updated: ";
    public static final String MESSAGE_FAILED = "There are some appointments are "
            + "outside the new operating hours given";

    public final OperatingHours toUpdate;

    /**
     * creates an UpdateOperatingHoursCommand to update the  operating hours in addressbook
     */
    public UpdateOperatingHoursCommand(OperatingHours operatingHours) {
        requireNonNull(operatingHours);
        toUpdate = operatingHours;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.setOperatingHours(toUpdate.getOpeningHour(), toUpdate.getClosingHour())) {
            return new CommandResult(MESSAGE_SUCCESS + toUpdate);
        } else {
            throw new CommandException(MESSAGE_FAILED);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateOperatingHoursCommand)) {
            return false;
        }

        UpdateOperatingHoursCommand otherUpdateOperatingHoursCommand = (UpdateOperatingHoursCommand) other;
        return toUpdate.equals(otherUpdateOperatingHoursCommand.toUpdate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toUpdate", toUpdate)
                .toString();
    }
}
