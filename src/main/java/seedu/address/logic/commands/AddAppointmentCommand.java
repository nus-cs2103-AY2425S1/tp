package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.AppointmentDescriptor;

/**
 * Adds an appointment to the appointment book.
 */
public class AddAppointmentCommand extends AddCommand {
    public static final String COMMAND_WORD = "add appt";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an appointment to the appointment book. "
            + "Parameters: "
            + PREFIX_APPOINTMENT_TYPE + "Appointment_TYPE"
            + PREFIX_PERSON_ID + "PersonId"
            + PREFIX_DATETIME + "Date"
            + PREFIX_SICKNESS + "Sickness"
            + PREFIX_MEDICINE + "Medicine"
            + "Example: " + COMMAND_WORD
            + PREFIX_APPOINTMENT_TYPE + "Check up"
            + PREFIX_DATETIME + "16/10/2024 12:00"
            + PREFIX_PERSON_ID + "1"
            + PREFIX_SICKNESS + "Common Cold"
            + PREFIX_MEDICINE + "Paracetamol";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the address book";

    private final AppointmentDescriptor toAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(AppointmentDescriptor appointment) {
        requireNonNull(appointment);
        toAdd = appointment;
    }

    /*
     * Checks if the entity being added to model already exists.
     */
    @Override
    protected boolean alreadyExists(Model model) {
        return model.hasAppointment(toAdd);
    };

    /*
     * Adds the entity to the model.
     */
    @Override
    protected void addEntity(Model model) {
        model.addAppointment(toAdd);
    };

    /*
     * Returns success message to display upon adding entity.
     */
    @Override
    protected String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    };

    /*
     * Returns the message to display when there is a duplicate.
     */
    @Override
    protected String getDuplicateEntityMessage() {
        return MESSAGE_DUPLICATE_APPOINTMENT;
    };

    /**
     * Formats the entity for displaying in the success message.
     */
    @Override
    protected String formatEntity() {
        return Messages.formatAppointment(toAdd);
    };

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (alreadyExists(model)) {
            throw new CommandException(getDuplicateEntityMessage());
        }

        addEntity(model);
        return new CommandResult(String.format(getSuccessMessage(), formatEntity()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAppointmentCommand otherAddAppointmentCommand)) {
            return false;
        }

        return toAdd.equals(otherAddAppointmentCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
