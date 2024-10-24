package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes a reminder identified using it's displayed name from the address book.
 */
public class DeleteReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder-delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified reminder.\n"
            + "Parameters: NAME (must be the name of an existing client)\n"
            + "Example: " + COMMAND_WORD + " John Doe";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted reminder for %1$s";

    private Name name;

    public DeleteReminderCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        int index = -1;
        for (int i = 0; i < lastShownList.size(); i++) {
            if (lastShownList.get(i).getName().toString().equals(name.toString())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new CommandException(Messages.MESSAGE_INVALID_NAME_DISPLAYED);
        }

        String reminder = lastShownList.get(index).getReminder().reminder;
        Person reminderToDelete = lastShownList.get(index);
        model.deleteReminder(reminderToDelete);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS,
                Messages.formatReminder(reminderToDelete, reminder)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReminderCommand // instanceof handles nulls
                && name.equals(((DeleteReminderCommand) other).name)); // state check
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toDeleteReminderFor", name)
                .toString();
    }
}
