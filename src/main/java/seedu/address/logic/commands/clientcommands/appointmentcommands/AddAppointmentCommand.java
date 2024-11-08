package seedu.address.logic.commands.clientcommands.appointmentcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;

/**
 * Adds or updates an appointment for a specified person in the address book.
 * The person is identified by their index in the displayed list.
 * The new appointment will overwrite any existing appointment for that person.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "apt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to a client "
            + "identified by the index number used in the displayed client list. "
            + "Existing appointment will be overwritten with the new appointment. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + " [DATE in ddMMyy] "
            + PREFIX_FROM + " [FROM] "
            + PREFIX_TO + " [TO]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "d/ 201224 fr/ 0800 to/ 1000";

    public static final String MESSAGE_ADD_APPOINTMENT_SUCCESS = "Appointment scheduled for %1$s on:\n"
                                                                    + "%2$s";
    public static final String MESSAGE_UPDATE_APPOINTMENT_SUCCESS = "Updated appointment scheduled for %1$s on:\n"
                                                                    + "%2$s";
    public static final String MESSAGE_INVALID_PERIOD =
            "Invalid from and to timings! From timing cannot be after to timing.";

    private final Index index;
    private final Appointment appointment;

    /**
     * Constructs an {@code AppointmentCommand} with the specified index and appointment details.
     *
     * @param index The index of the person in the filtered person list.
     * @param appointment The new appointment to be added or updated.
     */
    public AddAppointmentCommand(Index index, Appointment appointment) {
        requireNonNull(index);
        requireNonNull(appointment);
        this.index = index;
        this.appointment = appointment;
    }

    /**
     * Executes the command to add or update the appointment for the specified person.
     *
     * @param model The model which contains the list of persons and handles command execution.
     * @return The result of executing the command.
     * @throws CommandException If the index is invalid or the person cannot be found.
     */
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson;

        if (personToEdit instanceof Buyer buyer) {
            editedPerson = new Buyer(buyer.getName(), buyer.getPhone(),
                    buyer.getEmail(), buyer.getTags(),
                    appointment);
        } else {
            Seller seller = (Seller) personToEdit;
            editedPerson = new Seller(seller.getName(), seller.getPhone(),
                    seller.getEmail(), seller.getTags(),
                    appointment);
        }
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }
    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = personToEdit.hasAppointment()
                ? MESSAGE_ADD_APPOINTMENT_SUCCESS
                : MESSAGE_UPDATE_APPOINTMENT_SUCCESS;
        return String.format(message, personToEdit.getName(), appointment);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddAppointmentCommand)) {
            return false;
        }
        AddAppointmentCommand otherAppointmentCommand = (AddAppointmentCommand) other;
        return index.equals(otherAppointmentCommand.index) && appointment.equals(otherAppointmentCommand.appointment);
    }
}
