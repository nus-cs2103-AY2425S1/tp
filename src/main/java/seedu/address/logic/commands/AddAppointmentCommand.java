package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SICKNESS;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.AppointmentDescriptor;
import seedu.address.model.person.Person;

/**
 * Adds an appointment to the appointment book.
 */
public class AddAppointmentCommand extends AddCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + APPOINTMENT_ENTITY_STRING
            + ": Adds an appointment to the appointment book. \n"
            + "Parameters: "
            + PREFIX_PERSON_ID + "PERSON_ID "
            + PREFIX_APPOINTMENT_TYPE + "APPOINTMENT_TYPE "
            + PREFIX_DATETIME + "DATE_TIME "
            + "[" + PREFIX_SICKNESS + "SICKNESS] "
            + "[" + PREFIX_MEDICINE + "MEDICINE] \n"
            + "Example: " + COMMAND_WORD + " " + APPOINTMENT_ENTITY_STRING + " "
            + PREFIX_PERSON_ID + "1 "
            + PREFIX_APPOINTMENT_TYPE + "Check up "
            + PREFIX_DATETIME + "2024-10-16 12:30:30 "
            + PREFIX_SICKNESS + "Common Cold "
            + PREFIX_MEDICINE + "Paracetamol";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT =
            "This appointment already exists in the appointment book";
    public static final String MESSAGE_PERSON_NOT_FOUND =
            "This person ID does not belong to anyone in the address book";

    private final AppointmentDescriptor appointmentDescriptor;
    private final int personId;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(AppointmentDescriptor appointmentDescriptor, int personId) {
        requireAllNonNull(appointmentDescriptor, personId);
        this.appointmentDescriptor = appointmentDescriptor;
        this.personId = personId;
    }

    /**
     * Checks if the entity being added to model already exists.
     *
     * @param model Model to check if entity already exists in.
     * @return True if entity already exists in model, false otherwise.
     */
    @Override
    protected boolean alreadyExists(Model model) {
        return model.hasAppointment(appointmentDescriptor);
    };

    /**
     * Adds the entity to the model.
     *
     * @param model Model to add entity to.
     * @throws CommandException If the person ID does not exist in the model.
     */
    @Override
    protected void addEntity(Model model) throws CommandException {
        Optional<Person> personOptional = model.findPerson(personId);
        if (personOptional.isEmpty()) {
            throw new CommandException(getPersonIdDoesNotExistMessage());
        }
        model.addAppointment(personOptional.get(), appointmentDescriptor);
    };

    /**
     * Returns success message to display upon adding entity.
     *
     * @return Success message.
     */
    @Override
    protected String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    };

    /**
     * Returns the message to display when there is a duplicate.
     *
     * @return Duplicate message.
     */
    @Override
    protected String getDuplicateEntityMessage() {
        return MESSAGE_DUPLICATE_APPOINTMENT;
    };

    /**
     * Returns the message to display when the person ID does not exist.
     *
     * @return Person ID does not exist message.
     */
    protected String getPersonIdDoesNotExistMessage() {
        return MESSAGE_PERSON_NOT_FOUND;
    };

    /**
     * Formats the entity for displaying in the success message.
     *
     * @return Formatted entity.
     */
    @Override
    protected String formatEntity() {
        return Messages.formatAppointment(appointmentDescriptor);
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

        return appointmentDescriptor.equals(otherAddAppointmentCommand.appointmentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("appointmentDescriptor", appointmentDescriptor)
                .toString();
    }
}
