package careconnect.logic.commands;

import static careconnect.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import careconnect.commons.core.index.Index;
import careconnect.commons.util.ToStringBuilder;
import careconnect.logic.Messages;
import careconnect.logic.commands.exceptions.CommandException;
import careconnect.logic.parser.CliSyntax;
import careconnect.model.Model;
import careconnect.model.log.Log;
import careconnect.model.person.Address;
import careconnect.model.person.AppointmentDate;
import careconnect.model.person.Email;
import careconnect.model.person.Name;
import careconnect.model.person.Person;
import careconnect.model.person.Phone;
import careconnect.model.tag.Tag;

/**
 * Sets an appointment date for a person in the address book.
 */
public class SetAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "setappointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets an appointment date for the person "
            + "identified "
            + "by the index number used in the displayed person list. "
            + "If date is left blank, current appointment will be deleted.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + CliSyntax.PREFIX_DATE + "[YYYY-MM-DD]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_DATE + "2024-10-24";

    public static final String MESSAGE_SUCCESS = "Appointment set for person: %1$s on date: %2$s";

    private final Index targetIndex;
    private final AppointmentDate date;

    /**
     * @param index Index of the person in the filtered person list to set appointment for
     * @param date Appointment date to set
     */
    public SetAppointmentCommand(Index index, AppointmentDate date) {
        requireAllNonNull(index, date);
        this.targetIndex = index;
        this.date = date;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToSetAppointment} and
     * appointment set
     */
    private static Person createPersonWithSetAppointment(
            Person personToSetAppointment, AppointmentDate appointmentDate) {
        requireAllNonNull(personToSetAppointment, appointmentDate);

        Name name = personToSetAppointment.getName();
        Phone phone = personToSetAppointment.getPhone();
        Email email = personToSetAppointment.getEmail();
        Address address = personToSetAppointment.getAddress();
        ArrayList<Log> logs = new ArrayList<>(personToSetAppointment.getLogs());
        Set<Tag> tags = personToSetAppointment.getTags();


        return new Person(name, phone, email, address, tags, logs, appointmentDate);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToSetAppointment = lastShownList.get(targetIndex.getZeroBased());
        Person personWithSetAppointment = createPersonWithSetAppointment(personToSetAppointment, this.date);

        model.setPerson(personToSetAppointment, personWithSetAppointment);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                personToSetAppointment.getName(), this.date),
                false, false, targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetAppointmentCommand)) {
            return false;
        }
        SetAppointmentCommand otherSetAppointmentCommand = (SetAppointmentCommand) other;

        return targetIndex.equals(otherSetAppointmentCommand.targetIndex)
                && date.equals(otherSetAppointmentCommand.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("date", date)
                .toString();
    }

}
