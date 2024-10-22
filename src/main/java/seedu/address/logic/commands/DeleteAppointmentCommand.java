package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;

/**
 * Deletes a client's appointment identified by the client's name.
 * The name must correspond to a person in the current list of persons.
 */
public class DeleteAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "delapt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the appointment corresponding to the client's name.\n"
            + "Parameters: CLIENT_NAME (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " n/Tan Wen Xuan";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Successfully deleted appointment from %1$s";

    private final Name targetName;

    /**
     * Creates a {@code DeleteAppointmentCommand} to delete the appointment of the person with the specified
     * {@code Name}.
     *
     * @param targetName The name of the person whose appointment will be deleted.
     */
    public DeleteAppointmentCommand(Name targetName) {
        this.targetName = targetName;
    }

    /**
     * Executes the command to delete the appointment of the person identified by the {@code targetName}.
     * If the person is found in the filtered list of persons, the appointment is set to an empty {@code Appointment}.
     *
     * @param model The model which contains the list of persons.
     * @return The result of the command execution.
     * @throws CommandException If the person cannot be found in the filtered list.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToDeleteAppointment = model.getPersonByName(targetName);
        if (!lastShownList.contains(personToDeleteAppointment)) {
            String closestMatch = findClosestMatch(targetName.toString(), lastShownList);

            if (closestMatch != null) {
                throw new CommandException(String.format(Messages.MESSAGE_SUGGESTION, closestMatch));
            } else {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_INPUT);
            }
        }

        Person personWithoutAppointment;

        if (personToDeleteAppointment instanceof Buyer) {
            Buyer buyer = (Buyer) personToDeleteAppointment;
            personWithoutAppointment = new Buyer(buyer.getName(), buyer.getPhone(),
                    buyer.getEmail(), buyer.getTags(),
                    new Appointment(new Date(""), new From(""), new To("")),
                    buyer.getProperty());
        } else { // Must be a Seller
            Seller seller = (Seller) personToDeleteAppointment;
            personWithoutAppointment = new Seller(seller.getName(), seller.getPhone(),
                    seller.getEmail(), seller.getTags(),
                    new Appointment(new Date(""), new From(""), new To("")),
                    seller.getProperty());
        }
        model.setPerson(personToDeleteAppointment, personWithoutAppointment);

        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                personToDeleteAppointment.getName()));
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
        return targetName.equals(otherDeleteAppointmentCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .toString();
    }
}
