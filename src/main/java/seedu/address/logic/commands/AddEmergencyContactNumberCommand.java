package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Changes the emergency contact number of an existing student in the address book
 */
public class AddEmergencyContactNumberCommand extends Command {

    public static final String COMMAND_WORD = "addEmergencyContactNumber";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an emergency contact number to the student "
            + "identified by the name. "
            + "Parameters: n/NAME en/[EMERGENCY_NUMBER]\n"
            + "Example: " + COMMAND_WORD + "n/Henry en/91234567";

    public static final String MESSAGE_ARGUMENTS = "Name: %1$s, ECNumber: %2$s";

    private final Name name;
    private final Phone phone;

    /**
     * @param name name of the person in the filtered person list to edit the emergency contact phone
     * @param phone emergency contact phone of the person to be updated to
     */
    public AddEmergencyContactNumberCommand(Name name, Phone phone) {
        requireAllNonNull(name, phone);

        this.name = name;
        this.phone = phone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, name, phone));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddEmergencyContactNumberCommand)) {
            return false;
        }

        AddEmergencyContactNumberCommand e = (AddEmergencyContactNumberCommand) other;
        return name.equals(e.name)
                && phone.equals(e.phone);
    }
}
