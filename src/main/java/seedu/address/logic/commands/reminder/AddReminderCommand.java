package seedu.address.logic.commands.reminder;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * Adds a reminder to the address book.
 */
public class AddReminderCommand extends Command {
    public static final String COMMAND_WORD = "radd"; // reminder add
    public static final String COMMAND_WORD_SHORT = "ra"; // reminder add

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD_SHORT
            + ": Adds a reminder to Client hub.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME, "
            + PREFIX_DATE_TIME + "DATE_AND_TIME, "
            + PREFIX_DESCRIPTION + "DESCRIPTION\n"
            + "Examples:\n"
            + "- " + COMMAND_WORD + " " + PREFIX_NAME + "John " + PREFIX_DATE_TIME + "2021-12-31 23:59 "
            + PREFIX_DESCRIPTION + "New Year's Eve\n"
            + "- " + COMMAND_WORD_SHORT + " " + PREFIX_NAME + "John " + PREFIX_DATE_TIME + "2021-12-31 23:59 "
            + PREFIX_DESCRIPTION + "New Year's Eve\n";


    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";
    public static final String MESSAGE_NONEXISTENT_PERSON = "This client doesn't exist in the Client Hub.";
    public static final String MESSAGE_MORE_THAN_ONE_PERSON = "There is more than one client with this name in "
            + "client hub. Please use a more specific name instead.";

    private final Reminder toAdd;

    /**
     * Constructs an {@code AddReminderCommand} with the specified {@code Reminder}.
     *
     * <p>This command initializes an instance that will add the given reminder.
     * The reminder must not be null.
     *
     * @param reminder The {@code Reminder} to be added.
     * @throws NullPointerException if {@code reminder} is null.
     */
    public AddReminderCommand(Reminder reminder) {
        requireNonNull(reminder);
        toAdd = reminder;
    }

    /**
     * Executes the command to add a reminder to the model.
     *
     * @param model The {@code Model} which the command should operate on.
     * @return A {@code CommandResult} indicating the outcome of the command execution.
     * @throws CommandException If there is an issue during command execution.
     */

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Parse input using the NameContainsKeywordsPredicate
        String[] nameKeywords = toAdd.getPersonName().split("\\s+");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(List.of(nameKeywords));
        List<Person> matchingPersons = model.getClientHub().getPersonList().filtered(predicate);

        // Check if there is exactly one match
        if (matchingPersons.size() == 1) {
            Person person = matchingPersons.get(0);

            // Creates a new reminder with the full name of the person
            Reminder reminderWithFullName = toAdd.getReminderWithFullName(person.getName().fullName);
            person.addReminder(reminderWithFullName);
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(reminderWithFullName)));
        } else if (matchingPersons.isEmpty()) {
            throw new CommandException(MESSAGE_NONEXISTENT_PERSON);
        } else {
            model.updateFilteredPersonList(predicate);
            throw new CommandException(MESSAGE_MORE_THAN_ONE_PERSON);
        }
    }

    /**
     * Checks if this {@code AddReminderCommand} is equal to another object.
     *
     * @param other The other object to compare to.
     * @return {@code true} if the other object is an {@code AddReminderCommand}, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        // Check if same object
        if (other == this) {
            return true;
        }

        // Check if instance of AddReminderCommand and compare reminders
        if (other instanceof AddReminderCommand) {
            AddReminderCommand otherCommand = (AddReminderCommand) other;
            return toAdd.equals(otherCommand.toAdd);
        }

        // If neither of the above, return false
        return false;
    }

    /**
     * Returns a string representation of the {@code AddReminderCommand}.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return "AddReminderCommand {" + "toAdd=" + toAdd + '}';
    }
}
