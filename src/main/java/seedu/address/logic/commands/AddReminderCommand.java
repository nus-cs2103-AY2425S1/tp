package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_SEEN;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Reminder;

/**
 * Adds a reminder to the address book
 */
public class AddReminderCommand extends Command {

    public static final String COMMAND_WORD = "remind";
    public static final String ALT_COMMAND_WORD = "rem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a reminder "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LAST_SEEN + "DATE "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LAST_SEEN + "21-11-2024 "
            + PREFIX_DESCRIPTION + "Meet up for lunch ";

    public static final String MESSAGE_SUCCESS = "Created reminder about %1$s";

    private final Index targetIndex;
    private final String reminderDate;
    private final String reminderDescription;
    private Reminder toAdd;

    /**
     * Creates an AddReminderCommand to add the specified {@code Reminder}
     *
     * @param targetIndex Index of person relevant to the reminder.
     * @param reminderDate Date of the reminder.
     * @param reminderDescription Description of the reminder.
     */
    public AddReminderCommand(Index targetIndex, String reminderDate, String reminderDescription) {
        requireNonNull(reminderDate);
        requireNonNull(reminderDescription);
        requireNonNull(targetIndex);

        this.reminderDate = reminderDate;
        this.reminderDescription = reminderDescription;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personForReminder = lastShownList.get(targetIndex.getZeroBased());
        toAdd = new Reminder(reminderDate, reminderDescription, personForReminder.getName());
        model.addReminder(toAdd, personForReminder);
        model.addReminderToBook(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personForReminder.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddReminderCommand)) {
            return false;
        }

        AddReminderCommand otherAddReminderCommand = (AddReminderCommand) other;
        return targetIndex.equals(otherAddReminderCommand.targetIndex)
                && reminderDate.equals(otherAddReminderCommand.reminderDate)
                && reminderDescription.equals(otherAddReminderCommand.reminderDescription);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
