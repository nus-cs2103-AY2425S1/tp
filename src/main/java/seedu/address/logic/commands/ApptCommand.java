package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Appt;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Changes the remark of an existing person in the address book.
 */
public class ApptCommand extends Command {

    public static final String MESSAGE_ARGUMENTS = "Appt: %2$s, Name: %1$s";
    public static final String COMMAND_WORD = "appt";
    public static final String MESSAGE_APPT_ADDED_SUCCESS = "Appointment added successfully";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person not found";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the appointment date of the person identified "
            + "by the name in the last person listing. "
            + "Existing appointment will be overwritten by the input.\n"
            + "appt dt/YYYY-MM-DDTHH:MM n/NAME\n";

    private final LocalDateTime dateTime;
    private final Name name;

    /**
     * @param dateTime of the appointment
     * @param name of the person
     */
    public ApptCommand(LocalDateTime dateTime, Name name) {
        requireAllNonNull(dateTime, name);
        this.dateTime = dateTime;
        this.name = name;
    }

    /**
     * Executes the command and returns the result message.
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Find the person with the given name
        Optional<Person> optionalPerson = lastShownList.stream()
            .filter(person -> person.getName().equals(name))
            .findFirst();

        if (!optionalPerson.isPresent()) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        Person person = optionalPerson.get();

        // Add the appointment to the person's list of appointments
        person.addAppt(new Appt(dateTime));

        return new CommandResult(generateSuccessMessage(person));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ApptCommand)) {
            return false;
        }

        ApptCommand e = (ApptCommand) other;
        return name.equals(e.name)
                && dateTime.equals(e.dateTime);
    }

    /**
     * Generates a command execution success message based on whether the remark is added or deleted.
     */
    private String generateSuccessMessage(Person person) {
        return String.format(MESSAGE_APPT_ADDED_SUCCESS, person.getName());
    }
}
