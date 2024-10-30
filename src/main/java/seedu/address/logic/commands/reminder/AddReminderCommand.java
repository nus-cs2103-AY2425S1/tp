package seedu.address.logic.commands.reminder;
import static java.util.Objects.requireNonNull;

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
    public static final String COMMAND_WORD = "ra"; // reminder add

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to the address book. "
            + "Parameters: "
            + "Person "
            + "DATE and TIME "
            + "Description\n"
            + "Example: " + COMMAND_WORD + " "
            + "John "
            + "2021-12-31 2359 "
            + "New Year's Eve";

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";
    public static final String MESSAGE_NONEXISTENT_PERSON = "This person doesn't exist in the address book.";
    public static final String MESSAGE_MORE_THAN_ONE_PERSON = "There is more than one person with this name in the "
            + "address book. Please use a more specific name instead.";

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
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(List.of(toAdd.getPerson()));
        List<Person> matchingPersons = model.getClientHub().getPersonList().filtered(predicate);

        // Check if there is exactly one match
        if (matchingPersons.size() == 1) {
            Person person = matchingPersons.get(0);
            model.addReminder(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
        } else if (matchingPersons.isEmpty()) {
            throw new CommandException(MESSAGE_NONEXISTENT_PERSON);
        } else {
            throw new CommandException("More than one person with the specified name found. Please be more specific.");
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
        return other == this // short circuit if same object
                || other instanceof AddReminderCommand; // instanceof handles nulls
    }

    /**
     * Returns a string representation of the {@code AddReminderCommand}.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return "AddReminderCommand";
    }
}
