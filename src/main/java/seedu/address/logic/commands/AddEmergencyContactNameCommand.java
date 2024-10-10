package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.Model;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Name;

/**
 * Adds an emergency contact name to an existing person in the address book.
 */
public class AddEmergencyContactNameCommand extends Command {
    public static final String COMMAND_WORD = "addEmergencyContactName";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an emergency contact name to the student "
            + "identified by the name. "
            + "Parameters: n/NAME e/EMERGENCY_CONTACT\n"
            + "Example: " + COMMAND_WORD + " n/Henry e/John Doe";

    public static final String MESSAGE_ADD_CONTACT_SUCCESS = "Added emergency contact's name for Student: %1$s";
    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Removed emergency contact's name from Student: %1$s";

    public static final String MESSAGE_STUDENT_NOT_FOUND = "The student with name '%1$s' could not be found.";
    public static final String MESSAGE_ARGUMENTS = "Name: %1$s, ECName: %2$s";

    private final Name name;
    private final Name emergencyContactName;

    /**
     * @param name of the student in the filtered list
     * @param eCName to be added
     */
    public AddEmergencyContactNameCommand(Name name, Name eCName) {
        requireAllNonNull(name, eCName);

        this.name = name;
        this.emergencyContactName = eCName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Placeholder - Update with actual logic
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, name, emergencyContactName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddEmergencyContactNameCommand)) {
            return false;
        }

        AddEmergencyContactNameCommand e = (AddEmergencyContactNameCommand) other;
        return name.equals(e.name)
                && emergencyContactName.equals(e.emergencyContactName);
    }
}

