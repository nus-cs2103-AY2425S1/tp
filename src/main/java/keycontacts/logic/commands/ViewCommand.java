package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DATE;

import keycontacts.commons.util.ToStringBuilder;
import keycontacts.model.Model;
import keycontacts.model.lesson.Date;

/**
 * Displays the lesson schedule for a given date to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the schedule for the week of the given date."
            + " If no date is provided, the current date will be used.\n"
            + "Parameters: [" + PREFIX_DATE + "DATE]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DATE + "07-11-2024";

    public static final String MESSAGE_SUCCESS = "Calendar view updated.";

    private final Date date;

    public ViewCommand(Date date) {
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, date, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand otherViewCommand)) {
            return false;
        }

        return date.equals(otherViewCommand.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("date", date)
                .toString();
    }
}
