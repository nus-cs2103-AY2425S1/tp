package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.HasAppointmentTodayPredicate;

/**
 * Lists all persons with appointments today in the address book to the user.
 */
public class TodayCommand extends Command {
    public static final String COMMAND_WORD = "today";
    public static final String MESSAGE_NO_APPOINTMENTS_TODAY = "You have no appointments today";

    private static final HasAppointmentTodayPredicate PREDICATE = new HasAppointmentTodayPredicate();

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE);
        if (model.getFilteredPersonList().isEmpty()) {
            throw new CommandException(MESSAGE_NO_APPOINTMENTS_TODAY);
        }
        return new CommandResult(String.format(Messages.MESSAGE_TODAY_APPOINTMENTS,
                                                model.getFilteredPersonList().size()),
                                        false, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof TodayCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", PREDICATE)
                .toString();
    }
}
