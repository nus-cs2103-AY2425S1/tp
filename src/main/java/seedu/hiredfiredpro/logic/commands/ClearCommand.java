package seedu.hiredfiredpro.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hiredfiredpro.model.HiredFiredPro;
import seedu.hiredfiredpro.model.Model;

/**
 * Clears the list of candidates.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Candidate list has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setHiredFiredPro(new HiredFiredPro());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
