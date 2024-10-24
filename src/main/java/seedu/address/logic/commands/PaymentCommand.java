package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAID;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;

/**
 * Marks a payment as paid or unpaid for a person identified by their name and schedule.
 */
public class PaymentCommand extends Command {

    public static final String COMMAND_WORD = "payment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks payment as paid or unpaid for the person identified by the name.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE & TIME (in the format: YYYY-MM-DD HHmm) "
            + PREFIX_PAID + "PAID_STATUS (paid/unpaid/true/false)\n"
            + "Example: " + COMMAND_WORD + " "
            + "John Doe "
            + PREFIX_DATE + "2024-10-24 1000 "
            + PREFIX_PAID + "paid";

    public static final String MESSAGE_PAYMENT_SUCCESS = "Payment status updated for %1$s's appointment at %2$s: %3$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No person found with name: %1$s";
    public static final String MESSAGE_SCHEDULE_NOT_FOUND = "No appointment found for %1$s at %2$s";
    public static final String MESSAGE_PAYMENT_STATUS_INVALID =
            "Payment status must be either 'paid', 'unpaid', 'true', or 'false'";

    private final Name name;
    private final String dateTime;
    private final boolean isPaid;

    /**
     * Constructs a PaymentCommand to update the payment status for the specified appointment of a specified person.
     *
     * @param name The name of the person.
     * @param dateTime The date and time of the scheduled appointment.
     * @param isPaid The payment status of the appointment of the person.
     */
    public PaymentCommand(Name name, String dateTime, boolean isPaid) {
        this.name = name;
        this.dateTime = dateTime;
        this.isPaid = isPaid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();
        Person personToUpdate = findPersonByName(personList, name);

        if (personToUpdate == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, name));
        }

        // Find the specific schedule and update its payment status
        Set<Schedule> schedules = personToUpdate.getSchedules();
        Optional<Schedule> targetSchedule = schedules.stream()
                .filter(s -> s.getDateTime().equals(dateTime))
                .findFirst();

        if (!targetSchedule.isPresent()) {
            throw new CommandException(String.format(MESSAGE_SCHEDULE_NOT_FOUND, name, dateTime));
        }

        // Update the payment status
        Schedule updatedSchedule = new Schedule(dateTime, targetSchedule.get().getNotes());
        if (isPaid) {
            updatedSchedule.markPaymentAsPaid();
        } else {
            updatedSchedule.markPaymentAsUnpaid();
        }

        // Create new schedule set with updated schedule
        Set<Schedule> updatedSchedules = schedules.stream()
                .map(s -> s.getDateTime().equals(dateTime) ? updatedSchedule : s)
                .collect(Collectors.toCollection(HashSet::new));

        Person updatedPerson = new Person(personToUpdate.getName(),
                personToUpdate.getPhone(),
                personToUpdate.getEmail(),
                personToUpdate.getAddress(),
                updatedSchedules,
                personToUpdate.getReminder(),
                personToUpdate.getTags());

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_PAYMENT_SUCCESS,
                name,
                dateTime,
                isPaid ? "Paid" : "Unpaid"));
    }


    /**
     * Finds a person by name from the given list.
     *
     * @param personList The list of persons to search.
     * @param name The name of the person to find.
     * @return The person with the specified name, or null if not found
     */
    private Person findPersonByName(List<Person> personList, Name name) {
        return personList.stream()
                .filter(person -> person.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PaymentCommand // instanceof handles nulls
                && name.equals(((PaymentCommand) other).name)
                && dateTime.equals(((PaymentCommand) other).dateTime)
                && isPaid == ((PaymentCommand) other).isPaid);
    }
}
