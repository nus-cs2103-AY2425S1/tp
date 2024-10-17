package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Appt;
import seedu.address.model.person.Name;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Changes the remark of an existing person in the address book.
 */
public class ApptCommand extends Command {

    public static final String MESSAGE_ARGUMENTS = "Appt: %2$s, Name: %1$s";

    private final Appt date;
    private final Name name;

    public static final String COMMAND_WORD = "appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "r/ [REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "r/ Likes to swim.";

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param remark of the person to be updated to
     */
    public ApptCommand(Appt date, Name name) {
        requireAllNonNull(date, name);

        this.date = date;
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, date, name));
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
                && date.equals(e.date);
    }
}

