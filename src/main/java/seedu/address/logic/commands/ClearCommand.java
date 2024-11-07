package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.CampusConnect;
import seedu.address.model.Model;

/**
 * Clears the campus connect.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Campus Connect has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setCampusConnect(new CampusConnect());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ClearCommand)) {
            return false;
        }

        return true;
    }
}
