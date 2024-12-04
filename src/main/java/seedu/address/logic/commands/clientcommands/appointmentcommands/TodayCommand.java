package seedu.address.logic.commands.clientcommands.appointmentcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.HasAppointmentTodayPredicate;
import seedu.address.model.person.Person;

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
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_APPOINTMENTS_TODAY);
        }

        return new CommandResult(String.format(Messages.MESSAGE_TODAY_APPOINTMENTS,
                                                lastShownList.size()),
                                        false, false, false, false);
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
