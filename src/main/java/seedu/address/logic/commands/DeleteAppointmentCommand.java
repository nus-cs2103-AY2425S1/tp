package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes an appointment identified using it's displayed name from the address book
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "appointment-delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified appointment.\n"
            + "Parameters: NAME (must be the name of an existing client)\n"
            + "Example: " + COMMAND_WORD + " John Doe";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted appointment: %1$s";

    private Name name;

    public DeleteAppointmentCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        int index = -1;
        for (int i = 0; i < lastShownList.size(); i++) {
            if (lastShownList.get(i).getName().toString().equals(name.toString())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new CommandException(Messages.MESSAGE_INVALID_NAME_DISPLAYED);
        }

        Person appointmentToDelete = lastShownList.get(index);
        model.deleteAppointment(appointmentToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                Messages.format(appointmentToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteAppointmentCommand)) {
            return false;
        }

        DeleteAppointmentCommand otherDeleteAppointmentCommand = (DeleteAppointmentCommand) other;
        return name.equals(otherDeleteAppointmentCommand.name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toDeleteAppointment", name)
                .toString();
    }
}
